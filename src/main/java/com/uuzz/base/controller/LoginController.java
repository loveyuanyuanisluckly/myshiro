package com.uuzz.base.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.uuzz.base.model.Menu;
import com.uuzz.base.model.User;
import com.uuzz.base.model.UserEnum;
import com.uuzz.base.service.IMenuService;
import com.uuzz.base.service.IUserService;
import com.uuzz.common.Constants;
import com.uuzz.common.Message;
import com.uuzz.utils.LoggerUtil;
import com.uuzz.utils.UserUtil;
import com.uuzz.utils.vcode.Captcha;
import com.uuzz.utils.vcode.GifCaptcha;
import com.uuzz.utils.vcode.SpecCaptcha;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ConcurrentAccessException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static com.uuzz.base.model.Menu.State.CLOSED;

/**
 * @author zj
 * @desc 登陆
 * @date 2017/12/8
 * @since 1.7
 */
@Controller("loginController")
@RequestMapping("/login")
public class LoginController {

    private static final Logger logger = Logger.getLogger(LoginController.class);

    @Resource
    private IUserService userService;

    @Resource
    private IMenuService menuService;

    @RequestMapping(value = "go2Login")
    public String go2Login(){
        return "login";
    }

    @RequestMapping(value = "go2Register")
    public String go2Register(){
        return "register";
    }

    /**
     * 注册
     * @param request 请求对象
     * @param vcode 验证码
     * @param user 当前用户
     * @return Map
     */
    @ResponseBody
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public Message<Integer, String> register(HttpServletRequest request, String vcode, User user) {
        try {

            Message<Integer, String> msg = validateRegisteInfo(request, vcode, user);
            if(msg.state != 0)return msg;

            //添加用户信息
            user.setLastLoginTime(new Date());
            userService.addUser(user);
            logger.info(String.format("注册成功，注册信息：%s", JSONObject.toJSON(user)));

            //登陆
            UserUtil.login(user.getUserName(), user.getUserPswd());
            logger.info("登陆成功！");
            return new Message(200,"注册成功");
        } catch (Exception e) {
            logger.error("账号注册时发生未知异常！", e);
            return new Message(500,"系统注册发生未知异常，请尽快联系管理员！");
        }
    }

    /**
     * 校验用户注册信息
     * @param request 请求对象
     * @param vcode 验证码
     * @param user 用户信息
     * @return boolean
     */
    private Message<Integer,String> validateRegisteInfo(HttpServletRequest request, String vcode, User user) {

        String rgisterCode = (String)request.getSession().getAttribute(Constants.VERIFY_CODE);
        logger.debug(String.format("注册验证码：%s",rgisterCode));
        if (!Objects.equals(rgisterCode, vcode)) {
            new Message(UserEnum.YZM_IS_ERROR.getCode(),UserEnum.YZM_IS_ERROR.getMsg());
        }
        UserEnum userEnum = userService.checkUserInfo(user);
        return  new Message(userEnum.getCode(),userEnum.getMsg());
    }


    /**
     * 用户登陆
     *
     * @param userName 用户名称
     * @param userPswd 用户密码
     * @return Map
     */
    @ResponseBody
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public Message<Integer,String> login(HttpServletRequest request,@RequestParam("userName") String userName, @RequestParam("userPswd") String userPswd) {

        //返回页面结果
        Message<Integer,String> msg;
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.isAuthenticated();
            if(!subject.isAuthenticated()){
                UserUtil.login(userName, userPswd);
            }
            msg = new Message<>(200,"登陆成功！");
        } catch (UnknownAccountException e) {
            msg =  new Message<>(400,"用户名或密码不正确！");
            logger.info(String.format("%s 账号不存在或密码有误！", userName));
        } catch (LockedAccountException e) {
            msg =  new Message<>(400,"您的账户被锁定！");
            logger.warn(String.format("%s 账号被锁定！", userName));
        } catch (DisabledAccountException e) {
            msg =  new Message<>(400,"您的账户被禁用！");
            logger.warn(String.format("%s 账号被禁用！", userName));
        } catch (ConcurrentAccessException e) {
            msg =  new Message<>(400,"您的账户在其它地方已登陆！");
            logger.warn(String.format("%s 账号异地已登陆！", userName));
        } catch (Exception e) {
            msg =  new Message<>(400,"系统登陆发生未知异常，请尽快联系管理员！！");
            logger.error(String.format("%s 账号在登陆时发生未知异常！", userName), e);
        }
        return msg;
    }

    /**
     * 登出
     * @return String
     */
    @RequestMapping("logout")
    public String logout() {

        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            subject.logout();
        }
        return "forward:login/go2Login.do";
    }

    /**
     * 获取验证码（Gif版本）
     * @param response 响应对象
     */
    @RequestMapping(value = "getGifCode", method = RequestMethod.GET)
    public void getGifCode(HttpServletResponse response, HttpServletRequest request) {
        try {
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/gif");

            Captcha captcha = new GifCaptcha(146, 42, 4);
            //输出
            ServletOutputStream out = response.getOutputStream();
            captcha.out(out);
            out.flush();
            //存入session
            LoggerUtil.fmtDebug(getClass(), "验证码存入回话：%s%", captcha.text().toLowerCase());
            request.getSession().setAttribute(Constants.VERIFY_CODE, captcha.text().toLowerCase());
        } catch (Exception e) {
            LoggerUtil.fmtError(getClass(), e, "获取验证码异常：%s", e.getMessage());
        }
    }

    /**
     * 获取验证码（jpg版本）
     *
     * @param response 响应
     */
    @RequestMapping(value = "getJPGCode", method = RequestMethod.GET)
    public void getJPGCode(HttpServletResponse response, HttpServletRequest request) {
        try {
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/jpg");

            Captcha captcha = new SpecCaptcha(146, 33, 4);
            //输出
            captcha.out(response.getOutputStream());
            HttpSession session = request.getSession(true);
            //存入Session
            session.setAttribute(Constants.VERIFY_CODE, captcha.text().toLowerCase());
        } catch (Exception e) {
            LoggerUtil.fmtError(getClass(), e, "获取验证码异常：%s", e.getMessage());
        }
    }

    /**
     * 跳转至登陆首页
     * @return ModelAndView
     */
    @RequestMapping(value="welcome")
    public ModelAndView welcome(){

        return new ModelAndView("/layout/main");
    }

    /**
     * 加载菜单树
     * @return JSONArray
     */
    @ResponseBody
    @RequestMapping(value = "loadMenu" ,method = RequestMethod.GET)
    public JSONArray loadMenu(){
        return parseMenus(0,menuService.getMenus());
    }

    private JSONArray parseMenus(int pid,List<Menu> menus){
        JSONArray nodes = new JSONArray();
        Iterator<Menu> iterator = menus.iterator();
        while (iterator.hasNext()){
            Menu menu = iterator.next();
            if(menu.getPid() == pid){
                JSONObject node = new JSONObject();
                node.put("id",menu.getId());
                node.put("text",menu.getMenuName());
                JSONObject attributes = new JSONObject();
                attributes.put("url",menu.getMenuUrl());
                attributes.put("pid",pid);
                node.put("attributes",attributes);
                JSONArray children = parseMenus(menu.getId(), menus);
                if (children.size()>0){
                    node.put("state",CLOSED.getOpt());
                    node.put("children", children);
                }
                nodes.add(node);
            }
        }
        return nodes;
    }

}
