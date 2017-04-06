package com.darrenmoriarty.myfitnessapp.pageractivities.goals_package;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.darrenmoriarty.myfitnessapp.R;

/**
 * Created by Darren Moriarty on 28/03/2017.
 */

public class GoalsTab extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.goals_fragment, container, false);
        return rootView;
    }
}
