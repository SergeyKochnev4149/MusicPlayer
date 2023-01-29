package com.example.musicplayer.logic;

import android.content.Context;

import com.example.musicplayer.MVP_Contract;
import com.example.musicplayer.data.AppData;
import com.example.musicplayer.logic.music_control.MusicFile;
import com.example.musicplayer.logic.music_control.MusicPlayController;
import com.example.musicplayer.ui.Activity_FullScreenPlayer;
import com.example.musicplayer.ui.Fragment_SavedMusic;
import com.example.musicplayer.ui.MusicFileAdapter;

import java.util.List;

public class AppLogic implements MVP_Contract.MVP_Presenter {
    public static boolean isActive_SavedMusic;
    private final MVP_Contract.MVP_View.AppUI mvpView;
    private final MVP_Contract.MVP_Model mvpModel;
    private final Context appContext;
    private final MusicPlayController musicPlayController;
    private MVP_Contract.MVP_View.FullScreenPlayer fullScreenPlayer;
    private Fragment_SavedMusic savedMusicFragment;
    private MusicFile playingSong;


    public AppLogic(MVP_Contract.MVP_View.AppUI mvpView, Context appContext) {
        this.mvpView = mvpView;
        this.appContext = appContext;
        mvpModel = new AppData();
        musicPlayController = MusicPlayController.getInstance();
        musicPlayController.setAppContext(appContext);
    }


    @Override
    public void clickOnSavedMusicButton() {

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
    public void clickOnYoutubeMusicButton() {

    }

    @Override
    public void clickOnMusicDescription() {
        mvpView.showFullScreenPlayer();
    }

    @Override
    public void clickOnSong(MusicFile musicFile) {
        musicPlayController.playSong(musicFile);

        if (playingSong != null) {
            setSongInfo_MiniPlayer(musicFile);
            mvpView.showPauseButton();
        }else
            mvpView.showFullScreenPlayer();

        playingSong = musicFile;
    }


    @Override
    public void clickOnPauseSongButton() {
        musicPlayController.pauseSong();
        mvpView.showPlayButton();
        fullScreenPlayer.showPlayButton();
    }

    @Override
    public void clickOnPlaySongButton() {
        musicPlayController.playSong();
        mvpView.showPauseButton();
        fullScreenPlayer.showPauseButton();
    }

    @Override
    public void clickOnNextSongButton() {

    }

    @Override
    public void clickOnPreviousSongButton() {

    }

    @Override
    public void clickOnHideFullScreenPlayerButton() {
        setSongInfo_MiniPlayer(playingSong);
        mvpView.showMiniPlayer();
    }

    @Override
    public void isCreated_FullScreenPlayer(Activity_FullScreenPlayer fullScreenPlayer) {
        this.fullScreenPlayer = fullScreenPlayer;
        setSongInfo_FullScreenPlayer(playingSong);
    }

    @Override
    public void setSongInfo_MiniPlayer(MusicFile playingSong) {
        mvpView.setSongInfo(playingSong.getSongName(), playingSong.getSongAuthor(), playingSong.getAlbumArtUri());
    }

    @Override
    public void setSongInfo_FullScreenPlayer(MusicFile playingSong) {
        if (!musicPlayController.isPlay())
            fullScreenPlayer.showPlayButton();
        fullScreenPlayer.setSongDescription(playingSong.getSongName(), playingSong.getSongAuthor(), playingSong.getAlbumArtUri());
    }


}
