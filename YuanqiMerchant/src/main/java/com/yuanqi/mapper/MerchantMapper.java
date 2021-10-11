package com.yuanqi.mapper;

import com.yuanqi.entity.Merchant;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MerchantMapper {
    Merchant findMerchantById(int merchantId);

    void saveMerchantInfo(Merchant merchant);

    Merchant findMerchantByAccount(String account);
}
