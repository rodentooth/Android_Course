package com.example.androidcourse;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.androidcourse.Models.CustomMenuItemAdapter;
import com.example.androidcourse.Models.Menu;
import com.example.androidcourse.Models.MenuItem;
import com.example.androidcourse.Models.Score;
import com.example.androidcourse.Models.SpeedTracker;
import com.example.androidcourse.data.HighscoreHandler;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;


public class EndlessModeActivity extends AppCompatActivity {
    private static final String TAG = "debug";
    TextView score;
    TextView scoreRN;
    ImageView play;
    TextView clickSpeed;
    TextView moneyTV;
    int scorecounter; //needs to be changed vor shared preferences

    Score scoreObj = Score.getInstance();

    public static final String SHAREDPREF = "sharedpref";
    public static final String HIGHSCORE = "highscore";

    SpeedTracker s = null;



    // Temporary Menu Shizzle
    AlertDialog.Builder dialog;
    ListView listView;
    ArrayAdapter<MenuItem> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endlessmode);
        final RelativeLayout ll = (RelativeLayout) findViewById(R.id.relativeLayout);
        scoreObj =  Score.getInstance();

        score = findViewById(R.id.tvScore);
        scoreRN = findViewById(R.id.tvCurrentScore);
        play = findViewById(R.id.ivPlayButton);
        clickSpeed = findViewById(R.id.tvClickspeed);
        moneyTV = findViewById(R.id.moneyScore);


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

        Activity a = this;
        scoreObj.getClickCount().observe(this, new Observer<AtomicInteger>() {
            @Override
            public void onChanged(AtomicInteger atomicInteger) {
                if(atomicInteger.intValue() % 50 == 0){
                    Log.d(TAG, "Updated Score to Database");
                    new HighscoreHandler("endless", Score.getInstance().getScorePoints().getValue().intValue(), a);
                }
            }
        });



        scoreObj.getMoney().observe(this, new Observer<AtomicInteger>() {
            @Override
            public void onChanged(AtomicInteger atomicInteger) {
                moneyTV.setText("" + atomicInteger); // update ^Money text if variable changes
            }
        });


        Score.getInstance().getMidSum().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer sum) {
                TextView textView = new TextView(App.getAppContext());
                String temp = "+ "+sum;
                SpannableString sumstyle = new SpannableString(temp);
                sumstyle.setSpan(new StyleSpan(Typeface.BOLD),0, sumstyle.length(),0);
                textView.setText(sumstyle);
                textView.setText(sumstyle);
                //random numbers between 150 and 950 px from left border
                int xlocation = new Random().nextInt(800) + 150;
                //random numbers between 600 and 1200 px from top
                int ylocation = new Random().nextInt(600) + 600;
                textView.setX(xlocation);
                textView.setY(ylocation);
                textView.setTextColor(Color.WHITE);
                textView.setTextSize(25);
                ll.addView(textView);
                final Animation out = new AlphaAnimation(1.0f, 0.0f);
                out.setDuration(2000);
                out.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        ll.removeView(textView);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                textView.startAnimation(out);
            }
        });
        //loadScore();
    }


    public void ShowMenuDialog(Menu menu, boolean reInit){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        ListView listView = dialog.findViewById(R.id.lv_menu);

        ArrayAdapter arrayAdapter = new CustomMenuItemAdapter(this, R.layout.custom_dialog_listitem_layout, menu.getMenuItems());
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener((adapterView, view, which, l) -> {
            MenuItem item = menu.getMenuItems().get(which);
            boolean success = Menu.getMenu(this).makeTransaction(item);
            if(success){
                item.getItem().getEffect().runEffect(); // Executes the effect of the bought Item
                dialog.dismiss();
            }else{
                Toast.makeText(App.getAppContext(), "Not enough Money", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
        //dialog.getWindow().setLayout();
    }




    public void getPoint(View view){
        scoreObj.addPoint(1, true);
        if (s != null){ //check if object is already initialized
            clickSpeed.setText(s.trackTimeSpentForClick());
        } else {
            s = new SpeedTracker();
            clickSpeed.setText(s.trackTimeSpentForClick());

        }

    }
}
