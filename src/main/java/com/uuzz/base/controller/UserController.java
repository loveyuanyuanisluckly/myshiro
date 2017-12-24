package com.uuzz.base.controller;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.uuzz.base.model.User;
import com.uuzz.base.model.UserEnum;
import com.uuzz.base.service.IUserService;
import com.uuzz.common.Constants;
import com.uuzz.common.Message;
import com.uuzz.utils.UserUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.*;


/**
 * 用户信息控制器
 *
 * @author zj
 * @date 2017-11-12
 */
@Controller("userController")
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class);

    @Resource
    private IUserService userService;

    /**
     * 跳转至用户管理页面
     *
     * @return
     */
    @RequestMapping(value = "list")
    public String list() {
        return "view/user/user_list";
    }

    /**
     * 跳转至添加用户界面
     *
     * @return
     */
    @RequestMapping(value = "addUser")
    public String addUser() {
        return "view/user/user_add";
    }

    /**
     * 跳转至用户密码修改页面
     *
     * @return
     */
    @RequestMapping(value = "pswd")
    public String pswd() {
        return "view/user/user_pswd";
    }

    /**
     * 查询用户信息
     *
     * @param page 页数
     * @param rows 每页显示条数
     * @param user 查询条件
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryUsers")
    public Map<String, Object> queryUsers(@RequestParam int page, @RequestParam int rows,User user) {

        PageHelper.startPage(page, rows);
        List<User> users = userService.queryUsers(user);
        PageInfo<User> pageInfo = new PageInfo<>(users);
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("total", pageInfo.getTotal());
        userInfo.put("rows", pageInfo.getList());
        return userInfo;
    }


    /**
     * 跳转至编辑页面
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "findUser")
    public ModelAndView findUser(@RequestParam int userId) {

        User user = userService.findUserById(userId);
        return new ModelAndView("view/user/user_edit", "user", user);
    }

    /**
     * 修改用户信息
     *
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "editUser")
    public Message<Integer, String> editUser(User user) {

        UserEnum userEnum = userService.checkUserInfo(user);
        if (userEnum != UserEnum.OK && userEnum != UserEnum.PSWD_ILLEGAL) {
            return new Message<>(400, userEnum.getMsg());
        }
        userService.updateUserById(user);
        return new Message<>(200, "修改成功");
    }

    @ResponseBody
    @RequestMapping(value = "saveUser")
    public Message<Integer, String> saveUser(User user) {

        UserEnum userEnum = userService.checkUserInfo(user);
        if (userEnum != UserEnum.OK) {
            return new Message<>(400, userEnum.getMsg());
        }
        userService.addUser(user);
        return new Message<>(200, "添加成功");
    }


    @ResponseBody
    @RequestMapping(value = "deleteUsers")
    public Message<Integer, String> deleteUsers(int[] ids) {

        userService.deleteUsersbyIds(ids);
        return new Message<>(200, "删除成功成功");
    }

    @ResponseBody
    @RequestMapping(value = "updatePswd")
    public Message<Integer, String> updatePswd(User user, @RequestParam String newPswd) {

        User dbUser = userService.findUserByName(user.getUserName());
        if (dbUser == null) {
            return new Message<>(400, "账户信息有误！");
        }
        if (!Objects.equals(dbUser.getUserEmail(), user.getUserEmail())) {
            return new Message<>(400, "账户与注册邮箱不一致！");
        }
        if (StringUtils.isBlank(newPswd)) {
            return new Message<>(400, "密码不合法！");
        }

        SimpleHash encryptedPswd, newEncryptedPswd;
        if (Constants.ADMIN_NAME.equals(user.getUserName())) {
            encryptedPswd = UserUtil.
                    encryptedPswd("SHA", user.getUserPswd(), user.getUserName(), 1024);
            newEncryptedPswd = UserUtil.
                    encryptedPswd("SHA", newPswd, user.getUserName(), 1024);
        } else {
            encryptedPswd = UserUtil.
                    encryptedPswd("MD5", user.getUserPswd(), user.getUserName(), 1024);
            newEncryptedPswd = UserUtil.
                    encryptedPswd("MD5", newPswd, user.getUserName(), 1024);
        }

        if (!Objects.equals(dbUser.getUserPswd(), encryptedPswd.toString())) {
            return new Message<>(400, "原密码错误！");
        }

        dbUser.setUserPswd(newEncryptedPswd.toString());
        userService.updateUserById(dbUser);

        return new Message<>(200, "修改密码成功！");
    }


}
