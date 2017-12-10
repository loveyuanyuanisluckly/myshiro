package com.uuzz.base.service;

import com.uuzz.base.model.Menu;

import java.util.List;

/**
 * @author zj
 * createTime: 2017/12/9
 */
public interface IMenuService {

    /**
     * 查询多有菜单
     * @return List
     */
    List<Menu> getMenus();

}
