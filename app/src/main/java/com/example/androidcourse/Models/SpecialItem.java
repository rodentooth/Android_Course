package com.example.androidcourse.Models;


import android.content.Context;
import android.graphics.Bitmap;

import com.example.androidcourse.Models.Effects.Effect;

// purpose: More clicks per click, power clicks, faster auto-clicks etc...
public class SpecialItem extends PowerUp {

    public SpecialItem(String name, Effect effect, int iconName, Context context, int price) {
        super(name, effect, iconName, context, price);
    }
}
