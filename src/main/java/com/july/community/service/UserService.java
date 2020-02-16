package com.july.community.service;

import com.july.community.mapper.UserMapper;
import com.july.community.model.User;
import com.july.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;


    public void createOrUpdate(User user) {
        //检查该用户是否已在数据库中
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() == 0){
            //不存在，则插入
            user.setTimeCreate(System.currentTimeMillis());
            user.setTimeModified(System.currentTimeMillis());
            userMapper.insert(user);
        }else {
            //存在则修改token
            User dbUser = users.get(0);
            User updateUser = new User();
            updateUser.setToken(user.getToken());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setName(user.getName());
            updateUser.setTimeModified(System.currentTimeMillis());
            UserExample example = new UserExample();
            example.createCriteria().andAccountIdEqualTo(dbUser.getAccountId());
            userMapper.updateByExampleSelective(updateUser,example);
        }

    }
}
