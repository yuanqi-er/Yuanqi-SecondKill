package com.yuanqi.mapper;


import com.yuanqi.entity.Product;
import com.yuanqi.entity.ProductDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductDetailMapper {
    void saveProductDetailInfo(ProductDetail productDetail);

}
