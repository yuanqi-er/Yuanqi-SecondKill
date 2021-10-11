package com.yuanqi.dao;

import com.yuanqi.entity.Shop;
import com.yuanqi.mapper.ShopMapper;
import com.yuanqi.vo.ShopVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public class ShopDao {
    @Autowired
    private ShopMapper shopMapper;

    public void saveShopInfo(Shop shop) {
        shopMapper.saveShopInfo(shop);
    }

    public List<Shop> listShopInfoBy(ShopVo shopVo) {
        return shopMapper.listShopInfoBy(shopVo);
    }

    public void updateInfoBy(Shop shop) {
        shopMapper.updateInfoBy(shop);
    }
}
