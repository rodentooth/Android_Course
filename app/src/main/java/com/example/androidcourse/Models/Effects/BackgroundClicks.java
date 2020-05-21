package com.example.androidcourse.Models.Effects;

import android.content.Context;
import android.util.Log;

import com.example.androidcourse.Models.PowerUp;
import com.example.androidcourse.Models.Score;

import java.util.Timer;
import java.util.TimerTask;

public class BackgroundClicks extends Effect {

    public BackgroundClicks(String name, long duration, boolean repeatable, long interval) {
        super(name, duration, repeatable, interval);
    }

    @Override
    public void effect() {
        Score.getInstance().addPoint(1, false);
    }

    @Override
    public void runEffect() {
        if (!hasStarted) {
            hasStarted = true;
            startMilliseconds = System.currentTimeMillis();
            if (this.duration > 0) {
                Timer timer = new Timer(true);
                if (this.interval > 0) {
                    timer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            if (System.currentTimeMillis() - startMilliseconds > duration) {
                                timer.cancel();
                                hasStarted = false;
                                Log.d("debug", name + " Terminated!");
                            } else {
                                effect();
                            }
                        }
                    }, 0, interval);
                }
            } else {
                effect();
            }
        }
    }
}
