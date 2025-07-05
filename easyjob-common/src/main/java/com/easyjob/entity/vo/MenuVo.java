package com.easyjob.entity.vo;

import java.util.List;

// 前端菜单需要的部分数据
public class MenuVo {

    private String menuName;

    private String menuUrl;

    private String icon;

    private List<MenuVo> children; // 菜单实体的子节点


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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<MenuVo> getChildren() {
        return children;
    }

    public void setChildren(List<MenuVo> children) {
        this.children = children;
    }

}
