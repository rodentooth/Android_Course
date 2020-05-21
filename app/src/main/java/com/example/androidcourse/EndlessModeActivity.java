package com.example.androidcourse;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

        // scoreObj =  scoreObj.getScore();



        score = findViewById(R.id.tvScore);
        scoreRN = findViewById(R.id.tvCurrentScore);
        play = findViewById(R.id.ivPlayButton);


        findViewById(R.id.openMenu).setOnClickListener((event) ->
        {
            ShowMenuDialog(Menu.getMenu(this),  false);
        });

        scoreObj.loadScore();

        scoreObj.getScorePoints().observe(this, new Observer<AtomicInteger>() {
            @Override
            public void onChanged(AtomicInteger atomicInteger) {
                scoreRN.setText("" + atomicInteger.get()); // update Score text if variable changes
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
            MenuItem item = menu.getMenuItems().get(which);
            item.getItem().getEffect().runEffect(); // Executes the effect of the bought Item
            dialog.dismiss();
        });
        dialog.show();
    }




    public void getPoint(View view){
        scoreObj.addPoint(1);
    }


    // No longer in use
    public void loadScore(){
        scoreRN.setText(Integer.toString(scoreObj.getScore()));
    }
}
