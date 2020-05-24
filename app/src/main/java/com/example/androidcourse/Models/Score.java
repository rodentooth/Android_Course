package com.example.androidcourse.Models;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.androidcourse.App;
import com.example.androidcourse.data.ObjectJsonConverter;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class Score {
    private static Score instance;
    private MutableLiveData<AtomicInteger> scorePoints = new MutableLiveData<>();
    private MutableLiveData<AtomicInteger> clickCount = new MutableLiveData<>();
    private MutableLiveData<Integer> midSum = new MutableLiveData<>();
    private MutableLiveData<AtomicInteger> money = new MutableLiveData<>();


    public static final String SHAREDPREF = "sharedpref";
    public static final String HIGHSCORE = "highscore";
    public static final String MONEY = "money";

    public Score() {
        loadMoney();

        scorePoints.observeForever(new Observer<AtomicInteger>() {
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
                            currentlyRunning = false;
                        }
                    }, 1000);
                }
            }
        });
    }

    public static Score getInstance () {
        if (Score.instance == null) {
            Score.instance = new Score ();
        }
        return Score.instance;
    }

    public void addPoint(int amount, boolean click){
        Log.d("Points added", ""+ amount);
        if(click){
            this.clickCount.getValue().addAndGet(1);
            this.clickCount.postValue(clickCount.getValue());
        }
        int count = 0;
        for (int i = 0; i < amount; i++) {
            int value = this.scorePoints.getValue().incrementAndGet();

            if((value % 10) == 0){
                Log.d("TEST ",  "" + (count));
                count++;

                Log.d("MONEY ", money.getValue().toString());
                money.getValue().incrementAndGet();
                money.postValue(money.getValue());
                saveMoney();
            }
            this.scorePoints.postValue(scorePoints.getValue());
        }
        this.scorePoints.postValue(scorePoints.getValue());
        saveScore();
    }

    public int getScore() {
        return scorePoints.getValue().intValue();
    }

    public MutableLiveData<AtomicInteger> getMoney() {
        return money;
    }

    // Returns the object
    public MutableLiveData<AtomicInteger> getScorePoints() {
        return scorePoints;
    }

    // getClickCount
    public MutableLiveData<AtomicInteger> getClickCount() {
        return clickCount;
    }

    public MutableLiveData<Integer> getMidSum() {
        return midSum;
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

    public void saveMoney() {
        SharedPreferences sharedPreferences = App.getAppContext().getSharedPreferences(SHAREDPREF, App.getAppContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MONEY, money.getValue().toString());
        editor.apply();
    }

    public void loadMoney(){
        //get the shared preferences
        String savedMoney = App.getAppContext().getSharedPreferences(SHAREDPREF,  App.getAppContext().MODE_PRIVATE).getString(MONEY,"0" );

        this.money.setValue(new AtomicInteger(Integer.parseInt(savedMoney)));
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

