package com.yuanqi.service;

import com.yuanqi.dao.MerchantDao;
import com.yuanqi.entity.Merchant;
import com.yuanqi.entity.User;
import com.yuanqi.form.MerchantCheckInForm;
import com.yuanqi.form.UserRegisterForm;
import com.yuanqi.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MerchantService {
    @Autowired
    private MerchantDao merchantDao;

    public Merchant findMerchantById(int id){
        return merchantDao.findMerchantById(id);
    }

    public void saveMerchantInfo(MerchantCheckInForm merchantCheckInForm) throws Exception {
        Merchant merchant = new Merchant();

        merchant.setCreatetime(new Date());
        merchant.setAccount(merchantCheckInForm.getAccount());
        merchant.setTelphone(merchantCheckInForm.getTelphone());
        merchant.setProvince(merchantCheckInForm.getProvince());
        merchant.setCity(merchantCheckInForm.getCity());
        merchant.setAddress(merchantCheckInForm.getAddress());
        merchant.setName(merchantCheckInForm.getName());

        String password = merchantCheckInForm.getPassword();
        merchant.setOriginalPassword(password);     //明文
        merchant.setEncryptionPassword(Md5Util.md5(password, Md5Util.md5Key));     //密文
        merchantDao.saveMerchantInfo(merchant);
    }

    /**
     *  验证密码
     */
    public boolean verifyMerchantAccount(MerchantCheckInForm merchantCheckInForm) throws Exception {
        String account = merchantCheckInForm.getAccount();
        String password = merchantCheckInForm.getPassword();

        Merchant merchant = merchantDao.findMerchantByAccount(account);
        if (merchant == null) {
            return false;
        }
        //查询数据库中的密文
        String encryptionPassword = merchant.getEncryptionPassword();
        //将商家输入的密码加密后，对比是否相同
        return Md5Util.verify(password, Md5Util.md5Key, encryptionPassword);
    }

}
