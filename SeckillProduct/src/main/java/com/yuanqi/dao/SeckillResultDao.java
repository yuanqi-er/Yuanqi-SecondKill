package com.yuanqi.dao;

import com.yuanqi.entity.SeckillResult;
import com.yuanqi.mapper.SeckillResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SeckillResultDao {
    @Autowired
    private SeckillResultMapper seckillResultMapper;

    public void saveSeckillResult(SeckillResult seckillResult) {
        seckillResultMapper.saveSeckillResult(seckillResult);
    }
}
