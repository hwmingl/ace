package com.fighter.ace.app.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanebert on 17/7/5.
 */
public class MenuVO {

    private String menuUrl;
    private String menuTitle;
    private boolean isLeaf;

    private List<MenuVO> subMenus = new ArrayList<>();

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public List<MenuVO> getSubMenus() {
        return subMenus;
    }

    public void setSubMenus(List<MenuVO> subMenus) {
        this.subMenus = subMenus;
    }
}
