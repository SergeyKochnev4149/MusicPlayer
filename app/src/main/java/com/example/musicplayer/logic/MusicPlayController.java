package com.example.musicplayer.logic;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

//Singleton
public class MusicPlayController {
    private MediaPlayer mediaPlayer;
    private Context appContext;
    private static MusicPlayController instance;

    private MusicPlayController() {
    }

    public static MusicPlayController getInstance() {
        if (instance == null)
            instance = new MusicPlayController();

        return instance;
    }

    public void setAppContext(Context appContext) {
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

    public void pauseSong() {
        mediaPlayer.pause();
    }

    public void playSong() {
        if (mediaPlayer != null)
            mediaPlayer.start();
    }

    public boolean isPlay(){
        if (mediaPlayer != null){
            return mediaPlayer.isPlaying();
        }
        return false;
    }

}
