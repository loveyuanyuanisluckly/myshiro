package com.uuzz.base.service;

import com.uuzz.base.model.User;
import com.uuzz.base.model.UserEnum;
import com.uuzz.common.Message;

import java.util.List;
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
     * 通过登陆账号查询用户信息
     * @param userName
     * @return
     */
    User findUserByName(String userName);

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

    /**
     * 查询用户信息
     * @return
     */
    List<User> queryUsers(User user);

    /**
     * 通过id查询用户信息
     * @param id
     * @return
     */
    User findUserById(Integer id);

    /**
     * 校验用户名与注册邮箱
     * @param user 用户
     * @return
     */
    UserEnum checkUserInfo(User user);

    /**
     * 通过id批量删除用户信息
     * @param ids
     */
    void deleteUsersbyIds(int[] ids);

}
