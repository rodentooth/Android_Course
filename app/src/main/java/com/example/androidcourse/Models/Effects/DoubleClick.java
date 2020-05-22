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

public class DoubleClick extends Effect {

    private boolean started = false;
    private int multiplicator = 0;
    public DoubleClick(String name, long duration, int multiplicator) {
        super(name, duration, false, 0);
        this.multiplicator = multiplicator;
    }

    @Override
    public void effect() {
        Log.d("debug", "Effect Executed!");
        Score.getInstance().addPoint((this.multiplicator) -1, false);
    }

    @Override
    public void runEffect(){
        if(!started) {
            started = true;
            startMilliseconds = System.currentTimeMillis();
                // Timer timer = new Timer(true);
                    Score.getInstance().getClickCount().observeForever(new Observer<AtomicInteger>() {
                        @Override
                        public void onChanged(AtomicInteger atomicInteger) {
                                    if (System.currentTimeMillis() - startMilliseconds > duration) { // Check if the effect is still active.
                                        // timer.cancel();
                                        Score.getInstance().getClickCount().removeObserver(this);
                                        Log.d("debug", "Effect Terminated!");
                                    } else {
                                        effect();
                                    }
                        }
                    });
            }

    }
}
