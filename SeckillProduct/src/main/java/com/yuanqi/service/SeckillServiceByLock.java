package com.yuanqi.service;

import com.yuanqi.dao.SeckillDao;
import com.yuanqi.entity.SeckillProduct;
import com.yuanqi.entity.SeckillResult;
import com.yuanqi.util.RedisLockUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class SeckillServiceByLock {
    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private SeckillResultService seckillResultService;

    private Lock lock = new ReentrantLock();

    //把库存信息，秒杀数量 通过 kv 放入 Map 中
    private ConcurrentHashMap<String, Integer> cacheMap = new ConcurrentHashMap<>();
    private static final String Inventory = "Inventory";
    private static final String SeckillNum = "SeckillNum";

    //把FutureTask放入队列中。
    //private BlockingDeque<Future> seckillFutureQueue = new LinkedBlockingDeque<>();
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private Map<Integer, Future> cacheSeckillResultMap = new HashMap<>();


    /**
     * 基于Aop加锁的秒杀实现
     ！没实现用户的秒杀结果逻辑
     */
    @Transactional
    public Map<String, String> procedureLockByAOP(int userId, int id) {
        Map<String, String> dataMap = new HashMap<>();

        SeckillProduct seckillProduct = seckillDao.findSeckillInfoById(id);
        int seckillInventory = seckillProduct.getSeckillInventory();
        int seckillNum = seckillProduct.getSeckillNum();
        seckillNum++;
        //如果数量大于库存，就是卖完了！
        if (seckillNum > seckillInventory) {
            dataMap.put("flag", "fail");
            dataMap.put("data", "卖光了，谢谢惠顾！");
            return dataMap;
        }

        //如果成功卖出，秒杀数量 +1
        seckillDao.updateSeckillNumById(id, seckillNum);

        dataMap.put("flag", "success");
        dataMap.put("data", "秒杀成功！");
        return dataMap;
    }

    /**
     * 多线程秒杀实现
     */
    public void multiThread(int userId, int id) {
        Integer inventory = cacheMap.get(Inventory + id);       //库存

        /**
         * 这个是最开始先查出秒杀商品的信息，然后存进cache里，
         * 有人购买成功后商品数量是在库存里更新，不会更新到数据库。
         */
        //把库存放进cacheMap里
        if (inventory == null) {
            SeckillProduct seckillProduct = seckillDao.findSeckillInfoById(id);
            cacheMap.put(Inventory+id, seckillProduct.getSeckillInventory());
            cacheMap.put(SeckillNum+id, seckillProduct.getSeckillNum());
        }
        //再取一次库存
        inventory = cacheMap.get(Inventory + id);
        Integer seckillNum = cacheMap.get(SeckillNum + id);     //秒杀数量
        seckillNum++;
        cacheMap.put(SeckillNum+id, seckillNum);

        new Thread(new SeckillThread(userId, seckillNum, inventory, id)).start();
    }


    /*
        内部类SeckillThread，保存秒杀结果
     */
    class SeckillThread implements Runnable{
        private int userId;
        private int seckillNum;
        private int inventory;
        private int id;

        public SeckillThread(int userId, int seckillNum, int inventory, int id) {
            this.userId = userId;
            this.seckillNum = seckillNum;
            this.inventory = inventory;
            this.id = id;
        }

        @Override
        public void run() {
            SeckillResult seckillResult = new SeckillResult();
            SeckillProduct seckillProduct = seckillDao.findSeckillInfoById(id);

            seckillResult.setProductId(seckillProduct.getProductId());
            seckillResult.setSeckillId(seckillProduct.getId());
            seckillResult.setUserId(userId);
            seckillResult.setResult(0);
            seckillResult.setResultData("用户" + userId + "秒杀成功");
            seckillResult.setSeckillTime(new Date());

            if (seckillNum > inventory) {
                seckillResult.setResult(1);
                seckillResult.setResultData("秒杀失败！！");
                System.out.println("用户" + userId + "秒杀失败");
            }
            seckillResultService.saveSeckillResult(seckillResult);
        }
    }


    /**
     * 悲观锁实现（SELECT ... FOR UPDATE）
     * ！没实现用户的秒杀结果逻辑
     */
    @Transactional
    public Map<String, String> pessimismLock(int userid, int id) {
        Map<String, String> dataMap = new HashMap<>();

        SeckillProduct seckillProduct = seckillDao.selectForUpdate(id);
        //获取库存和已售数量
        int seckillInventory = seckillProduct.getSeckillInventory();
        int seckillnum = seckillProduct.getSeckillNum();

        if(seckillnum>=seckillInventory) {
            dataMap.put("flag", "fail");
            dataMap.put("data", "卖光了，谢谢惠顾！");
            return dataMap;
        }
        seckillnum++;
        //更新秒杀数量
        seckillDao.updateSeckillNumById(id, seckillnum);
        dataMap.put("flag", "success");
        dataMap.put("data", "秒杀成功！");
        return dataMap;
    }


    /**
     * 乐观锁实现（基于版本号）
     * ！没实现用户的秒杀结果逻辑
     */
    @Transactional
    public Map<String, String> optimisticLock(int userid, int id) {
        Map<String, String> dataMap = new HashMap<>(id);

        SeckillProduct seckillProduct = seckillDao.findSeckillInfoById(id);

        //获取库存、已售数量、版本号
        int seckillInventory = seckillProduct.getSeckillInventory();
        int seckillnum = seckillProduct.getSeckillNum();
        int seckillVersion = seckillProduct.getSeckillversion();

        if(seckillnum>=seckillInventory) {
            dataMap.put("flag", "fail");
            dataMap.put("data", "卖光了，谢谢惠顾！");
            return dataMap;
        }
        seckillnum++;
        //更新秒杀数量
        seckillDao.updateSeckillNumByVersion(id, seckillnum, seckillVersion);
        dataMap.put("flag", "success");
        dataMap.put("data", "秒杀成功！");
        return dataMap;
    }


    /**
     * redis分布式实现秒杀（抢到锁才开始处理秒杀业务）
     */
    @Transactional
    public Map<String, String> redisLock(int userid, int id) {
        Map<String, String> dataMap = new HashMap<>(id);
        boolean hasLock = RedisLockUtil.tryGetDistributedLock(id + "", userid + "", 50000);
        if(hasLock) {
            Map<String, String> result = processSeckill(userid, id, dataMap);
            RedisLockUtil.releaseDistributedLock(id+"", userid+"");
            return result;
        } else {
            //没有抢到锁，再循环三次去抢
            for (int i=0; i<3; i++) {
                hasLock = RedisLockUtil.tryGetDistributedLock(id + "", userid + "", 50000);
                if (hasLock) {
                    Map<String, String> result = processSeckill(userid, id, dataMap);
                    RedisLockUtil.releaseDistributedLock(id+"", userid+"");
                    return result;
                }
            }
        }
        //三番两次都抢不到锁，显示活动太火爆了，请稍后再试
        dataMap.put("flag", "fail");
        dataMap.put("data", "太火爆了，请重新秒杀");
        return dataMap;
    }

    //redis秒杀锁 中复用的秒杀过程
    private Map<String, String> processSeckill(int userid, int id, Map<String, String> dataMap) {
        SeckillProduct seckillProduct = seckillDao.findSeckillInfoById(id);

        //获取库存、已售数量、版本号
        int seckillInventory = seckillProduct.getSeckillInventory();
        int seckillnum = seckillProduct.getSeckillNum();

        if(seckillnum>=seckillInventory) {
            dataMap.put("flag", "fail");
            dataMap.put("data", "卖光了，谢谢惠顾！");
            return dataMap;
        }
        seckillnum++;
        //更新秒杀数量
        seckillDao.updateSeckillNumById(id, seckillnum);
        dataMap.put("flag", "success");
        dataMap.put("data", "秒杀成功！");
        return dataMap;
    }


    /**
     * Future模式异步封装秒杀过程
     */
    public void seckillFuture(int userid, int id) {
        //在线程池里执行futureTask，callable可以直接放进submit里执行。
        Future<Integer> resultFuture = executorService.submit(new processSeckill(userid, id));
        //把Future放入队列
        cacheSeckillResultMap.put(userid, resultFuture);
    }

    class processSeckill implements Callable<Integer> {
        private int userid;
        private int id;

        public processSeckill(int userid, int id) {
            this.userid = userid;
            this.id = id;
        }

        @Override
        public Integer call() throws Exception {
            SeckillProduct seckillInfo = seckillDao.findSeckillInfoById(id);
            int inventory = seckillInfo.getSeckillInventory();
            int seckillNum = seckillInfo.getSeckillNum();
            seckillNum++;

            SeckillResult seckillResult = new SeckillResult();
            seckillResult.setProductId(seckillInfo.getProductId());
            seckillResult.setSeckillId(seckillInfo.getId());
            seckillResult.setUserId(userid);
            seckillResult.setResult(0);
            seckillResult.setResultData("用户" + userid + "秒杀成功！");
            seckillResult.setSeckillTime(new Date());
            if (seckillNum > inventory) {
                seckillResult.setResult(1);
                seckillResult.setResultData("用户" + userid + "秒杀失败");
            } else {
                seckillDao.updateSeckillNumById(id, seckillNum);
            }
            seckillResultService.saveSeckillResult(seckillResult);

            //Result为0即成功！
            return seckillResult.getResult();
        }
    }



}



