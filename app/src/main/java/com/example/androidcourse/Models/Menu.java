package com.example.androidcourse.Models;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.androidcourse.App;
import com.example.androidcourse.Models.Effects.BackgroundClicks;
import com.example.androidcourse.Models.Effects.MultiClick;
import com.example.androidcourse.Models.Effects.SingleClick;
import com.example.androidcourse.R;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

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
        final String SHAREDPREF = "sharedpref";
        SharedPreferences sharedPreferences = App.getAppContext().getSharedPreferences(SHAREDPREF, App.getAppContext().MODE_PRIVATE);
        float f = sharedPreferences.getFloat("recordclickspeed", 0);
        int clickSpeedPerSecondHighScore = (int) f; // Parse and round down clickspeed per second
        int avgPerMillisecond = 1000 / clickSpeedPerSecondHighScore; // transform the format

        this.menuItems.add(new MenuItem(new Boost("Single Background", new BackgroundClicks("Single Background Click", 60000, false, 1000), R.drawable.icon_edited, context, 5)));
        this.menuItems.add(new MenuItem(new Boost("Double Background", new BackgroundClicks("Double Background Click", 30000, false, 500), R.drawable.double_time, context, 10)));
        this.menuItems.add(new MenuItem(new Boost("Quad Background", new BackgroundClicks("Quad Background Click", 15000, false, 250), R.drawable.four_time, context, 50)));
        this.menuItems.add(new MenuItem(new Boost("Octa Background", new BackgroundClicks("Octa Background Click", 10000, false, 125), R.drawable.eight_time, context, 100)));
        this.menuItems.add(new MenuItem(new Boost("Hexa Background", new BackgroundClicks("Hexa Background Click", 5000, false, 62), R.drawable.sixteen_time, context, 200)));

        this.menuItems.add(new MenuItem(new SpecialItem("Double Click", new MultiClick("Double Click", 60000, 2), R.drawable.double_time, context, 5)));
        this.menuItems.add(new MenuItem(new SpecialItem("Quad Click", new MultiClick("Quad Click", 60000, 4), R.drawable.four_time, context, 10)));
        this.menuItems.add(new MenuItem(new SpecialItem("Octa Click", new MultiClick("Octa Click", 60000, 8), R.drawable.eight_time, context, 100)));

        this.menuItems.add(new MenuItem(new SpecialItem("200-Liquid", new SingleClick("200 Click", 200), R.drawable.science1, context, 20)));
        this.menuItems.add(new MenuItem(new SpecialItem("400-Liquid", new SingleClick("400 Click", 400), R.drawable.science2, context, 500)));
        this.menuItems.add(new MenuItem(new SpecialItem("600-Liquid", new SingleClick("600 Click", 600), R.drawable.science3, context, 700)));

        this.menuItems.add(new MenuItem(new Boost("My Prime!", new BackgroundClicks("Prime Avg Click", 10000, false, avgPerMillisecond), R.drawable.icon, context, 10)));

        // CLick with your record speed

        Log.d("Debug", "Menu Filled "+ this.getMenuItems().size());
    }

    public boolean makeTransaction(MenuItem item){
        if(item.getItem().price <= Score.getInstance().getMoney().getValue().intValue()){
            Log.d("Money First", " "+   Score.getInstance().getMoney().getValue());
            Score.getInstance().getMoney().setValue(new AtomicInteger(Score.getInstance().getMoney().getValue().intValue() - item.getItem().price)); // Remove money from the client
            Log.d("Money Left", " "+   Score.getInstance().getMoney().getValue());
            Score.getInstance().saveMoney();
            return true;
        }
        return false;
    }

    public ArrayList<MenuItem> getMenuItems(){
        return this.menuItems;
    }


    public String getMenuName(){
        return this.menuName;
    }


}
