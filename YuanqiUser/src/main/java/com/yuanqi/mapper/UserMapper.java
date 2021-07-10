package com.yuanqi.mapper;

import com.yuanqi.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User findUserById(int userId);

    //用于注册，保存用户信息
    void saveUserInfo(User userInfo);

    //通过用户名找user
    User findUserByAccount(String account);

}
