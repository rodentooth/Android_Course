package com.example.androidcourse.Models.Effects;

import android.util.Log;
import androidx.lifecycle.Observer;


import com.example.androidcourse.Models.Score;

import java.util.concurrent.atomic.AtomicInteger;

public class MultiClick extends Effect {

    private boolean started = false;
    private int multiplicator = 0;
    public MultiClick(String name, long duration, int multiplicator) {
        super(name, duration, false, 0);
        this.multiplicator = multiplicator;
    }

    @Override
    public void effect() {
        Score.getInstance().addPoint((this.multiplicator) -1, false); // Add multiple to single click.
    }

    @Override
    public void runEffect(){
        if(!started) {
            started = true;
            startMilliseconds = System.currentTimeMillis();
                    Score.getInstance().getClickCount().observeForever(new Observer<AtomicInteger>() {
                        @Override
                        public void onChanged(AtomicInteger atomicInteger) {
                                    if (System.currentTimeMillis() - startMilliseconds > duration) { // Check if the effect is still active.
                                        Score.getInstance().getClickCount().removeObserver(this); // Remove the observer, so it will not trigger again
                                        started = false;
                                        Log.d("debug", "Effect Terminated!");
                                    } else {
                                        effect();
                                    }
                        }
                    });
            }
    }
}
