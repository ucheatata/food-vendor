package com.foodvendor.model;

public class Menu_v2 {

    private String id;
    private String description;
    private double cost;

    public Menu_v2(String id, String description, double cost) {
        this.id = id;
        this.description = description;
        this.cost = cost;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Menu_v2{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                '}';
    }
}
