package com.example.androidcourse.Models.Effects;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.example.androidcourse.App;
import com.example.androidcourse.Models.Score;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class DoubleClick extends Effect implements LifecycleOwner {

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
                            // Score.getInstance().getScorePoints().observeForever();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (System.currentTimeMillis() - startMilliseconds > duration) {
                                timer.cancel();
                                Log.d("debug", "Effect Terminated!");
                            } else {
                                effect();
                            }
                        }
                    }, 0, duration);
                }
            }
            else{
                effect();
            }

        }
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return null;
    }
}
