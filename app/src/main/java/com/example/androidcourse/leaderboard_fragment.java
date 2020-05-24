package com.example.androidcourse;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.androidcourse.data.HighscoreHandler;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class leaderboard_fragment extends Fragment {



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

            return inflater.inflate(R.layout.fragment_leaderboard, container, false);
        }

    }

