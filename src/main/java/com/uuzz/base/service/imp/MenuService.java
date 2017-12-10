package com.uuzz.base.service.imp;

import com.uuzz.base.dao.MenuMapper;
import com.uuzz.base.model.Menu;
import com.uuzz.base.service.IMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zj
 * createTime: 2017/12/9
 */
@Service("menuService")
public class MenuService implements IMenuService {

    @Resource
    MenuMapper menuMapper;

    @Override
    public List<Menu> getMenus() {
        return menuMapper.getMenus();
    }
}
