package com.foodvendor.controller;


import com.foodvendor.doa.MenuDataSeeder;
import com.foodvendor.model.Developer;
import com.foodvendor.model.Menu_v2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Class exposes API rest points for querying menu collection.
 * Add cross origin addresses to enable access; eg @CrossOrigin(origins = {"http://localhost:3000"}).
 */
@RequestMapping("/menu")
@RestController
public class MenuController {

    /**
     * Method exposes API rest point to query Menu collection by menu item id variable
     * @param id Menu item id to be queried
     * @return Menu item full details
     */
    @GetMapping("/{id}")
    public Menu_v2 getById(@PathVariable("id") String id) {
        Menu_v2 menuItem = MenuDataSeeder.getInstance().findById(id);
        return menuItem;
    }

    /**
     * Method exposes API rest point for listing all menu items
     * @return All menu items
     */
    @GetMapping("/all")
    public List<Menu_v2> getAllMenuItems() {
        List<Menu_v2> menuItems = MenuDataSeeder.getInstance().getMenuList();
        return menuItems;
    }
}
