package com.example.musicplayer.logic.music_control;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

public class MusicPlayController {
    //TODO this class Singleton
    MediaPlayer mediaPlayer;
    Context appContext;

    public MusicPlayController(Context appContext) {
        this.appContext = appContext;
    }

    public void playSong(MusicFile musicFile) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        Uri songUri = Uri.parse(musicFile.getPath());
        mediaPlayer = MediaPlayer.create(appContext, songUri);
        mediaPlayer.start();
    }

    public void stopSong() {
        mediaPlayer.stop();
    }

    public void nextSong() {
    }

    public void previousSong() {
    }


}
