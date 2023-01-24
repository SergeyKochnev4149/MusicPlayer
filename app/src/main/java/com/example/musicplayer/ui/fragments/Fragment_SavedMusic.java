package com.example.musicplayer.ui.fragments;

import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.R;
import com.example.musicplayer.logic.music_control.MusicFileAdapter;



public class Fragment_SavedMusic extends Fragment {
    RecyclerView rvSavedMusic;
    private MusicFileAdapter musicFileAdapter;

    public Fragment_SavedMusic(MusicFileAdapter musicFileAdapter) {
        this.musicFileAdapter = musicFileAdapter;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_saved_music, container, false);
        rvSavedMusic = view.findViewById(R.id.rvSavedMusic);
        rvSavedMusic.setHasFixedSize(true);
        rvSavedMusic.setAdapter(musicFileAdapter);
        rvSavedMusic.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        return view;
    }



}