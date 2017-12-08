package com.uuzz.common.realms;

import com.uuzz.utils.SpringContextUtil;
import com.uuzz.utils.UserUtil;
import com.uuzz.base.model.User;
import com.uuzz.base.service.IUserService;
import com.uuzz.base.service.imp.UserServiceImp;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 管理员身份认证
 * @author zj
 * @date 207-11-12
 */
public class AdminRealm extends AuthorizingRealm {

    /**
     * 认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        UsernamePasswordToken userToken = (UsernamePasswordToken) token;

        String userName = userToken.getUsername();
        String userPswd = String.valueOf(userToken.getPassword());
        //对页面传来的明文密码进行SHA-1盐值加密:ce2f6417c7e1d32c1d81a797ee0b499f87c5de06
        userPswd = UserUtil.encryptedPswd("SHA-1",userPswd,userName,1024).toString();

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

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String userName = (String)principalCollection.getPrimaryPrincipal();

        //TODO 从数据库中读取
        Set<String> roles = new HashSet<String>();
        if("admin".equals(userName)){
            roles.add("admin");
        }
        roles.add("user");
        return new SimpleAuthorizationInfo(roles);
    }
}
