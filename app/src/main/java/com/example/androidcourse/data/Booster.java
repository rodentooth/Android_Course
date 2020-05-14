package com.example.androidcourse.data;

public class Booster {

    //booster can be purchased or randomly obtained during the gameplay

    //Types:
    //AutoTap: Taps for you with frequency X.
    //TapBonus: For each tap X bonustaps
    //IdleTap: Taps for you while the activity is not active

    enum type{
        AutoTap,
        TapBonus,
        IdleTap
    }

    private final type type;
    private int tapFrequencyPerSecond;
    private int tapBonusPerTap;

    
    Booster(type t){
        this.type = t;
    }
}
