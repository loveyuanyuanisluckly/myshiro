package com.uuzz.user.service;

import com.uuzz.user.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 用户服务
 * @author zj
 * createTime: 2017-11-3
 */
public interface IUserService {

    /**
     * 通过登陆账号与密码查询用户信息
     * @param userName
     * @param userPswd
     * @return
     */
    User queryUserByNameAndPswd(String userName, String userPswd);

    /**
     * 通过id更新用户信息
     * @param user
     */
    void updateUserById(User user);

    /**
     * 通过用户邮箱查询用户
     * @param email
     * @return
     */
    User findUserByEmail(String email);

    /**
     * 新增用户
     * @param user
     */
    void addUser(User user);

}
