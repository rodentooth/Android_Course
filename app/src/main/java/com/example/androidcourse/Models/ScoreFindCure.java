package com.example.androidcourse.Models;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.androidcourse.App;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class ScoreFindCure {
    private static ScoreFindCure instance;
    private MutableLiveData<AtomicInteger> scorePointsFindCure = new MutableLiveData<>();
    private MutableLiveData<Integer> midSum = new MutableLiveData<>();
    public static final String SHAREDPREF = "sharedpref";
    public static final String CURRSCOREFINDCURE = "currscorefindcure"; // the current score
    public static final String CURETARGETSCORE = "curetargetscore";
    public static final String NROFCURESFOUND = "nrofcuresfound";
    public static final String ALLTIMECLICKSCURE = "alltimeclickscure";





    private ScoreFindCure () {
        scorePointsFindCure.observeForever(new Observer<AtomicInteger>() {
            boolean currentlyRunning = false;
            @Override
            public void onChanged(AtomicInteger atomicInteger) {
                if(!currentlyRunning){
                    currentlyRunning = true;
                    int sum = atomicInteger.get();
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            midSum.postValue( ((atomicInteger.get() + 1)- sum) );
                            Log.d("MidSum", "" + ((atomicInteger.get() + 1)- sum));
                            currentlyRunning = false;
                        }
                    }, 1000);
                }
            }
        });
    }

    public static ScoreFindCure getInstance() {
      if (ScoreFindCure.instance == null) {
          ScoreFindCure.instance = new ScoreFindCure();

      }
      return ScoreFindCure.instance;
    }

    public void addPoint(int amount, boolean click) {
        scorePointsFindCure.getValue().addAndGet(amount);
        scorePointsFindCure.postValue(scorePointsFindCure.getValue());
        inceaseAllTimeClicksCure();


        saveScore();
    }

    public boolean checkIfCureWasFound(){
        SharedPreferences sharedPreferences = App.getAppContext().getSharedPreferences(SHAREDPREF, App.getAppContext().MODE_PRIVATE);
        if (sharedPreferences.getInt("curetargetscore", 0) == sharedPreferences.getInt("currscorefindcure", 0)) {
            return true;
        } else {
            return false;
        }
    }
    public MutableLiveData<Integer> getMidSum() {
        return midSum;
    }

    public int getScore() {
        return scorePointsFindCure.getValue().intValue();
    }

    public MutableLiveData<AtomicInteger> getScorePoints() {
        return scorePointsFindCure;
    }

    public void inceaseAllTimeClicksCure(){
        SharedPreferences sharedPreferences = App.getAppContext().getSharedPreferences(SHAREDPREF, App.getAppContext().MODE_PRIVATE);
        Integer allTimeClicksCure = sharedPreferences.getInt("alltimeclickscure", 0);
        allTimeClicksCure++;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("alltimeclickscure", allTimeClicksCure);
        editor.apply();
    }

    public void saveScore() {
        SharedPreferences sharedPreferences = App.getAppContext().getSharedPreferences(SHAREDPREF, App.getAppContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(CURRSCOREFINDCURE, scorePointsFindCure.getValue().intValue());
        editor.commit();
    }

    public MutableLiveData<AtomicInteger> loadScore(){
        SharedPreferences sharedPreferences = App.getAppContext().getSharedPreferences(SHAREDPREF,  App.getAppContext().MODE_PRIVATE);
        scorePointsFindCure.setValue(new AtomicInteger(sharedPreferences.getInt(CURRSCOREFINDCURE,0 )));
        return scorePointsFindCure;
    }




}
