package com.example.androidcourse;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        TextView emanuel = (TextView) findViewById(R.id.emanuel);
        TextView sebastian = (TextView) findViewById(R.id.sebastian);
        TextView simon = (TextView) findViewById(R.id.simon);
        TextView fabio = (TextView) findViewById(R.id.fabio);
        TextView dominic = (TextView) findViewById(R.id.dominic);
        TextView present = (TextView) findViewById(R.id.present);

        final ViewGroup transitionsContainer = (ViewGroup) findViewById(R.id.transition_container);

        //final ConstraintLayout button = (ConstraintLayout) transitionsContainer.findViewById(R.id.transition_container);


        Handler handler = new Handler();
        final int[] i = {0};
        Runnable splashAnimation = new Runnable() {
            public void run() {
                try {
                    //prepare and send the data here..

                    //transitionsContainer.removeAllViews();
                    TextView t = null;
                    Slide s = null;

                    switch (i[0]){
                        case 0:
                            t = transitionsContainer.findViewById(R.id.emanuel);
                            s = new Slide(Gravity.LEFT);
                            break;
                        case 1:
                            t = transitionsContainer.findViewById(R.id.sebastian);
                            s = new Slide(Gravity.RIGHT);
                            break;
                        case 2:
                            t = transitionsContainer.findViewById(R.id.simon);
                            s = new Slide(Gravity.LEFT);
                            break;
                        case 3:
                            t = transitionsContainer.findViewById(R.id.fabio);
                            s = new Slide(Gravity.RIGHT);
                            break;
                        case 4:
                            t = transitionsContainer.findViewById(R.id.dominic);
                            s = new Slide(Gravity.LEFT);
                            break;
                        case 5:
                            t = transitionsContainer.findViewById(R.id.present);
                            s = new Slide(Gravity.BOTTOM);
                            break;
                        case 8:
                            Intent justanintent_buimm = new Intent(splash.this, MainActivity.class);
                            startActivity(justanintent_buimm);
                            splash.this.finish();
                            break;
                    }

                    i[0]++;

                    if (t!=null) {
                        TransitionManager.beginDelayedTransition(transitionsContainer, s);
                        t.setVisibility(View.VISIBLE);
                    }


                    handler.postDelayed(this, 1000);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        };
        handler.postDelayed(splashAnimation, 3000);



    }




}


