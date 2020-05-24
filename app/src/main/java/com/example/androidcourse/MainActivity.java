package com.example.androidcourse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.androidcourse.login.LoginActivity;
import com.example.androidcourse.EndlessModeActivity;
import com.example.androidcourse.FindTheCureActivity;

public class MainActivity extends AppCompatActivity {


    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), LoginActivity.class);
                //i.putExtra("PersonID", personID);
                startActivity(i);
            }
        });*/

        mediaPlayer = MediaPlayer.create(this, R.raw.megalovania);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);


        findViewById(R.id.btnLeaderboard).setOnClickListener((event) ->
        {
            Intent i = new Intent(getBaseContext(), Leaderboard_Stats.class);
            startActivity(i);
        });




        findViewById(R.id.btnEndless).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), EndlessModeActivity.class);

                startActivity(i);
            }
        });

        findViewById(R.id.btnFindCure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), FindTheCureActivity.class);

                startActivity(i);
            }
        });



    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mediaPlayer!=null&& mediaPlayer.isPlaying())
        mediaPlayer.stop();
        mediaPlayer = MediaPlayer.create(this, R.raw.megalovania);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);

    }
}
