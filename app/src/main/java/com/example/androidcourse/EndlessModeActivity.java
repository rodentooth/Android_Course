package com.example.androidcourse;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.service.autofill.TextValueSanitizer;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.androidcourse.Models.CustomMenuItemAdapter;
import com.example.androidcourse.Models.Menu;
import com.example.androidcourse.Models.MenuItem;
import com.example.androidcourse.Models.Score;

import java.util.concurrent.atomic.AtomicInteger;

import static android.content.SharedPreferences.*;


public class EndlessModeActivity extends AppCompatActivity {
    private static final String TAG = "debug";
    TextView score;
    TextView scoreRN;
    ImageView play;
    int scorecounter; //needs to be changed vor shared preferences

    Score scoreObj = Score.getInstance();


    public static final String SHAREDPREF = "sharedpref";
    public static final String HIGHSCORE = "highscore";


    // Temporary Menu Shizzle
    AlertDialog.Builder dialog;
    ListView listView;
    ArrayAdapter<MenuItem> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endlessmode);

        score = findViewById(R.id.tvScore);
        scoreRN = findViewById(R.id.tvScoreRN);
        play = findViewById(R.id.ivPlay);


        findViewById(R.id.openMenu).setOnClickListener((event) ->
        {
            ShowMenuDialog(Menu.getMenu(this),  false);
        });

        scoreObj.loadScore();

        scoreObj.getScorePoints().observe(this, new Observer<AtomicInteger>() {
            @Override
            public void onChanged(AtomicInteger atomicInteger) {
                Log.d(TAG, " "+ atomicInteger.get());
                scoreRN.setText("" + atomicInteger.get());
            }
        });

        //loadScore();
    }


    public void ShowMenuDialog(Menu menu, boolean reInit){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // this is optional
        }
        ListView listView = dialog.findViewById(R.id.lv_menu);
        TextView tv = dialog.findViewById(R.id.tv_popup_title);

        ArrayAdapter arrayAdapter = new CustomMenuItemAdapter(this, R.layout.custom_dialog_listitem_layout, menu.getMenuItems());
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener((adapterView, view, which, l) -> {
          //  Log.d(TAG, "showAssignmentsList: " + menu.getMenuItems().get(which).toString());
            MenuItem item = menu.getMenuItems().get(which);
            item.getItem().getEffect().runEffect();
            // TODO : Listen to click callbacks at the position
        });
        dialog.show();
    }







    // No longer in use
    public void getPoint(View view){
        scoreObj.addPoint(1);
        scoreRN.setText(Integer.toString(scoreObj.getScore()));
    }


    // No longer in use
    public void loadScore(){
        scoreRN.setText(Integer.toString(scoreObj.getScore()));
    }
}
