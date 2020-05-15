package com.example.androidcourse.Models.Effects;

import androidx.lifecycle.ViewModel;

public abstract class Effect implements EffectI {
    String name; // Name of the effect
    long duration; // How long should the effect run
    long interval; // Every x-milliseconds
    boolean repeatable;
    long startMilliseconds = 0; // how many seconds have passed
    boolean hasStarted = false;

    public Effect(String name, long duration, boolean repeatable, long interval){
        this.name = name;
        this.duration = duration;
        this.repeatable = repeatable;
        this.interval = interval;
    }
}
