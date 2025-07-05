package com.easyjob.entity.dto;

// po 对应 db
// dto 后端的扩展
// vo 直接给前端

import com.easyjob.entity.po.Menu;
import com.easyjob.entity.vo.MenuVo;

import java.util.List;

// 返回部分信息给前端
public class SessionUserAdminDto {

    private Integer userId;
    private String userName;
    private Boolean superAdmin;
    private List<MenuVo> menuLst; // 可访问菜单 已经是树
    private List<String> permissionLst; // 直接从该线性中查找权限

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getSuperAdmin() {
        return superAdmin;
    }

    public void setSuperAdmin(Boolean superAdmin) {
        this.superAdmin = superAdmin;
    }

    public List<MenuVo> getMenuLst() {
        return menuLst;
    }

    public void setMenuLst(List<MenuVo> menuLst) {
        this.menuLst = menuLst;
    }

    public List<String> getPermissionLst() {
        return permissionLst;
    }

    public void setPermissionLst(List<String> permissionLst) {
        this.permissionLst = permissionLst;
    }
}
