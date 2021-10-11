package com.yuanqi.mapper;

import com.yuanqi.entity.ProductType;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductTypeMapper {
    void saveProductTypeInfo(ProductType productType);
}
