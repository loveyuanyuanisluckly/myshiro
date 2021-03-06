package com.uuzz.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 针对用户信息工具
 * @author zj
 * createTime: 2017/11/4
 */
public class UserUtil {

    /**
     * 返回加密数据
     * @param hashAlgorithmName 加密算法：MD5|SHA等
     * @param credentails 加密数据
     * @param salt 盐
     * @param hashIterations 加密次数
     * @return
     */
    public static SimpleHash encryptedPswd(String hashAlgorithmName,Object credentails,Object salt,int hashIterations ){

        return new SimpleHash(hashAlgorithmName,credentails,salt,hashIterations);
    }

    /**
     * 用户登陆
     * @param userName
     * @param userPswd
     */
    public static void login(String userName,String userPswd){
        UsernamePasswordToken token = new UsernamePasswordToken(userName,userPswd);
        SecurityUtils.getSubject().login(token);
    }

    /**
     * 校验邮箱是否合法
     * @param string
     * @return
     */
    public static boolean isEmail(String string) {
        if (string == null)
            return false;
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(string);
        return m.matches();
    }

    public static void main(String[] args) {

        System.out.println(encryptedPswd("MD5","123456","user",1024));
    }
}
