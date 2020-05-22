package com.example.androidcourse.Models;

import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;

import com.example.androidcourse.App;

import java.util.concurrent.atomic.AtomicInteger;

public class ScoreFindCure {
    private static ScoreFindCure instance;
    private MutableLiveData<AtomicInteger> scorePointsFindCure = new MutableLiveData<>();
    public static final String SHAREDPREF = "sharedpref";
    public static final String CURRSCOREFINDCURE = "currscorefindcure"; // the current score
    public static final String CURETARGETSCORE = "curetargetscore";
    public static final String NROFCURESFOUND = "nrofcuresfound";


    private ScoreFindCure () {

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
    public int getScore() {
        return scorePointsFindCure.getValue().intValue();
    }

    public MutableLiveData<AtomicInteger> getScorePoints() {
        return scorePointsFindCure;
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
