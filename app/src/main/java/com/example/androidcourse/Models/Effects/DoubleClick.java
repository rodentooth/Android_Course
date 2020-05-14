package com.example.androidcourse.Models.Effects;

import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class DoubleClick extends Effect {

    private boolean started = false;
    public DoubleClick(String name, long duration, boolean repeatable, long interval) {
        super(name, duration, repeatable, interval);
    }

    @Override
    public void effect() {
        Log.d("debug", "Effect Executed!");
    }

    @Override
    public void runEffect(){
        if(!started) {
            started = true;
            startMilliseconds = System.currentTimeMillis();
            if (this.duration > 0) {
                Timer timer = new Timer(true);
                if (this.interval > 0) {
                    timer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            if (System.currentTimeMillis() - startMilliseconds > duration) {
                                timer.cancel();
                                Log.d("debug", "Effect Terminated!");
                            } else {
                                effect();
                            }
                        }
                    }, 0, interval);
                }
            }
            else{
                effect();
            }
            /*
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    effect();
                }
            }, 0, duration);

             */
        }
    }
}
