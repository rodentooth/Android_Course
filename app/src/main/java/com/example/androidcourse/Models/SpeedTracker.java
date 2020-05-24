package com.example.androidcourse.Models;

import android.content.SharedPreferences;

import com.example.androidcourse.App;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

public class SpeedTracker {

    ArrayList<Date> clicks = new ArrayList<Date>();
    public static final String SHAREDPREF = "sharedpref";
    public static final String RECORDCLICKSPEED = "recordclickspeed";
    public SpeedTracker(){


    }

    public String trackTimeSpentForClick(){
        String s;
        Date d1 = new Date();
        this.clicks.add(d1);

        if (clicks.size()>1){ //if more than one click is in the array

            long s1 = clicks.get(clicks.size()-1).getTime();
            long s2 = clicks.get(clicks.size()-2).getTime();

            float s3 = s1-s2;
            DecimalFormat df = new DecimalFormat(".00");
            df.setRoundingMode(RoundingMode.DOWN); //round down
            float s4 = 1000/s3;

            CheckIfClickSpeedIsNewLocalRecord(s4);

            s = "Speed: "+df.format(s4)+" Clicks/Second";
        } else {
            s ="";
        }

        return s;
    }

    public void CheckIfClickSpeedIsNewLocalRecord(float d){
        SharedPreferences sharedPreferences = App.getAppContext().getSharedPreferences(SHAREDPREF, App.getAppContext().MODE_PRIVATE);
        float f = sharedPreferences.getFloat("recordclickspeed", 0);
        if (f < d) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putFloat("recordclickspeed", d);
            editor.apply();
            System.out.println("New record was found: "+d );
        }
    }
}
