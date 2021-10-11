package com.yuanqi.service;

import com.yuanqi.dao.ShopDao;
import com.yuanqi.entity.Shop;
import com.yuanqi.form.ShopApplyForm;
import com.yuanqi.form.ShopSearchForm;
import com.yuanqi.vo.ShopCondition;
import com.yuanqi.vo.ShopVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class ShopService {
    @Autowired
    private ShopDao shopDao;

    public void saveShopInfo(ShopApplyForm shopApplyForm) {
        Shop shop = new Shop();
        shop.setCreateTime(new Date());
        shop.setMerchantId(shopApplyForm.getMerchantId());
        shop.setProvince(shopApplyForm.getProvince());
        shop.setCity(shopApplyForm.getCity());
        shop.setBusinessLicense(shopApplyForm.getBusinessLicense());
        shop.setShopName(shopApplyForm.getShopName());
        shop.setShopBusinessScope(shopApplyForm.getShopBusinessScope());
        shop.setShopDescription(shopApplyForm.getShopDescription());
        shop.setState(0);

        shopDao.saveShopInfo(shop);
    }

    public List<Shop> listShopInfoBy(ShopSearchForm shopSearchForm) {
        ShopVo shopVo = new ShopVo();
        ShopCondition shopCondition = new ShopCondition();
        if ( ! StringUtils.isEmpty(shopSearchForm.getShopName())) {
            shopCondition.setShopName(shopSearchForm.getShopName());
        }
        if ( ! StringUtils.isEmpty(shopSearchForm.getState())) {
            shopCondition.setState(Integer.parseInt(shopSearchForm.getState()));
        }
        return shopDao.listShopInfoBy(shopVo);
    }

    public void updateInfoBy(Shop shop) {
        shopDao.updateInfoBy(shop);
    }
}






