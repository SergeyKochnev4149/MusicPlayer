package com.example.musicplayer.data;

import android.content.Context;

import com.example.musicplayer.MVP_Contract;
import com.example.musicplayer.logic.music_control.MusicFile;

import java.util.List;

public class AppData implements MVP_Contract.MVP_Model {
    @Override
    public List<MusicFile> getAllSavedMusicFiles(Context context) {
        return SavedMusic.getAllSavedMusicFiles(context);
    }

    @Override
    public void getYoutubeMusicFiles() {

    }
}
