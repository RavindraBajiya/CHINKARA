package com.ravindra.siit.chinkara.DataObj;

public class MenuListData {
    String menu;
    String menuText;

    public MenuListData(String menu, String menuText) {
        this.menu = menu;
        this.menuText = menuText;
    }

    public MenuListData() {
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getMenuText() {
        return menuText;
    }

    public void setMenuText(String menuText) {
        this.menuText = menuText;
    }
}
