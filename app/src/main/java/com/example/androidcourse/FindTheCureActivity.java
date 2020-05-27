package com.example.androidcourse;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.androidcourse.Models.CustomMenuItemAdapter;
import com.example.androidcourse.Models.Menu;
import com.example.androidcourse.Models.MenuItem;
import com.example.androidcourse.Models.Score;
import com.example.androidcourse.Models.ScoreFindCure;
import com.example.androidcourse.Models.SpeedTracker;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import java.util.concurrent.atomic.AtomicInteger;

public class FindTheCureActivity extends AppCompatActivity {


    private static final String TAG = "debug";

    TextView score;
    TextView difficulty;
    TextView scoreRN;
    TextView clickSpeed;
    ImageView play;

    ScoreFindCure scoreObj = ScoreFindCure.getInstance();

    SharedPreferences sharedPreferences = App.getAppContext().getSharedPreferences(SHAREDPREF, App.getAppContext().MODE_PRIVATE);

    public static final String SHAREDPREF = "sharedpref";
    public static final String CURETARGETSCORE = "curetargetscore";
    public static final String CUREDIFFICULTY = "curedifficulty";
    private static final String CURRSCOREFINDCURE = "currscorefindcure";
    private static final String NROFCURESFOUND = "nrofcuresfound";
    private static final String NROFCURESEASY = "nrofcureseasy";
    private static final String NROFCURESMEDIUM = "nrofcuresmedium";
    private static final String NROFCURESHARD = "nrofcureshard";
    private static final String NROFCURESIMPOSSIBLE = "nrofcuresimpossible";

