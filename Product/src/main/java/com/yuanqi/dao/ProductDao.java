package com.yuanqi.dao;

import com.yuanqi.entity.Product;
import com.yuanqi.mapper.ProductMapper;
import com.yuanqi.vo.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductDao {
    @Autowired
    private ProductMapper productMapper;

    public void saveProductInfo(Product product) {
        productMapper.saveProductInfo(product);
    }

    public List<Product> listProductInfo(ProductVo productVo) {
        return productMapper.listProductInfo(productVo);
    }

    public void updateProductState(Product product) {
        productMapper.updateProductState(product);
    }

    public Product findProductById(int id) {
        return productMapper.findProductById(id);
    }
}
