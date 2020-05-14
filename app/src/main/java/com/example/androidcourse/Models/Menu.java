package com.example.androidcourse.Models;
import android.R.layout;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.androidcourse.Models.Effects.BackgroundSingleClick;
import com.example.androidcourse.Models.Effects.DoubleClick;
import com.example.androidcourse.R;

import java.util.ArrayList;

// Purpose: Logic of Shop, Displaying items, buying, making transactions, checking balances
public class Menu {

    private static Menu menu; // Should be a singleton, since we only need one instance of the Menu, not multiple
    private String menuName;
    private Context context;
    ArrayList<MenuItem> menuItems = new ArrayList<>();
    private Menu(String menuName, Context context){
        this.menuName = menuName;
        this.context = context;
        this.fillMenu();
    }
    public static Menu getMenu (Context context){
        if(menu != null){
            return menu;
        }else{
            menu = new Menu("Store", context);
        }
        return menu;
    }

    void fillMenu(){
        this.menuItems.add(new MenuItem(new SpecialItem("Double Clicker", new DoubleClick("Twice", 30000, false, 1000), R.drawable.double_time, context)));
        this.menuItems.add(new MenuItem(new Boost("Single Background", new BackgroundSingleClick("Single", 15000, false, 1000), R.drawable.icon_edited, context)));
        this.menuItems.add(new MenuItem(new SpecialItem("Double Clicker", new DoubleClick("Twice", 30000, false, 1000), R.drawable.eight_time, context)));
        this.menuItems.add(new MenuItem(new SpecialItem("Double Clicker", new DoubleClick("Twice", 30000, false, 1000), R.drawable.sixteen_time, context)));
        this.menuItems.add(new MenuItem(new SpecialItem("Double Clicker", new DoubleClick("Twice", 30000, false, 1000), R.drawable.four_time, context)));
        Log.d("Debug", "Menu Filled "+ this.getMenuItems().size());
    }

    public ArrayList<MenuItem> getMenuItems(){
        return this.menuItems;
    }


    public String getMenuName(){
        return this.menuName;
    }


}
