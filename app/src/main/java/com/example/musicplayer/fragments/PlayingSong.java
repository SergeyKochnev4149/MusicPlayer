package com.example.musicplayer.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.musicplayer.R;


public class PlayingSong extends Fragment {
    ImageButton ibtnHideWindow;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_playing_song, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        ibtnHideWindow = getView().findViewById(R.id.ibtnHideWindow);
        setOnClickMethods();
    }

    private void setOnClickMethods(){
        ibtnHideWindow.setOnClickListener(v -> hideWindow());
    }


    private void hideWindow(){
        getActivity().getSupportFragmentManager().popBackStack();
    }
}