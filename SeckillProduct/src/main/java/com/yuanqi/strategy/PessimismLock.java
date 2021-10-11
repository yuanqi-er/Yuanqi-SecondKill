package com.yuanqi.strategy;

import com.yuanqi.dao.SeckillDao;
import com.yuanqi.entity.SeckillProduct;
import com.yuanqi.service.SeckillServiceByLock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * 悲观锁实现（SELECT ... FOR UPDATE）
 * ！没实现用户的秒杀结果逻辑
 */
public class PessimismLock implements SeckillOperator {
    @Autowired
    private SeckillDao seckillDao;

    private SeckillServiceByLock seckillServiceByLock;
    public PessimismLock(SeckillServiceByLock seckillServiceByLock) {
        this.seckillServiceByLock = seckillServiceByLock;
    }

    @Override
    public Map<String, String> seckill(int userid, int id) {
        Map<String, String> dataMap = seckillServiceByLock.pessimismLock(userid, id);
        return dataMap;
    }
}
