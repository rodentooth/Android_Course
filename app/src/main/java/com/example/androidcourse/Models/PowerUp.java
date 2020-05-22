package com.example.androidcourse.Models;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;

import com.example.androidcourse.Models.Effects.Effect;


// Purpose: Superclass that defines properties of consumables.
public class PowerUp {
    String name;
    Effect effect;
    Bitmap icon;
    int price;
    public PowerUp(String name, Effect effect, int iconName, Context context, int price){
        this.effect = effect;
        this.name = name;
        this.icon = BitmapFactory.decodeResource(context.getResources(), iconName);
        this.price = price;
    }

    public Effect getEffect() {
        return effect;
    }
}
