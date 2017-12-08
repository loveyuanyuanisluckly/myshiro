package com.uuzz.base.controller;

import com.uuzz.base.service.IUserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;


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


    @RequestMapping(value = "list")
    public String list(){
        return "view/user/success";
    }

}
