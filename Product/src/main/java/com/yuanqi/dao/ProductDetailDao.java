package com.yuanqi.dao;

import com.yuanqi.entity.Product;
import com.yuanqi.entity.ProductDetail;
import com.yuanqi.mapper.ProductDetailMapper;
import com.yuanqi.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductDetailDao {
    @Autowired
    private ProductDetailMapper productDetailMapper;

    public void saveProductDetailInfo(ProductDetail productDetail) {
        productDetailMapper.saveProductDetailInfo(productDetail);
    }
}
