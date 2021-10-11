package com.yuanqi.service;

import com.yuanqi.dao.ProductTypeDao;
import com.yuanqi.entity.ProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductTypeService {
    @Autowired
    private ProductTypeDao productTypeDao;

    public void saveProductTypeInfo(ProductType productType) {
        productTypeDao.saveProductTypeInfo(productType);
    }
}
