package com.foodvendor.doa;

import com.foodvendor.model.Menu_v2;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class for seeding menu data from csv file.
 * Implements singleton design pattern
 */
public class MenuDataSeeder {

    private ArrayList<Menu_v2> menuList;

    private static MenuDataSeeder instance;

    //Instantiate singleton
    public static MenuDataSeeder getInstance() {
        //instance = null;
        //if (instance == null) // only if no instance
        synchronized (MenuDataSeeder.class) { // lock block
            //if (instance == null) // and re-check
            instance = new MenuDataSeeder();
        }
        return instance;
    }

    //Method loads menu
    private MenuDataSeeder() {
        menuList = new ArrayList<>();
        loadMenu();
    }

    //Method adds an item to the menu
    private void addMenuItem(Menu_v2 menuItem) {
        menuList.add(menuItem);
    }

    //Method implements menu search function by menu item id
    public Menu_v2 findById(String id) {
        for (Menu_v2 menuItem : menuList) {
            if (menuItem.getId().equals(id)) {
                return menuItem;
            }
        }
        return null;
    }

    //Method for processing csv line, creating menu objects and adding to menu list
    private void processMenuLine(String line) {
        String parts[] = line.split(",");
        String id = parts[0];
        String description = parts[1];
        double cost = Double.parseDouble(parts[2]);

        Menu_v2 menuItem = new Menu_v2(
                id,
                description,
                cost
        );
        this.addMenuItem(menuItem);
    }

    //Method for reading csv file
    private void loadMenu(){
        try {
            URL url = DataSeeder.class.getResource("/static/menu_seed_data.csv");
            File f = new File(url.toURI());
            Scanner scanner = new Scanner(f);
            while (scanner.hasNextLine()) {
                String inputLine = scanner.nextLine();
                if (inputLine.length() != 0) {
                    processMenuLine(inputLine);
                }
            }
        } catch (FileNotFoundException | URISyntaxException fnf) {
            System.err.println(fnf);
            System.exit(0);
        }
    }

    //Method returns menu list
    public ArrayList<Menu_v2> getMenuList() {
        return menuList;
    }

}
