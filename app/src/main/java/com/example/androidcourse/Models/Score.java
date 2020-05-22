package com.example.androidcourse.Models;

import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;

import com.example.androidcourse.App;
import com.example.androidcourse.data.ObjectJsonConverter;

import java.util.concurrent.atomic.AtomicInteger;

public class Score {
    private static Score instance;
    private MutableLiveData<AtomicInteger> scorePoints = new MutableLiveData<>();
    private MutableLiveData<AtomicInteger> clickCount = new MutableLiveData<>();
    public static final String SHAREDPREF = "sharedpref";
    public static final String HIGHSCORE = "highscore";

    public Score() {

    }

    public static Score getInstance () {
        if (Score.instance == null) {
            Score.instance = new Score ();
        }
        return Score.instance;
    }

    public void addPoint(int amount, boolean click){
        if(click){
            this.clickCount.getValue().addAndGet(1);
            this.clickCount.postValue(clickCount.getValue());
        }
        this.scorePoints.getValue().addAndGet(amount);
        this.scorePoints.postValue(scorePoints.getValue());
        saveScore();
    }

    public int getScore() {
        return scorePoints.getValue().intValue();
    }

    // Returns the object
    public MutableLiveData<AtomicInteger> getScorePoints() {
        return scorePoints;
    }

    // getClickCount
    public MutableLiveData<AtomicInteger> getClickCount() {
        return clickCount;
    }


    public void saveScore() {

        //Convert the value of the scoreobject to an Json String
        ObjectJsonConverter converter = new ObjectJsonConverter();
        String scorepoints = converter.convertObjectToJson(scorePoints);

        SharedPreferences sharedPreferences = App.getAppContext().getSharedPreferences(SHAREDPREF, App.getAppContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(HIGHSCORE, scorepoints);
        editor.apply();
    }

    public MutableLiveData<AtomicInteger> loadScore(){

        //get the shared preferences
        String savedScoreString = App.getAppContext().getSharedPreferences(SHAREDPREF,  App.getAppContext().MODE_PRIVATE).getString(HIGHSCORE,"" );

        //Convert the Json string to a score Object
        ObjectJsonConverter converter = new ObjectJsonConverter();
        MutableLiveData<Integer> savedScore = (MutableLiveData<Integer>) converter.convertJsonToObject(savedScoreString, MutableLiveData.class);


        if(savedScore==null)
            this.scorePoints.setValue(new AtomicInteger(0));
        else
            //Well it actually converts it to a normal integer. so here we go.
            this.scorePoints.setValue(new AtomicInteger(savedScore.getValue()));

        System.out.println("score is: "+scorePoints.getValue());
        clickCount.setValue(new AtomicInteger(0));
        return scorePoints;
    }
}

