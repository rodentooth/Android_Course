package com.example.androidcourse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.androidcourse.ui.login.LoginActivity;
import com.example.androidcourse.EndlessModeActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), LoginActivity.class);
                //i.putExtra("PersonID", personID);
                startActivity(i);
            }
        });

        findViewById(R.id.btnEndless).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), EndlessModeActivity.class);

                startActivity(i);
            }
        });

    }


}
