package com.uuzz.user.model;

/**
 * @author zj
 * @desc 菜单
 * @date 2017/12/7
 * @since 1.7
 */
public class Menu {

    // 菜单唯一标识
    private Integer id;

    // 父菜单id
    private Integer parentId;

    //菜单描述
    private String text;

    //菜单状态（1:open,0:close）
    private Integer state;

    //菜单链接
    private String url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
