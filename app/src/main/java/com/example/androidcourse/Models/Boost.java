package com.example.androidcourse.Models;

import android.content.Context;

import com.example.androidcourse.Models.Effects.Effect;

// Purpose: background activity
public class Boost extends PowerUp {
    public Boost(String name, Effect effect, int iconName, Context context, int price) {
        super(name, effect, iconName, context, price);
    }
}
