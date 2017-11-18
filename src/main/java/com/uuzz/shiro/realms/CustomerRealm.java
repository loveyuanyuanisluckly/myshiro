package com.uuzz.shiro.realms;

import com.uuzz.common.util.SpringContextUtil;
import com.uuzz.common.util.UserUtil;
import com.uuzz.user.model.User;
import com.uuzz.user.service.IUserService;
import com.uuzz.user.service.imp.UserServiceImp;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;

import java.util.Date;

/**普通用户身份认证
 * @author zj
 * createTime: 2017/11/4
 */
public class CustomerRealm extends AuthenticatingRealm {

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        UsernamePasswordToken userToken = (UsernamePasswordToken) token;

        String userName = userToken.getUsername();
        String userPswd = String.valueOf(userToken.getPassword());
        //对页面传来的明文密码进行MD5盐值加密:098d2c478e9c11555ce2823231e02ec1
        userPswd = UserUtil.encryptedPswd("MD5",userPswd,userName,1024).toString();

        IUserService userService = SpringContextUtil.getBean("userService", UserServiceImp.class);
        User user = userService.queryUserByNameAndPswd(userName,userPswd);

        if(user==null){
            throw  new UnknownAccountException("用户名或密码不正确！");
        }

        //更新最后一次登陆时间
        user.setLastLoginTime(new Date());
        userService.updateUserById(user);

        return new SimpleAuthenticationInfo(userName,userPswd, ByteSource.Util.bytes(userName),getName());
    }
}
