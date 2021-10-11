package com.yuanqi.dao;

import com.yuanqi.entity.ProductType;
import com.yuanqi.mapper.ProductTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductTypeDao {

    @Autowired
    private ProductTypeMapper productTypeMapper;

    public void saveProductTypeInfo(ProductType productType) {
        productTypeMapper.saveProductTypeInfo(productType);
    }
}
