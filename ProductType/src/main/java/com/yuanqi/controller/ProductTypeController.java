package com.yuanqi.controller;

import com.yuanqi.entity.ProductType;
import com.yuanqi.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("productType")
public class ProductTypeController {
    @Autowired
    private ProductTypeService productTypeService;

    @RequestMapping(value = "toAddProductType", method = RequestMethod.GET)
    public String toAddProductType() {
        return "toAddProductType";
    }

    @RequestMapping(value = "addProductType", method = RequestMethod.POST)
    public String addProductType(ProductType productType, Model model) {
        if (StringUtils.isEmpty(productType.getProductTypeName())) {
            model.addAttribute("error", "商品类别名称不能为空");
            return "toAddProductType";
        }

        productTypeService.saveProductTypeInfo(productType);

        return "AddProductTypeSuccess";
    }
}








