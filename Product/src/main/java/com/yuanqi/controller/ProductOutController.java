package com.yuanqi.controller;

import com.yuanqi.entity.Product;
import com.yuanqi.form.ProductForm;
import com.yuanqi.service.ProductDetailService;
import com.yuanqi.service.ProductService;
import com.yuanqi.vo.ProductCondition;
import com.yuanqi.vo.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductOutController {
    @Autowired
    private ProductService productService;

    @PostMapping("listProductBy")
    public List<Product> listProductBy(int shopId) {
        List<Product> products = productService.listProductInfo(shopId);
        return products;
    }

    @PostMapping("findProductById")
    public Product findProductById(int id) {
        Product product = productService.findProductById(id);
        return product;
    }



}
