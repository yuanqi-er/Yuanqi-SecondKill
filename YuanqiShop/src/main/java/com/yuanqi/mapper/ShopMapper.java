package com.yuanqi.mapper;

import com.yuanqi.entity.Shop;
import com.yuanqi.vo.ShopVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShopMapper {
    void saveShopInfo(Shop shop);

    //管理员根据名字和状态查询所商铺
    List<Shop> listShopInfoBy(ShopVo shopVo);

    //更新商铺状态
    void updateInfoBy(Shop shop);
}
