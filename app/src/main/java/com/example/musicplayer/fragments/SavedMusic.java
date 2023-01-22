package com.example.musicplayer.fragments;

import static com.example.musicplayer.MainActivity.musicFiles;

import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.R;
import com.example.musicplayer.music_control.MusicFileAdapter;


public class SavedMusic extends Fragment {
    RecyclerView rvSavedMusic;
    MusicFileAdapter musicFileAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_saved_music, container, false);
        rvSavedMusic = view.findViewById(R.id.rvSavedMusic);
        rvSavedMusic.setHasFixedSize(true);
        if (musicFiles.size() > 0){
            musicFileAdapter = new MusicFileAdapter(getContext(), musicFiles);
            rvSavedMusic.setAdapter(musicFileAdapter);
            rvSavedMusic.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        }else
            Log.e("Error", "Empty musicFiles");
        return view;
    }
}