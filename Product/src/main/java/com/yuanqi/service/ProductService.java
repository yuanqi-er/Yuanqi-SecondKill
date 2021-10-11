package com.yuanqi.service;

import com.yuanqi.dao.ProductDao;
import com.yuanqi.entity.Product;
import com.yuanqi.form.ProductForm;
import com.yuanqi.vo.ProductCondition;
import com.yuanqi.vo.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductDao productDao;

    public void saveProductInfo(ProductForm productForm) {
        Product product = new Product();
        product.setProductPrice(productForm.getProductPrice());
        product.setProductName(productForm.getProductName());
        product.setProductTitle(productForm.getProductTitle());
        product.setMerchantId(productForm.getMerchantId());
        product.setProductDiscounts(productForm.getProductDiscounts());
        product.setProductInventory(productForm.getProductInventory());
        product.setProductTypeId(productForm.getProductTypeId());
        product.setProductPictureUrl(productForm.getProductPictureUrl());
        product.setCreateTime(new Date());
        product.setState(0);

        productDao.saveProductInfo(product);
    }


    /**
     *  根据shopId，state的状态显示 商品列表
     */
    public List<Product> listProductInfo(int shopId) {
        ProductVo productVo = new ProductVo();
        ProductCondition productCondition = new ProductCondition();

        //ShopId为-1，显示所有商品
        if(shopId != -1) {
            productCondition.setShopId(shopId);
            productCondition.setState(1);   //审核通过的商品才能显出出来，从而发布秒杀
        }

        productVo.setProductCondition(productCondition);
        return productDao.listProductInfo(productVo);
    }

    public void updateProductState(Product product) {
        //更新状态后，设置修改时间
        product.setUpdateTime(new Date());
        //如果审核通过或者被退回，修改审核时间
        if (product.getState() == 1 || product.getState() == 2) {
            product.setApproveTime(new Date());
        }
        productDao.updateProductState(product);
    }

    public Product findProductById(int id) {
        return productDao.findProductById(id);
    }
}
