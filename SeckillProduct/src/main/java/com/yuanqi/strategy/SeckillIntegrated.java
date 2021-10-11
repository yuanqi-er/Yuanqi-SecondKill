package com.yuanqi.strategy;

import com.yuanqi.service.IntegratedService;

import java.util.Map;

public class SeckillIntegrated implements SeckillOperator {

    private IntegratedService integratedService;
    public SeckillIntegrated(IntegratedService integratedService) {
        this.integratedService = integratedService;
    }

    @Override
    public Map<String, String> seckill(int userid, int id) {
        integratedService.seckillIntegrated(userid, id);
        return null;
    }
}
