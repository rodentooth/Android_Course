package com.example.androidcourse.data;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.example.androidcourse.App;
import com.example.androidcourse.MainActivity;
import com.example.androidcourse.login.LoginActivity;
import com.example.androidcourse.splash;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class HighscoreHandler {

    public HighscoreHandler(String gamemode, int HighScore, Activity a) {
        System.out.println("highscoreHandler initiated");


        String username = App.getAppContext().getSharedPreferences("login", App.getAppContext().MODE_PRIVATE).getString("username", "");
        if (username.equals("")) {


            Intent justanintent_buimm = new Intent(a, LoginActivity.class);
            justanintent_buimm.putExtra("gamemode", gamemode);
            justanintent_buimm.putExtra("highscore", HighScore);
            a.startActivity(justanintent_buimm);
            a.finish();


        } else {

            System.out.println(username);
            ArrayList<String> postData = new ArrayList<>();
            postData.add("username");
            postData.add(username);
            postData.add("gamemode");
            postData.add(gamemode);
            postData.add("highscore");
            postData.add(String.valueOf(HighScore));

            final PostRequest PR = new PostRequest("http://tappinggame.frozensparks.com/uploadHighScore.php", postData, true);
            PR.setOnPostExecuteFunction(new Callable<Void>() {
                public Void call() {
                    return null;
                }
            });
            PR.execute();

        }

    }
}
