package com.nautilus.omni.appmodules.navigationdrawer;

public class ObjectDrawerItem {
    private int activeIcon;
    private int icon;
    private String name;

    public ObjectDrawerItem(int icon2, int activeIcon2, String name2) {
        this.icon = icon2;
        this.name = name2;
        this.activeIcon = activeIcon2;
    }

    public int getIcon() {
        return this.icon;
    }

    public String getName() {
        return this.name;
    }

    public int getActiveIcon() {
        return this.activeIcon;
    }

    public void setIcon(int icon2) {
        this.icon = icon2;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public void setActiveIcon(int activeIcon2) {
        this.activeIcon = activeIcon2;
    }
}
