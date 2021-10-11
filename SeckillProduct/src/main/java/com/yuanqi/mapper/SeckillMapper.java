package com.yuanqi.mapper;

import com.yuanqi.entity.SeckillProduct;
import com.yuanqi.vo.SeckillProductVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SeckillMapper {
    //保存秒杀商品信息
    void saveSeckillProduct(SeckillProduct seckillProduct);

    //查询秒杀商品信息
    List<SeckillProduct> listSeckillInfo(SeckillProductVo seckillProductVo);

    //更新秒杀商品的状态
    void updateSeckillProductState(SeckillProduct seckillProduct);

    //根据id查找秒杀商品
    SeckillProduct findSeckillInfoById(int id);

    //更新秒杀商品的数量
    void updateSeckillNumById(int id, int seckillNum);

    //根据主键id查询秒杀商品（悲观锁实现）
    SeckillProduct selectForUpdate(int id);

    //通过id和版本号更新秒杀数量，版本号对应才能更新成功（乐观锁实现）
    int updateSeckillNumByVersion(int id, int seckillNum, int seckillVersion);

}
