package com.example.androidcourse.Models.Effects;

import android.util.Log;

import androidx.lifecycle.Observer;

import com.example.androidcourse.App;
import com.example.androidcourse.Models.Menu;
import com.example.androidcourse.Models.MenuItem;
import com.example.androidcourse.Models.Score;
import com.example.androidcourse.Models.SpecialItem;

import java.util.concurrent.atomic.AtomicInteger;

public class SingleClick extends Effect {

    boolean executed = false;
    int amount;

    public SingleClick(String name, int amount){
        super(name, 0, false, 0);
        this.amount = amount;
    }

    @Override
    public void effect() {
        executed = true;
        int sumOfActiveMultiplicators = 0;

        // Here it should be checked, if there are currently Click-Multiplicators active, If so, the Single-Click amount should be multiplied by the multiplier.
        for (MenuItem item: Menu.getMenu(App.getAppContext()).getMenuItems()) { // We iterate through all available Menu-Items
            if(item.getItem().getEffect() instanceof MultiClick){ // We are only interested in the MultiClick Effects, since they could multiply the amount.
                MultiClick multiClickEffect = (MultiClick) item.getItem().getEffect();
                if(multiClickEffect.isActive()){ // Check if the effect is still ongoing.
                    sumOfActiveMultiplicators += multiClickEffect.getMultiplicator(); // Sum the different mutlipliers.
                }
            }
        }
        if(sumOfActiveMultiplicators == 0){
            sumOfActiveMultiplicators++;
        }
        Score.getInstance().addPoint((this.amount * sumOfActiveMultiplicators), false); // Add the score
    }

    @Override
    public void runEffect() {
        Log.d("debug", "Effect Terminated!");
        effect();
    }
}
