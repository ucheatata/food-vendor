package com.foodvendor.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Menu {

    RICE_AND_STEW(101, "rice dish and stew", 300.00),
    EBA_AND_SOUP(102,"swallow and soup", 500.00),
    BEANS_AND_RICE(103,"porridge beans and rice with stew", 450),
    FRIED_PLANTAIN_AND_EGGS(104, "eggs woth fried plantain", 250),
    YAM_PORRIDGE(105,"yam garnished with tasty porridge", 600);


    private final int menuId;
    private final String description;
    private final double cost;
    private static final Map<Integer, Menu> MENU_MAP = new HashMap<>();

    private Menu(int menuId, String description, double cost) {
        this.menuId = menuId;
        this.description = description;
        this.cost = cost;
    }

    public int getMenuId() {
        return menuId;
    }

    public String getDescription() {
        return description;
    }

    public double getCost() {
        return cost;
    }

    static {
        for (Menu menu : values()) {
            MENU_MAP.put(menu.menuId, menu);
        }
    }

    public static Menu GetMenu (int id) {
        return MENU_MAP.get(id);
    }

    public static List<Menu> getAllMenu() {
        List<Menu> allMenu = Arrays.asList(Menu.values());
        return allMenu;
    }
}
