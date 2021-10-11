package com.yuanqi.service;

import com.yuanqi.dao.ProductDao;
import com.yuanqi.dao.ProductDetailDao;
import com.yuanqi.entity.Product;
import com.yuanqi.entity.ProductDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductDetailService {
    @Autowired
    private ProductDetailDao productDetailDao;

    public void saveProductDetailInfo(ProductDetail productDetail) {
        productDetailDao.saveProductDetailInfo(productDetail);
    }
}
