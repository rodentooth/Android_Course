package com.example.androidcourse.Models;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.example.androidcourse.App;

import java.util.concurrent.atomic.AtomicInteger;

public class Score {
    private static Score instance;
    private MutableLiveData<AtomicInteger> scorePoints = new MutableLiveData<>();
    public static final String SHAREDPREF = "sharedpref";
    public static final String HIGHSCORE = "highscore";

    private Score () {

    }

    public static synchronized Score getInstance () {
        if (Score.instance == null) {
            Score.instance = new Score ();
        }
        return Score.instance;
    }

    public void addPoint(int amount){
        scorePoints.getValue().set(amount);
        saveScore();
    }

    public int getScore() {
        return scorePoints.getValue().intValue();
    }

    // Returns the object
    public MutableLiveData<AtomicInteger> getScorePoints() {
        return scorePoints;
    }

    public void saveScore() {
        SharedPreferences sharedPreferences = App.getAppContext().getSharedPreferences(SHAREDPREF, App.getAppContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(HIGHSCORE, scorePoints.getValue().intValue());
        editor.commit();
    }

    public MutableLiveData<AtomicInteger> loadScore(){
        SharedPreferences sharedPreferences = App.getAppContext().getSharedPreferences(SHAREDPREF,  App.getAppContext().MODE_PRIVATE);
        scorePoints.setValue(new AtomicInteger(sharedPreferences.getInt(HIGHSCORE,0 )));
        return scorePoints;
    }
}

