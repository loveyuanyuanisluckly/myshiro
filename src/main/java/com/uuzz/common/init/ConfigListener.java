package com.uuzz.common.init;

import com.uuzz.common.Constants;
import com.uuzz.utils.LoggerUtil;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 初始化监听器
 * @author zj
 * createTime: 2017/11/5
 */
public class ConfigListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        LoggerUtil.debug(ConfigListener.class,"start Initialized base config...");
        preparedBaseParam(servletContextEvent);
        LoggerUtil.debug(ConfigListener.class,"end Initialized base config...");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

        LoggerUtil.debug(ConfigListener.class,"this server is shutdown!");
    }

    /**
     * 初始化一些基本参数
     */
    private void preparedBaseParam(ServletContextEvent servletContextEvent){

        //为页面初始化bashPath参数 "basePath", request.getContextPath()
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute(Constants.BASE_PATH,servletContext.getContextPath());
    }
}
