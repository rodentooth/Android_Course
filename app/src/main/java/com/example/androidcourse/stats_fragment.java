package com.example.androidcourse;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class stats_fragment extends Fragment {


    private static final String SHAREDPREF = "sharedpref";
    private static final String CURRSCOREFINDCURE = "currscorefindcure";
    private static final String HIGHSCORE = "highscore";
    public static final String ALLTIMECLICKSCURE = "alltimeclickscure";
    public static final String RECORDCLICKSPEED = "recordclickspeed";
    private static final String NROFCURESEASY = "nrofcureseasy";
    private static final String NROFCURESMEDIUM = "nrofcuresmedium";
    private static final String NROFCURESHARD = "nrofcureshard";
    private static final String NROFCURESIMPOSSIBLE = "nrofcuresimpossible";

    SharedPreferences sharedPreferences = App.getAppContext().getSharedPreferences(SHAREDPREF, App.getAppContext().MODE_PRIVATE);

    TextView scoreAllTaps;
    TextView nrAllCuresFound;
    TextView nrClicksEndless;
    TextView nrClicksCure;
    TextView recordClickSpeed;
    TextView nrOfEasyCures;
    TextView nrOfMediumCures;
    TextView nrOfHardCures;
    TextView nrOfImpossibleCures;

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

        // assign and fill the textviews
        scoreAllTaps = view.findViewById(R.id.tvStatAllClicks);
        scoreAllTaps.setText(calculateAllClicksFromAllModes().toString());

        nrClicksEndless = view.findViewById(R.id.tvStatEndlessClicks);
        String sEndless = sharedPreferences.getString("highscore", "");

        Integer i;
        if (sEndless == ""){ // handle the possibility that the string is empty and could not get converted to a number
            i = 0;
        } else {
            i = Integer.parseInt(sEndless.replaceAll("[\\D]", "")); // as the highscore of endlessmode is stored as a string, remove all non-int chars
        }

        nrClicksEndless.setText(i+"");

        nrClicksCure = view.findViewById(R.id.tvStatAllCureClicks);
        nrClicksCure.setText(sharedPreferences.getInt("alltimeclickscure", 0)+"");

        nrAllCuresFound = view.findViewById(R.id.statNrCuresFound);
        nrAllCuresFound.setText(sharedPreferences.getInt("nrofcuresfound", 0)+"");

        nrOfEasyCures = view.findViewById(R.id.nrOfEasyCures);
        nrOfEasyCures.setText(sharedPreferences.getInt("nrofcureseasy", 0)+"");

        nrOfMediumCures = view.findViewById(R.id.nrOfMediumCures);
        nrOfMediumCures.setText(sharedPreferences.getInt("nrofcuresmedium", 0)+"");

        nrOfHardCures = view.findViewById(R.id.nrOfHardCures);
        nrOfHardCures.setText(sharedPreferences.getInt("nrofcureshard", 0)+"");

        nrOfImpossibleCures = view.findViewById(R.id.nrOfImpossibleCures);
        nrOfImpossibleCures.setText(sharedPreferences.getInt("nrofcuresimpossible", 0)+"");

        recordClickSpeed = view.findViewById(R.id.statClickSpeed);
        DecimalFormat df = new DecimalFormat(".00");
        df.setRoundingMode(RoundingMode.DOWN); //round down
        recordClickSpeed.setText(df.format(sharedPreferences.getFloat("recordclickspeed", 0))+" Clicks/Sec.");



        return view;

    }

// returns the sum of all clicks from all game modes
    public Integer calculateAllClicksFromAllModes(){

        Integer sum = 0;
        Integer i;

        Integer sCure = sharedPreferences.getInt("alltimeclickscure", 0);
        String sEndless = sharedPreferences.getString("highscore", "");
        if (sEndless == ""){
             i = 0;
        } else{
             i = Integer.parseInt(sEndless.replaceAll("[\\D]", "")); // as the highscore of endlessmode is stored as a string, remove all non-int chars
        }


        System.out.println(i);

        sum = sCure + i;


        return sum;
    }
}
