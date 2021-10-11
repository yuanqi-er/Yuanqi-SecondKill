package com.yuanqi.service;

import com.yuanqi.entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 通过 Feign 调用注册后的服务，变成接口！！
 * 就能在这个模块的 Controller 里，用其他模块的服务了！！
 */


@FeignClient(value = "yuanqiProduct")
public interface ProductService {
    @PostMapping("product/listProductBy")
    List<Product> listProductBy(@RequestParam(value = "shopId") int shopId);

    @PostMapping("product/findProductById")
    Product findProductById(@RequestParam(value = "id") int id);
}
