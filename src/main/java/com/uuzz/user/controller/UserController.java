package com.uuzz.user.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.uuzz.common.Constants;
import com.uuzz.common.util.LoggerUtil;
import com.uuzz.common.util.UserUtil;
import com.uuzz.common.vcode.Captcha;
import com.uuzz.common.vcode.GifCaptcha;
import com.uuzz.common.vcode.SpecCaptcha;
import com.uuzz.user.model.Menu;
import com.uuzz.user.model.User;
import com.uuzz.user.service.IUserService;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.SimpleHash;
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
import java.util.*;

import static com.uuzz.user.model.Menu.State.CLOSED;
import static com.uuzz.user.model.Menu.State.OPEN;


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
     * 登录跳转
     *
     * @return
     */
    @RequestMapping(value = "toLogin", method = RequestMethod.GET)
    public ModelAndView toLogin(HttpServletRequest request) {

        return new ModelAndView("login");
    }

    /**
     * 注册跳转
     *
     * @return
     */
    @RequestMapping(value = "toRegister", method = RequestMethod.GET)
    public ModelAndView toRegister() {

        return new ModelAndView("register");
    }

    /**
     * 跳转至首页
     * @return
     */
    @RequestMapping(value="index",method=RequestMethod.GET)
    public ModelAndView userIndex(){

        return new ModelAndView("/view/index");
    }

    /**
     * 注册|登陆
     *
     * @param request
     * @param vcode
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public Map<String, Object> register(HttpServletRequest request, String vcode, User user) {

        Map<String, Object> result = null;
        try {
            result = new HashMap<>();
            result.put("status", 400);

            if (validateRegisteInfo(request, vcode, user, result)) return result;

            Date date = new Date();
            user.setCreateTime(date);
            user.setLastLoginTime(date);
            String userPswd = user.getUserPswd();
            String userName = user.getUserName();
            SimpleHash md5Pswd = UserUtil.encryptedPswd("MD5", userPswd, userName, 1024);
            //把密码md5
            user.setUserPswd(md5Pswd.toString());
            userService.addUser(user);
            logger.info(String.format("注册成功，注册信息：%s",JSONObject.toJSON(user)));

            UserUtil.login(userName, userPswd);
            result.put("message", "注册成功！");
            result.put("status", 200);
            result.put("back_url",request.getContextPath()+"/user/index.do");
            logger.info(String.format("登陆成功！"));
        } catch (Exception e) {
            result.put("message", "系统注册发生未知异常，请尽快联系管理员！！");
            logger.error("账号注册时发生未知异常！", e);
        }
        return result;
    }

    /**
     * 校验用户注册信息
     * @param request
     * @param vcode
     * @param user
     * @param result
     * @return
     */
    private boolean validateRegisteInfo(HttpServletRequest request, String vcode, User user, Map<String, Object> result) {
        String rgisterCode = (String)request.getSession().getAttribute(Constants.VERIFY_CODE);
        logger.debug(String.format("注册验证码：%s",rgisterCode));
        if (!Objects.equals(rgisterCode, vcode)) {
            result.put("message", "验证码不正确！");
            return true;
        }
        String email = user.getUserEmail();
        userService.findUserByEmail(email);
        User userDb = userService.findUserByEmail(email);
        if (null != userDb) {
            result.put("message", "帐号|Email已经存在！");
            return true;
        }
        return false;
    }

    /**
     * 用户登陆
     *
     * @param userName
     * @param userPswd
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public Map<String, Object> login(@RequestParam("userName") String userName, @RequestParam("userPswd") String userPswd) {

        //返回页面结果
        Map<String, Object> result = new HashMap<>();
        result.put("status", 400);

        Subject subject = SecurityUtils.getSubject();

        if (!subject.isAuthenticated()) {//没有被认证
            try {
                UserUtil.login(userName, userPswd);
                result.put("back_url","user/index.do");
                result.put("status", 200);
            } catch (UnknownAccountException e) {
                result.put("message", "用户名或密码不正确！");
                logger.info(String.format("%s 账号不存在或密码有误！", userName));
            } catch (LockedAccountException e) {
                result.put("message", "您的账户被锁定！");
                logger.warn(String.format("%s 账号被锁定！", userName));
            } catch (DisabledAccountException e) {
                result.put("message", "您的账户被禁用！");
                logger.warn(String.format("%s 账号被禁用！", userName));
            } catch (ConcurrentAccessException e) {
                result.put("message", "您的账户在其它地方已登陆！");
                logger.warn(String.format("%s 账号异地已登陆！", userName));
            } catch (Exception e) {
                result.put("message", "系统登陆发生未知异常，请尽快联系管理员！！");
                logger.error(String.format("%s 账号在登陆时发生未知异常！", userName), e);
            }
        }
        return result;
    }

    /**
     * 登出
     *
     * @return
     */
    @RequestMapping("logout")
    public String logout() {

        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            subject.logout();
        }
        return "redirect:/login.jsp";
    }


    /**
     * 获取验证码（Gif版本）
     *
     * @param response
     */
    @RequestMapping(value = "getGifCode", method = RequestMethod.GET)
    public void getGifCode(HttpServletResponse response, HttpServletRequest request) {
        try {
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/gif");
            /**
             * gif格式动画验证码
             * 宽，高，位数。
             */
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
     * @param response
     */
    @RequestMapping(value = "getJPGCode", method = RequestMethod.GET)
    public void getJPGCode(HttpServletResponse response, HttpServletRequest request) {
        try {
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/jpg");
            /**
             * jgp格式验证码
             * 宽，高，位数。
             */
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

    @ResponseBody
    @RequestMapping(value = "loadMenu" ,method = RequestMethod.GET)
    public JSONArray loadMenu(){

        JSONArray menus = parseMenus(0,getMenus());
        return menus;
    }

    private JSONArray parseMenus(int pid,List<Menu> menus){
        JSONArray nodes = new JSONArray();
        Iterator<Menu> iterator = menus.iterator();
        while (iterator.hasNext()){
            Menu menu = iterator.next();
            if(menu.getPid() == pid){
                JSONObject node = new JSONObject();
                node.put("id",menu.getId());
                node.put("text",menu.getText());
                Map attributes = new HashMap();
                attributes.put("url",menu.getUrl());
                attributes.put("pid",pid);
                node.put("attributes",attributes);
                //将已经解析的数据从集合中剔除以提高效率
                iterator.remove();
                JSONArray children = parseMenus(menu.getId(), menus);
                if (children.size()>0){
                    node.put("state",menu.getState().getOpt());
                    node.put("children", children);
                }
                nodes.add(node);
            }
        }
        return nodes;
    }

    private List<Menu> getMenus(){
        List<Menu> menus = new ArrayList<>();

        Menu root = new Menu();
        root.setId(1);
        root.setPid(0);
        root.setState(CLOSED);
        root.setText("菜单");
        root.setUrl("http://www.baidu.com");
        menus.add(root);


        Menu menu = new Menu();
        menu.setId(2);
        menu.setPid(1);
        menu.setState(CLOSED);
        menu.setText("java");
        menu.setUrl("http://www.baidu.com");
        menus.add(menu);

        Menu menu1 = new Menu();
        menu1.setId(3);
        menu1.setPid(1);
        menu1.setState(CLOSED);
        menu1.setText("php");
        menu1.setUrl("http://www.baidu.com");
        menus.add(menu1);

        Menu menu2 = new Menu();
        menu2.setId(4);
        menu2.setPid(3);
        menu2.setState(CLOSED);
        menu2.setText("666");
        menu2.setUrl("http://www.baidu.com");
        menus.add(menu2);

        return menus;
    }

}
