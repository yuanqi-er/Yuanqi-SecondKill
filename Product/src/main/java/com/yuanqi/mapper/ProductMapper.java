package com.yuanqi.mapper;


import com.yuanqi.entity.Product;
import com.yuanqi.vo.ProductVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    void saveProductInfo(Product product);

    //查询商品
    List<Product> listProductInfo(ProductVo productVo);

    //更新状态
    void updateProductState(Product product);

    //根据id查询商品
    Product findProductById(int id);
}
