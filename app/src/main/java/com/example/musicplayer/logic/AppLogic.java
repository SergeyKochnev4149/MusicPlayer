package com.example.musicplayer.logic;

import android.content.Context;

import com.example.musicplayer.MVP_Contract;
import com.example.musicplayer.data.AppData;
import com.example.musicplayer.logic.music_control.MusicFile;
import com.example.musicplayer.logic.music_control.MusicPlayController;
import com.example.musicplayer.ui.Fragment_SavedMusic;
import com.example.musicplayer.ui.MusicFileAdapter;

import java.util.List;

public class AppLogic implements MVP_Contract.MVP_Presenter {
    private final MVP_Contract.MVP_View mvpView;
    private final MVP_Contract.MVP_Model mvpModel;
    private final Context appContext;
    private final MusicPlayController musicPlayController;
    private Fragment_SavedMusic savedMusicFragment;

    public static boolean isActive_SavedMusic;


    public AppLogic(MVP_Contract.MVP_View mvpView, Context appContext) {
        this.mvpView = mvpView;
        this.appContext = appContext;
        mvpModel = new AppData();
        musicPlayController = MusicPlayController.getInstance();
        musicPlayController.setAppContext(appContext);
    }


    @Override
    public void clickOnSavedMusic() {

        if (savedMusicFragment == null) {
            List<MusicFile> savedMusicFiles = mvpModel.getAllSavedMusicFiles(appContext);
            MusicFileAdapter musicFileAdapter = new MusicFileAdapter(appContext, savedMusicFiles, this);
            savedMusicFragment = new Fragment_SavedMusic(musicFileAdapter);
        }

        isActive_SavedMusic = true;
        mvpView.changeColor_FragmentButtons(isActive_SavedMusic);
        mvpView.showFragment(savedMusicFragment);
    }

    @Override
    public void clickOnYoutubeMusic() {

    }

    @Override
    public void clickOnMusicDescription(){
        mvpView.openFullScreenPlayer();
    }

    @Override
    public void clickOnSong(MusicFile musicFile) {
        musicPlayController.playSong(musicFile);
        mvpView.openFullScreenPlayer();
    }

    @Override
    public void clickOnNextSong() {

    }

    @Override
    public void clickOnPreviousSong() {

    }

    @Override
    public void clickOnPlayOrStopSong() {

    }


    @Override
    public void clickOnHideFullScreenPlayer() {
        mvpView.showMiniPlayer();
    }



}
