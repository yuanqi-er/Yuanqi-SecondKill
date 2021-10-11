package com.yuanqi.dao;

import com.yuanqi.entity.SeckillProduct;
import com.yuanqi.mapper.SeckillMapper;
import com.yuanqi.vo.SeckillProductVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SeckillDao {
    @Autowired
    private SeckillMapper seckillMapper;

    public void saveSeckillProduct(SeckillProduct seckillProduct) {
        seckillMapper.saveSeckillProduct(seckillProduct);
    }

    public List<SeckillProduct> listSeckillInfo(SeckillProductVo seckillProductVo) {
        return seckillMapper.listSeckillInfo(seckillProductVo);
    }

    public void updateSeckillProductState(SeckillProduct seckillProduct) {
        seckillMapper.updateSeckillProductState(seckillProduct);
    }

    public SeckillProduct findSeckillInfoById(int id) {
        return seckillMapper.findSeckillInfoById(id);
    }

    public void updateSeckillNumById(int id, int seckillNum) {
        seckillMapper.updateSeckillNumById(id, seckillNum);
    }

    public SeckillProduct selectForUpdate(int id) {
        return seckillMapper.selectForUpdate(id);
    }

    public int updateSeckillNumByVersion(int id, int seckillNum, int seckillVersion) {
        return seckillMapper.updateSeckillNumByVersion(id, seckillNum, seckillVersion);
    }

}