    SpeedTracker s = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findthecuremode);

        score = findViewById(R.id.tvScore);
        difficulty = findViewById(R.id.tvDifficulty);
        scoreRN = findViewById(R.id.tvCurrentScore);
        play = findViewById(R.id.ivPlayButton);
        clickSpeed = findViewById(R.id.tvClickSpeed);
        final RelativeLayout ll = (RelativeLayout) findViewById(R.id.relativeLayout);

        difficulty.setText(sharedPreferences.getString("curedifficulty", null));
        int sharedPrefCureTarget = sharedPreferences.getInt("curetargetscore", 0);

        System.out.println("SharedPrefTargetScore: " + sharedPrefCureTarget);
        if (sharedPrefCureTarget == 0) {
            ShowDifficultyChoiceDialog();
        }

        // assign buttons


        findViewById(R.id.resetGame).setOnClickListener((event) -> {
            Dialog dialog = restartDialog();
            dialog.show();
        });

        scoreObj.loadScore();

        scoreObj.getScorePoints().observe(this, new Observer<AtomicInteger>() {
            @Override
            public void onChanged(AtomicInteger atomicInteger) {
                scoreRN.setText("" + atomicInteger.get()); // update Score text if variable changes
            }
        });

        ScoreFindCure.getInstance().getMidSum().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer sum) {
                TextView textView = new TextView(App.getAppContext());

                textView.setText("+ " + sum);

                String temp = "+ "+sum;
                SpannableString sumstyle = new SpannableString(temp);
                sumstyle.setSpan(new StyleSpan(Typeface.BOLD),0, sumstyle.length(),0);
                textView.setText(sumstyle);

                //random numbers between 150 and 950 px from left border
                int xlocation = new Random().nextInt(800) + 150;
                //random numbers between 600 and 1200 px from top
                int ylocation = new Random().nextInt(600) + 600;
                textView.setX(xlocation);
                textView.setY(ylocation);
                textView.setTextColor(Color.CYAN);
                textView.setTextSize(25);

                ll.addView(textView);
                Log.d(TAG, "" + sum); // update Score text if variable changes

                final Animation out = new AlphaAnimation(1.0f, 0.0f);

                out.setDuration(2000);
                out.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        ll.removeView(textView); // Remove it again, so it won't fill up the whole UI and memory
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                textView.startAnimation(out);
            }
        });
    }

    public void ShowDifficultyChoiceDialog() {
        AlertDialog.Builder difficultyDialog = new AlertDialog.Builder(FindTheCureActivity.this);
        difficultyDialog.setTitle("Choose a Difficulty");
        String[] listItems = getResources().getStringArray(R.array.difficultyFindCure);
        final TextView tvDifficulty = (TextView) findViewById(R.id.tvDifficulty);
        difficultyDialog.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tvDifficulty.setText("Difficulty: " + listItems[i]);
                setCureTargetScore(listItems[i]);
                dialogInterface.dismiss();
            }
        });


        AlertDialog dialog = difficultyDialog.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public void setCureTargetScore(String s) {

        int range = 0;
        int targetScore;

        switch (s) {
            case "Easy (Range: 100)":
                range = 100;
                break;
            case "Medium (Range: 1000)":
                range = 1000;
                break;
            case "Hard (Range: 10000)":
                range = 10000;
                break;
            case "Impossible! (Range: 100000)":
                range = 100000;
                break;
        }

        Random rand = new Random();
        targetScore = rand.nextInt(range - 0) + 1;

        // save in sharedPreference
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CUREDIFFICULTY, s);
        editor.putInt(CURETARGETSCORE, targetScore);
        editor.apply();
        System.out.println(sharedPreferences.getAll());

    }

    public void ShowMenuDialog(Menu menu, boolean reInit) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // this is optional
        }
        ListView listView = dialog.findViewById(R.id.lv_menu);

        ArrayAdapter arrayAdapter = new CustomMenuItemAdapter(this, R.layout.custom_dialog_listitem_layout, menu.getMenuItems());
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener((adapterView, view, which, l) -> {
            MenuItem item = menu.getMenuItems().get(which);
            item.getItem().getEffect().runEffect(); // Executes the effect of the bought Item
            dialog.dismiss();
        });
        dialog.show();
    }

    public void getPoint(View view) {
        scoreObj.addPoint(1, true);
        if (s != null) {
            clickSpeed.setText(s.trackTimeSpentForClick());
        } else {
            s = new SpeedTracker();
            clickSpeed.setText(s.trackTimeSpentForClick());

        }
        //check whether the cure was found!
        if (scoreObj.checkIfCureWasFound()) { // do stuff when the cure was found!
            increaseNrOfWinsFound();
            Dialog dialog = getGameWonDialog();
            dialog.show();
            resetValues();
        }
    }

    public Dialog getGameWonDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("You found the cure!");
        alertBuilder.setMessage("It took you " + sharedPreferences.getInt("curetargetscore", 0) + " clicks.");
        alertBuilder.setCancelable(true);

        alertBuilder.setPositiveButton(
                "Go To Main Menu",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        resetValues();
                        Intent i = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(i);
                    }
                });
        alertBuilder.setNegativeButton(
                "Play again!",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resetValues();
                        reloadActivity();
                    }
                }
        );


        AlertDialog dialog = alertBuilder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public Dialog restartDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("Are you sure you want to restart?");
        alertBuilder.setMessage("Your progress towards the cure will be lost!");
        alertBuilder.setCancelable(true);

        alertBuilder.setPositiveButton(
                "Restart!",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        resetValues();
                        reloadActivity();
                    }
                });
        alertBuilder.setNegativeButton(
                "Don't Restart",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = alertBuilder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;

    }

    public void increaseNrOfWinsFound() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Integer nrOfCures = sharedPreferences.getInt("nrofcuresfound", 0);
        nrOfCures++;
        editor.putInt(NROFCURESFOUND, nrOfCures);
        editor.apply();

        // depending on what gamemode was played, increase the number of wins in that category
        String s = sharedPreferences.getString("curedifficulty", null);
        switch (s) {
            case "Easy (Range: 100)":
                Integer nrOfWinsEasy = sharedPreferences.getInt("nrofcureseasy", 0);
                nrOfWinsEasy++;
                editor.putInt(NROFCURESEASY, nrOfWinsEasy);
                editor.apply();
                break;
            case "Medium (Range: 1000)":
                Integer nrOfWinsMedium = sharedPreferences.getInt("nrofcuresmedium", 0);
                nrOfWinsMedium++;
                editor.putInt(NROFCURESMEDIUM, nrOfWinsMedium);
                editor.apply();
                break;
            case "Hard (Range: 10000)":
                Integer nrOfWinsHard = sharedPreferences.getInt("nrofcureshard", 0);
                nrOfWinsHard++;
                editor.putInt(NROFCURESHARD, nrOfWinsHard);
                editor.apply();
                break;
            case "Impossible! (Range: 100000)":
                Integer nrOfWinsImpossible = sharedPreferences.getInt("nrofcuresimpossible", 0);
                nrOfWinsImpossible++;
                editor.putInt(NROFCURESIMPOSSIBLE, nrOfWinsImpossible);
                editor.apply();
                break;
        }

    }

    public void resetValues() {
        // resets the values in the sharedPref
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CUREDIFFICULTY, null);
        editor.putInt(CURETARGETSCORE, 0);
        editor.putInt(CURRSCOREFINDCURE, 0);
        editor.apply();
        System.out.println(sharedPreferences.getAll());

    }

    public void reloadActivity() { // restarts the activity
        finish();
        startActivity(getIntent());
    }

}


