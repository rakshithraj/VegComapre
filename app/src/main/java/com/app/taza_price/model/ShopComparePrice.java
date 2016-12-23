package com.app.taza_price.model;

import java.util.ArrayList;

/**
 * Created by Rakshith on 12/22/2016.
 */

public class ShopComparePrice {

    String name;
    ArrayList<String> price = new  ArrayList<String>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getPrice() {
        return price;
    }

    public void setPrice(ArrayList<String> price) {
        this.price = price;
    }
}
