package com.uuzz.base.service.imp;

import com.uuzz.base.dao.UserMapper;
import com.uuzz.base.model.User;
import com.uuzz.base.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zj
 * createTime: 2017/11/3
 */
@Service("userService")
public class UserServiceImp implements IUserService{

    @Resource
    private UserMapper userMapper;

    @Override
    public User queryUserByNameAndPswd(String userName, String userPswd) {

        return userMapper.queryUserByNameAndPswd(userName,userPswd);
    }

    @Override
    public void updateUserById(User user) {
        userMapper.updateUserById(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userMapper.findUserByEmail(email);
    }

    @Override
    public void addUser(User user) {
        userMapper.addUser(user);
    }
}