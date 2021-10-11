package com.yuanqi.service;

import com.yuanqi.dao.SeckillDao;
import com.yuanqi.entity.SeckillProduct;
import com.yuanqi.entity.SeckillResult;
import com.yuanqi.util.RedisLockUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * 秒杀的整合服务！
 */
@Service
public class IntegratedService {
    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private SeckillResultService seckillResultService;

    //把FutureTask放入队列中。
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private Map<SeckillUnique, Future> cacheSeckillResultMap = new HashMap<>();


    /**内部类，用于实现Callable**/
    class processSeckill implements Callable<Integer> {
        private int userid;
        private int id;

        public processSeckill(int userid, int id) {
            this.userid = userid;
            this.id = id;
        }

        @Override
        public Integer call() throws Exception {
            try {
                //获取锁（获取失败就继续获取）
                boolean hasLock = RedisLockUtil.tryGetDistributedLock(id + "", userid + "", 50000);
                if (!hasLock) {
                    while (!hasLock) {
                        hasLock = RedisLockUtil.tryGetDistributedLock(id + "", userid + "", 50000);
                    }
                }
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
            } finally {
                //最后释放锁（释放失败就继续释放）
                boolean releaseLock = RedisLockUtil.releaseDistributedLock(id + "", userid + "");
                if (!releaseLock) {
                    while (!releaseLock) {
                        releaseLock = RedisLockUtil.releaseDistributedLock(id + "", userid + "");
                    }
                }
            }

        }
    }

    /**商品唯一码？**/
    @Data
    class SeckillUnique {
        int userid;
        int id;

        public SeckillUnique(int userid, int id) {
            this.userid = userid;
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SeckillUnique that = (SeckillUnique) o;
            return userid == that.userid && id == that.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(userid, id);
        }
    }

    /**
     * Future异步 整合 分布式锁
     **/
    public void seckillIntegrated(int userid, int id) {
        processSeckill seckillFuture = new processSeckill(userid, id);
        Future<Integer> result = executorService.submit(seckillFuture);
        //构建商品订单唯一码
        SeckillUnique seckillUnique = new SeckillUnique(userid, id);
        cacheSeckillResultMap.put(seckillUnique, result);
    }

}
