package com.uuzz.base.service.imp;

import com.uuzz.base.dao.UserMapper;
import com.uuzz.base.model.User;
import com.uuzz.base.model.UserEnum;
import com.uuzz.base.service.IUserService;
import com.uuzz.common.Message;
import com.uuzz.utils.UserUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    public User findUserByName(String userName) {

        return userMapper.findUserByName(userName);
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
        Date date = new Date();
        user.setCreateTime(date);
        SimpleHash md5Pswd = UserUtil.
                encryptedPswd("MD5", user.getUserPswd(), user.getUserName(), 1024);
        //把密码md5
        user.setUserPswd(md5Pswd.toString());
        userMapper.addUser(user);
    }

    @Override
    public List<User> queryUsers(User user) {
        return userMapper.queryUsers(user);
    }

    @Override
    public User findUserById(Integer id) {
        return userMapper.queryUserById(id);
    }

    @Override
    public UserEnum checkUserInfo(User user) {

        if(user == null){
            return UserEnum.NULL;
        }
        if(StringUtils.isEmpty(user.getUserName())||
                !UserUtil.isEmail(user.getUserEmail())){
            return UserEnum.NAME_ILLEGAL;
        }
        if(StringUtils.isEmpty(user.getUserPswd())){
            return UserEnum.PSWD_ILLEGAL;
        }
        User dbUser = findUserByEmail(user.getUserEmail());
        if(dbUser!=null){
            return UserEnum.EMAIL_IS_EXSIST;
        }
        User dBUser = findUserByName(user.getUserName());
        if(dBUser != null){
            return UserEnum.ACOUNT_IS_EXSIST;
        }
        return UserEnum.OK;
    }

    @Override
    public void deleteUsersbyIds(int[] ids) {
        userMapper.deleteUsersbyIds(ids);
    }
}
