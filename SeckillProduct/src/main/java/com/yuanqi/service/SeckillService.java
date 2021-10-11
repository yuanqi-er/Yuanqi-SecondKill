package com.yuanqi.service;

import com.yuanqi.dao.SeckillDao;
import com.yuanqi.entity.Product;
import com.yuanqi.entity.SeckillProduct;
import com.yuanqi.form.SeckillForm;
import com.yuanqi.utils.DateUtils;
import com.yuanqi.vo.SeckillProductCondition;
import com.yuanqi.vo.SeckillProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service
public class SeckillService {
    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private ProductService productService;

    public void saveSeckillProduct(SeckillForm seckillForm) throws ParseException {
        SeckillProduct seckillProduct = new SeckillProduct();
        if(seckillForm != null) {
            seckillProduct.setProductId(seckillForm.getProductId());
            seckillProduct.setSeckillNum(0);
            seckillProduct.setSeckillPrice(seckillForm.getSeckillPrice());
            seckillProduct.setProductPrice(seckillForm.getProductPrice());

            Product product = productService.findProductById(seckillForm.getProductId());
            seckillProduct.setSeckillInventory(product.getProductInventory());

            seckillProduct.setCreateTime(new Date());
            //开始时间，结束时间
            String formatString = "yyyy-MM-dd hh:mm:ss";
            Date startTime = DateUtils.transformTimeBy(seckillForm.getStartTime(), formatString);
            Date endTime = DateUtils.transformTimeBy(seckillForm.getEndTime(), formatString);
            seckillProduct.setStartTime(startTime);
            seckillProduct.setEndTime(endTime);

            seckillProduct.setProductTitle(seckillForm.getProductTitle());
            seckillProduct.setProductName(seckillForm.getProductName());

            seckillProduct.setShopId(product.getShopId());
            seckillProduct.setState(0);

        }
        seckillDao.saveSeckillProduct(seckillProduct);
    }


    public List<SeckillProduct> listSeckillInfo(SeckillForm seckillForm) {
        SeckillProductVo seckillProductVo = new SeckillProductVo();
        if (seckillForm != null) {
            SeckillProductCondition seckillProductCondition = new SeckillProductCondition();

            if (seckillForm.getShopId() != -1) {
                seckillProductCondition.setShopId(seckillForm.getShopId());
            }
            if (seckillForm.getState() != -1) {
                seckillProductCondition.setState(seckillForm.getState());
            }
            seckillProductVo.setSeckillProductCondition(seckillProductCondition);
        }
        return seckillDao.listSeckillInfo(seckillProductVo);
    }

    public void updateSeckillProductState(int id, int state) {
        SeckillProduct seckillProduct = new SeckillProduct();
        seckillProduct.setId(id);
        seckillProduct.setState(state);
        if (state == 1 || state == 2) {
            seckillProduct.setApproveTime(new Date());
        }
        seckillDao.updateSeckillProductState(seckillProduct);
    }
}









