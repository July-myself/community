package com.july.community.service;

import com.july.community.mapper.UserMapper;
import com.july.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;


    public void createOrUpdate(User user) {
        //检查该用户是否已在数据库中
        User dbUser = userMapper.findByAccountId(user.getAccountId());
        if (dbUser == null){
            //不存在，则插入
            user.setTimeCreate(System.currentTimeMillis());
            user.setTimeModified(System.currentTimeMillis());
            userMapper.insert(user);
        }else {
            //存在则修改token
            dbUser.setToken(user.getToken());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setName(user.getName());
            dbUser.setTimeModified(System.currentTimeMillis());
            userMapper.update(dbUser);
        }

    }
}
