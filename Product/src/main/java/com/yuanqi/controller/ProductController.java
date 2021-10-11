package com.yuanqi.controller;

import com.yuanqi.entity.Product;
import com.yuanqi.form.ProductForm;
import com.yuanqi.service.ProductDetailService;
import com.yuanqi.service.ProductService;
import com.yuanqi.vo.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductDetailService productDetailService;

    @GetMapping("toApplyProduct")
    public String toAddProduct() {
        return "toAddProduct";
    }

    @PostMapping("applyProduct")
    public String addProduct(ProductForm productForm, Model model) {
        if (StringUtils.isEmpty(productForm.getProductName())) {
            model.addAttribute("error", "商品名称不能为空");
            return "toAddProduct";
        }
        if (StringUtils.isEmpty(productForm.getProductTitle())) {
            model.addAttribute("error", "商品标题不能为空");
            return "toAddProduct";
        }
        if (StringUtils.isEmpty(productForm.getProductPrice())) {
            model.addAttribute("error", "商品价格不能为空");
            return "toAddProduct";
        }

        productService.saveProductInfo(productForm);

        return "AddProductSuccess";
    }

    @GetMapping("listProduct")
    public String listProduct(ProductVo productVo, Model model) {

        List<Product> listProducts = productService.listProductInfo(-1);
        model.addAttribute("listProducts", listProducts);

        return "listProduct";
    }

    @GetMapping("updateState")
    public String updateState(int id, int state) {
        Product product = new Product();
        product.setId(id);
        product.setState(state);
        productService.updateProductState(product);

        return "redirect:listProduct";
    }

}
