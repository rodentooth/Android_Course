package com.example.androidcourse;

import android.os.Bundle;
import android.service.autofill.TextValueSanitizer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;



public class EndlessModeActivity extends AppCompatActivity {
    TextView score;
    TextView scoreRN;
    ImageView play;
    int scorecounter =0; //needs to be changed vor shared preferences

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endlessmode);

        score = findViewById(R.id.tvScore);
        scoreRN = findViewById(R.id.tvScoreRN);
        play = findViewById(R.id.ivPlay);
        scoreRN.setText(Integer.toString(scorecounter));
    }

    public void getPoint(View view){
        scorecounter++;
        scoreRN.setText(Integer.toString(scorecounter));
    }



}
