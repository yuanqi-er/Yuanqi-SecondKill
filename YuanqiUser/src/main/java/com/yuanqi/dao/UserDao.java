package com.yuanqi.dao;

import com.yuanqi.mapper.UserMapper;
import com.yuanqi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDao {
    @Autowired
    private UserMapper userMapper;

    public User findUserById(int id){
        return userMapper.findUserById(id);
    }

    public void saveUserInfo(User user) {
        userMapper.saveUserInfo(user);
    }

    public User findUserByAccount(String account) {
        return userMapper.findUserByAccount(account);
    }
}
