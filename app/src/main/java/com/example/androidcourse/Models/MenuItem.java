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
        return "" + item.name + " " + item.price + " Coins";
    }
}
