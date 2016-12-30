package com.app.taza_price.model;

/**
 * Created by Rakshith on 12/19/2016.
 */

public class FruitPriceDetail {
    String price_id;
    String  veggi_id;
    String shop_id;

    public String getPrice_kg() {
        return price_kg;
    }

    public void setPrice_kg(String price_kg) {
        this.price_kg = price_kg;
    }

    public String getPrice_gm() {
        return price_gm;
    }

    public void setPrice_gm(String price_gm) {
        this.price_gm = price_gm;
    }

    String price_kg;
    String price_gm;
    String unit;
    String shop_name;
    String veg_name;
    String veg_type;
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    String image;
    public String getPrice_id() {
        return price_id;
    }

    public void setPrice_id(String price_id) {
        this.price_id = price_id;
    }

    public String getVeggi_id() {
        return veggi_id;
    }

    public void setVeggi_id(String veggi_id) {
        this.veggi_id = veggi_id;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }



    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getVeg_name() {
        return veg_name;
    }

    public void setVeg_name(String veg_name) {
        this.veg_name = veg_name;
    }

    public String getVeg_type() {
        return veg_type;
    }

    public void setVeg_type(String veg_type) {
        this.veg_type = veg_type;
    }
}
