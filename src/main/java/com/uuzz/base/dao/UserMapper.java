package com.uuzz.base.dao;

import com.uuzz.base.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    /**
     * 根据主键删除用户信息
     * @param id
     * @return
     */
    int deleteUserById(Integer id);

    /**
     * 新增用户部分信息
     * @param record
     * @return
     */
    int addUser(User record);

    /**
     * 通过主键查询用户信息
     * @param id
     * @return
     */
    User queryUserById(Integer id);

    /**
     * 更新用户信息
     * @param record
     * @return
     */
    int updateUserById(User record);

    /**
     * 通过用户名与密码查询用户信息
     * @param userName
     * @return
     */
    User findUserByName(@Param("userName") String userName);

    /**
     * 通过用户名与密码查询用户信息
     * @param userName
     * @param userPswd
     * @return
     */
    User queryUserByNameAndPswd(@Param("userName") String userName,@Param("userPswd") String userPswd);

    /**
     * 通过注册邮箱查询用户信息
     * @param email
     * @return
     */
    User findUserByEmail(@Param("email") String email);

    /**
     * 查询用户信息
     * @return
     */
    List<User> queryUsers(User user);

    /**
     * 通过id批量删除用户信息
     * @param ids
     */
    void deleteUsersbyIds(@Param("ids") int[] ids);
}