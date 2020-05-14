package com.example.androidcourse.Models;

import android.content.Context;

import com.example.androidcourse.Models.Effects.Effect;

// Purpose: Timebooster, background activity
public class Boost extends PowerUp {
    public Boost(String name, Effect effect, int iconName, Context context) {
        super(name, effect, iconName, context);
    }
}
