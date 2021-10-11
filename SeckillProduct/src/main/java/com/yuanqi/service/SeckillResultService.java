package com.yuanqi.service;

import com.yuanqi.dao.SeckillResultDao;
import com.yuanqi.entity.SeckillResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeckillResultService {
    @Autowired
    private SeckillResultDao seckillResultDao;

    public void saveSeckillResult(SeckillResult seckillResult) {
        seckillResultDao.saveSeckillResult(seckillResult);
    }
}
