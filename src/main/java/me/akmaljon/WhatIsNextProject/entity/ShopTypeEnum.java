package me.akmaljon.WhatIsNextProject.entity;

public enum ShopTypeEnum {
    GROCERY,
    CLOTHES_SHOP,
    BAKERY,
    BOOKSTORE,
    SUPERMARKET,
    BUTCHERS,
    RESTAURANT,
    TOY_SHOP,
    SHOE_SHOP,
    ELECTRONICS_SHOP,
    SPORTS_SHOP;


    @Override
    public String toString() {
        String originalString = super.toString();
        String outputString = originalString.replaceAll("_", " ");
        outputString = outputString.toLowerCase();
        return outputString.substring(0, 1).toUpperCase() + outputString.substring(1);
    }
}
