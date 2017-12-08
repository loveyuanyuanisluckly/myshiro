package com.uuzz.base.model;

import java.util.Date;

/**
 * 用户信息
 * @author zj
 * createTime: 2017/11/3
 */
public class User {

    /*主键*/
    private Integer id;

    /*用户名称/账号*/
    private String userName;

    /*用户密码*/
    private String userPswd;

    /*用户邮箱*/
    private String userEmail;

    /*创建时间*/
    private Date createTime;

    /*最后一次登陆时间*/
    private Date lastLoginTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserPswd() {
        return userPswd;
    }

    public void setUserPswd(String userPswd) {
        this.userPswd = userPswd == null ? null : userPswd.trim();
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail == null ? null : userEmail.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}