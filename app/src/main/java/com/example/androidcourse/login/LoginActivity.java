package com.example.androidcourse.login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidcourse.App;
import com.example.androidcourse.MainActivity;
import com.example.androidcourse.R;
import com.example.androidcourse.data.HighscoreHandler;
import com.example.androidcourse.data.PostRequest;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class LoginActivity extends AppCompatActivity {



    EditText usernameEditText;
    EditText passwordEditText;
    EditText passwordRepeatEditText;
    EditText emailEditText;
    Button loginBtn;
    Button registerBtn;
    Button submitBtn;
    Button goHomeBtn;
    int highscore;
    String gamemode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                highscore= 0;
                gamemode=null;
            } else {
                highscore= extras.getInt("highscore");
                gamemode= extras.getString("gamemode");
            }
        } else {
            highscore= (int) savedInstanceState.getSerializable("highscore");
            gamemode= (String) savedInstanceState.getSerializable("gamemode");
        }



        usernameEditText         = findViewById(R.id.username);
        passwordEditText         = findViewById(R.id.password);
        passwordRepeatEditText   = findViewById(R.id.repeatPassword);
        emailEditText            = findViewById(R.id.email);
        loginBtn                   = findViewById(R.id.login);
        registerBtn                = findViewById(R.id.register);
        submitBtn                  = findViewById(R.id.submit);
        goHomeBtn                  = findViewById(R.id.backtohome);

        goHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent justanintent_buimm = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(justanintent_buimm);
                LoginActivity.this.finish();
            }
        });
        final boolean[] isRegistering = {true};

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {

                submitBtn.setEnabled(true);
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    //todo
                }
                return false;
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailEditText.setVisibility(View.INVISIBLE);
                passwordRepeatEditText.setVisibility(View.INVISIBLE);
                isRegistering[0]=false;

            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailEditText.setVisibility(View.VISIBLE);
                passwordRepeatEditText.setVisibility(View.VISIBLE);
                isRegistering[0] =true;

            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkTextFields(isRegistering[0])){

                    String URL;
                    ArrayList<String> postData = new ArrayList<>();
                    postData.add("username");
                    postData.add(usernameEditText.getText().toString());
                    postData.add("password");
                    postData.add(passwordEditText.getText().toString());

                    if(isRegistering[0]){
                        postData.add("email");
                        postData.add(emailEditText.getText().toString());
                        URL = "https://tappinggame.frozensparks.com/register.php";
                    }else{
                         URL = "https://tappinggame.frozensparks.com/login.php";
                    }
                    final PostRequest PR = new PostRequest(URL,postData,true);
                    PR.setOnPostExecuteFunction(new Callable<Void>() {
                        public Void call() {
                            return resultHandler(PR,LoginActivity.this);
                        }
                    });
                    PR.execute();

                }else{
                    Toast.makeText(LoginActivity.this, "Please check your entries", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void updateUiWithUser(Activity a) {
        String welcome = getString(R.string.welcome) ;

        SharedPreferences.Editor sharedPreferencesEditor = App.getAppContext().getSharedPreferences("login", App.getAppContext().MODE_PRIVATE).edit();
        sharedPreferencesEditor.putString("username", usernameEditText.getText().toString()).apply();
        sharedPreferencesEditor.apply();

        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();

        new HighscoreHandler(gamemode,highscore,a);

        Intent justanintent_buimm = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(justanintent_buimm);
        LoginActivity.this.finish();


    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private Void resultHandler(PostRequest pr, Activity a){

        String result = pr.getResult();
        Log.println(Log.DEBUG,"Result",result);
        if(result.contains("ERROR")){
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
        }else if(result.equals("0")){
            Toast.makeText(getApplicationContext(), "Wrong Credentials!", Toast.LENGTH_SHORT).show();
        }else if(result.equals("1")){
            updateUiWithUser(a);
        }

        return null;
    }

    private boolean checkTextFields(boolean isRegistering){


        if(isRegistering){
            if(emailEditText.getText().toString().length()<1){
                return false;
            }
            if(passwordEditText.getText().toString().length()<1|| !passwordEditText.getText().toString().equals(passwordRepeatEditText.getText().toString())){
                return false;
            }
        }
        if(usernameEditText.getText().toString().length()<1){
            return false;
        }
        if(passwordEditText.getText().toString().length()<1){
            return false;
        }
        return true;
    }
}
