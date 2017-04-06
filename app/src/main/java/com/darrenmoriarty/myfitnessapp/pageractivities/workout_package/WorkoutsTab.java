package com.darrenmoriarty.myfitnessapp.pageractivities.workout_package;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.darrenmoriarty.myfitnessapp.R;

/**
 * Created by Darren Moriarty on 28/03/2017.
 */

public class WorkoutsTab extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.workout_fragment, container, false);
        return rootView;


    }
}