package com.nautilus.omni.appmodules.tutorial.view;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.nautilus.omni.R;

public class TutorialFragment extends Fragment {
    int tutorial = 0;

    public int getTutorial() {
        return this.tutorial;
    }

    public void setTutorial(int tutorial2) {
        this.tutorial = tutorial2;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (this.tutorial == 0) {
            return inflater.inflate(R.layout.fragment_tutorial_1, container, false);
        }
        if (this.tutorial == 1) {
            return inflater.inflate(R.layout.fragment_tutorial_2, container, false);
        }
        if (this.tutorial == 2) {
            return inflater.inflate(R.layout.fragment_tutorial_3, container, false);
        }
        return inflater.inflate(R.layout.fragment_tutorial_4, container, false);
    }
}
