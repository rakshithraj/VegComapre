package com.app.taza_price.model;

import java.util.ArrayList;

/**
 * Created by Rakshith on 12/22/2016.
 */

public class ShopComparePrice {

    String name="";

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<String> getPrice_kg() {
        return price_kg;
    }

    public void setPrice_kg(ArrayList<String> price_kg) {
        this.price_kg = price_kg;
    }

    String image="";
    ArrayList<String> price_kg = new  ArrayList<String>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getPrice() {
        return price_kg;
    }

    public void setPrice(ArrayList<String> price_kg) {
        this.price_kg = price_kg;
    }
}
