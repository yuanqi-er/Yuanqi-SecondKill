package com.yuanqi.dao;

import com.yuanqi.entity.Merchant;
import com.yuanqi.mapper.MerchantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MerchantDao {
    @Autowired
    private MerchantMapper merchantMapper;

    public Merchant findMerchantById(int id){
        return merchantMapper.findMerchantById(id);
    }

    public void saveMerchantInfo(Merchant merchant) {
        merchantMapper.saveMerchantInfo(merchant);
    }

    public Merchant findMerchantByAccount(String account) {
        return merchantMapper.findMerchantByAccount(account);
    }
}
