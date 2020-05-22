package com.example.androidcourse.Models;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

public class SpeedTracker {

    ArrayList<Date> clicks = new ArrayList<Date>();

    public SpeedTracker(){


    }

    public String trackTimeSpentForClick(){
        String s;
        Date d1 = new Date();
        this.clicks.add(d1);

        if (clicks.size()>1){ //if more than one click is in the array

            long s1 = clicks.get(clicks.size()-1).getTime();
            long s2 = clicks.get(clicks.size()-2).getTime();

            double s3 = s1-s2;
            DecimalFormat df = new DecimalFormat(".00");
            df.setRoundingMode(RoundingMode.DOWN); //round down
            double s4 = 1000/s3;

            s = "Speed: "+df.format(s4)+" Clicks/Second";
        } else {
            s ="";
        }

        return s;
    }
}
