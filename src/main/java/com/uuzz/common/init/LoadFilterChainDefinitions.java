package com.uuzz.common.init;

/**
 * @author zj
 * createTime: 2017/11/4
 */

import com.uuzz.utils.LoggerUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 初始化加载权限与角色定义
 */
public class LoadFilterChainDefinitions {

    public Map<String,String> load(){

        Map<String,String> definitions = new LinkedHashMap<>();
        InputStream in = null;
        try {
            in = this.getClass().getClassLoader().getResourceAsStream("property/base_auth.properties");
            OrderedProperties properties = new OrderedProperties();
            properties.load(in);

            Set<String> keys = properties.stringPropertyNames();
            for (String key:keys) {
                definitions.put(key,properties.getProperty(key));
            }
        } catch (IOException e) {
            e.getStackTrace();
            LoggerUtil.fmtError(getClass(),"initialize system base_auth in zhe %s is error!" ,getClass().getName());
        }finally {

            try {
                if(in != null)
                in.close();
            } catch (IOException e) {
                e.getStackTrace();
                LoggerUtil.fmtError(getClass(),"in zhe %s java.io.InputStream is not safely close" ,getClass().getName());
            }

        }

        return definitions;
    }
}
