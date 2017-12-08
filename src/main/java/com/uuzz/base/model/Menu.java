package com.uuzz.base.model;

import static com.uuzz.base.model.Menu.State.CLOSED;

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

    //菜单描述
    private String text;

    //菜单状态（1:open,0:close）
    private State State = CLOSED;

    //菜单链接
    private String url;

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Menu.State getState() {
        return State;
    }

    public void setState(Menu.State state) {
        State = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
