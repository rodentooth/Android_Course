package com.example.androidcourse.Models;
import android.content.Context;
import android.util.Log;

import com.example.androidcourse.Models.Effects.BackgroundClicks;
import com.example.androidcourse.Models.Effects.MultiClick;
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
        this.menuItems.add(new MenuItem(new Boost("Single Background", new BackgroundClicks("Single Background Click", 60000, false, 1000), R.drawable.icon_edited, context, 100)));
        this.menuItems.add(new MenuItem(new Boost("Double Background", new BackgroundClicks("Double Background Click", 30000, false, 500), R.drawable.double_time, context, 200)));
        this.menuItems.add(new MenuItem(new Boost("Quad Background", new BackgroundClicks("Quad Background Click", 15000, false, 250), R.drawable.four_time, context, 500)));
        this.menuItems.add(new MenuItem(new Boost("Octa Background", new BackgroundClicks("Octa Background Click", 10000, false, 125), R.drawable.eight_time, context, 1000)));
        this.menuItems.add(new MenuItem(new SpecialItem("Double Click", new MultiClick("Double Click", 60000, 2), R.drawable.double_time, context, 50)));
        this.menuItems.add(new MenuItem(new SpecialItem("Quad Click", new MultiClick("Quad Click", 60000, 4), R.drawable.four_time, context, 200)));
        this.menuItems.add(new MenuItem(new SpecialItem("Octa Click", new MultiClick("Quad Click", 60000, 8), R.drawable.eight_time, context, 500)));

        Log.d("Debug", "Menu Filled "+ this.getMenuItems().size());
    }

    public ArrayList<MenuItem> getMenuItems(){
        return this.menuItems;
    }


    public String getMenuName(){
        return this.menuName;
    }


}
