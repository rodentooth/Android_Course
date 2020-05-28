package com.example.androidcourse.Models;

public class MenuItem {
    PowerUp item;
    public MenuItem(PowerUp item) {
        this.item = item;
    }

    public PowerUp getItem() {
        return item;
    }

    @Override
    public String toString(){

        // so many spaces are not pretty, but it works :D
        return "" + item.name + "                      " + item.price + " Coins";
    }
}
