package com.uuzz.base.model;

/**
 * @author zj
 * @desc 菜单
 * @date 2017/12/7
 * @since 1.7
 */
public class Menu {

    public enum State{
        CLOSED(0,"closed"),OPEN(1,"open");

        private int flag;
        private String opt;
        State(int flag,String opt){
            this.flag = flag;
            this.opt = opt;
        }

        public int getFlag() {
            return flag;
        }

        public String getOpt() {
            return opt;
        }
    }

    // 菜单唯一标识
    private Integer id;

    // 父菜单id
    private Integer pid;

    //菜单名称
    private String menuName;

    //菜单链接
    private String menuUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }
}
