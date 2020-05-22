package com.example.androidcourse;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class stats_fragment extends Fragment {


    private static final String SHAREDPREF = "sharedpref";
    private static final String CURRSCOREFINDCURE = "currscorefindcure";
    private static final String HIGHSCORE = "highscore";

    SharedPreferences sharedPreferences = App.getAppContext().getSharedPreferences(SHAREDPREF, App.getAppContext().MODE_PRIVATE);

    TextView scoreAllTaps;
    TextView nrAllCuresFound;

    public stats_fragment() {
// Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_stats, container, false); // Inflate the layout for this fragment

        scoreAllTaps = view.findViewById(R.id.tvStatAllClicks);
        scoreAllTaps.setText("Total Clicks: "+calculateAllClicksFromAllModes().toString());

        nrAllCuresFound = view.findViewById(R.id.statNrCuresFound);
        nrAllCuresFound.setText("Cures Found: "+sharedPreferences.getInt("nrofcuresfound", 0));

        return view;

    }

// returns the sum of all clicks from all game modes
    public Integer calculateAllClicksFromAllModes(){

        Integer sum = 0;

        Integer sCure = sharedPreferences.getInt("currscorefindcure", 0);
        String sEndless = sharedPreferences.getString("highscore", "");
        Integer i = Integer.parseInt(sEndless.replaceAll("[\\D]", "")); // as the highscore of endlessmode is stored as a string, remove all non-int chars

        System.out.println(i);

        sum = sCure + i;


        return sum;
    }
}
