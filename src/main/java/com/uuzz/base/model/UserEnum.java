package com.uuzz.base.model;

/**
 * @author zj
 * 用户校验枚举
 * createTime: 2017/12/17
 */
public enum  UserEnum{
    OK(0,"通过"),
    NULL(-1,"用户信息为空"),
    NAME_ILLEGAL(1,"用户名不合法"),
    EMAIL_ILLEGAL(2,"邮箱不合法"),
    PSWD_ILLEGAL(3,"密码不合法"),
    ACOUNT_IS_EXSIST(4,"账户已注册"),
    EMAIL_IS_EXSIST(5,"邮箱已注册"),
    YZM_IS_ERROR(6,"验证码不正确");

    int code;
    String msg;
    UserEnum(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

