package com.yuanqi.mapper;

import com.yuanqi.entity.SeckillResult;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SeckillResultMapper {
    void saveSeckillResult(SeckillResult seckillResult);


}
