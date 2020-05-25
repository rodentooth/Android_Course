package com.example.androidcourse;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.androidcourse.data.HighscoreHandler;
import com.example.androidcourse.data.PostRequest;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

public class leaderboard_fragment extends Fragment {



    ListView leaderboard_list_endless;
        public leaderboard_fragment() {
// Required empty public constructor
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);


        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);



            leaderboard_list_endless = view.findViewById(R.id.leaderboard_list_endless);


            ArrayList<String> postData = new ArrayList<>();
            postData.add("gamemode");
            postData.add("endless");

            final PostRequest PR = new PostRequest("https://tappinggame.frozensparks.com/getHighScore.php", postData, true);
            PR.setOnPostExecuteFunction(new Callable<Void>() {
                public Void call() {
                    return updateUI(PR,leaderboard_fragment.this);
                }
            });
            PR.execute();
            return view;
        }

        private Void updateUI(PostRequest pr, leaderboard_fragment leaderboard_fragment){

            List<String> your_array_list=null;
            String json = pr.getResult();
            JSONArray array=null;
            try {

                array = new JSONArray(json);

                Log.d("My App", array.toString());


            // Instanciating an array list (you don't need to do this,
            // you already have yours).
           your_array_list = new ArrayList<String>();



                for (int i = 0; i < array.length(); i++) {

                    String uname = null;
                    String highscore = null;
                    JSONObject objects = array.getJSONObject(i);
                    Iterator key = objects.keys();
                    while (key.hasNext()) {
                        String k = key.next().toString();
                        System.out.println("Key : " + k + ", value : "
                                + objects.getString(k));

                        if(k.equals("username"))
                            uname = objects.getString(k);
                        else                         if(k.equals("highscore"))
                            highscore = objects.getString(k);

                    }
                    if(highscore!=null)
                    your_array_list.add(highscore+" achieved by "+uname);

                    // System.out.println(objects.toString());
                    System.out.println("-----------");

                }


            } catch (Throwable t) {
                Log.e("My App", "Could not parse malformed JSON: \"" + json + "\"");
            }


            // This is the array adapter, it takes the context of the activity as a
            // first parameter, the type of list view as a second parameter and your
            // array as a third parameter.
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    leaderboard_fragment.getContext(),
                    R.layout.list_item_white,
                    your_array_list );

            leaderboard_list_endless.setAdapter(arrayAdapter);



            return null;
        }
    }

