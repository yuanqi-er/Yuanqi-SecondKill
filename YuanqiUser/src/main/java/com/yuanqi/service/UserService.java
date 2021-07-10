package com.yuanqi.service;

import com.yuanqi.dao.UserDao;
import com.yuanqi.form.UserRegisterForm;
import com.yuanqi.mapper.UserMapper;
import com.yuanqi.entity.User;
import com.yuanqi.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public User findUserById(int id){
        return userDao.findUserById(id);
    }

    public void saveUserInfo(UserRegisterForm userRegisterForm) throws Exception {
        User user = new User();
        user.setAccount(userRegisterForm.getAccount());
        user.setBirthday(userRegisterForm.getBirthday());
        user.setTelphone(userRegisterForm.getTelphone());
        user.setQq(userRegisterForm.getQq());
        user.setWeixin(userRegisterForm.getWeixin());

        String password = userRegisterForm.getPassword();
        user.setOriginalPassword(password);     //明文
        user.setEncryptionPassword(Md5Util.md5(password, Md5Util.md5Key));     //密文
        userDao.saveUserInfo(user);
    }

    /**
     *  验证密码
     */
    public boolean verifyUserAccount(UserRegisterForm userRegisterForm) throws Exception {
        String account = userRegisterForm.getAccount();
        String password = userRegisterForm.getPassword();

        User user = userDao.findUserByAccount(account);
        if (user == null) {
            return false;
        }
        //查询数据库中的密文
        String encryptionPassword = user.getEncryptionPassword();
        //将用户输入的密码加密后，对比是否相同
        return Md5Util.verify(password, Md5Util.md5Key, encryptionPassword);
    }

}
