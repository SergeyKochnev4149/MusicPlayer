package com.example.musicplayer.logic;

import android.content.Context;

import com.example.musicplayer.MVP_Contract;
import com.example.musicplayer.data.AppData;
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
    private MusicFileAdapter musicFileAdapter;


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
            musicFileAdapter = new MusicFileAdapter(appContext, savedMusicFiles, this);
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
    public void clickOnSong(MusicFile musicFile) {
        musicPlayController.playSong(musicFile);

        if (playingSong != null) {
            setSongInfo_MiniPlayer(musicFile);
            mvpView.showPauseButton();
        } else
            mvpView.showFullScreenPlayer();

        playingSong = musicFile;
    }

    @Override
    public void clickOnPauseSongButton() {
        musicPlayController.pauseSong();
        if (fullScreenPlayer != null)
            fullScreenPlayer.showPlayButton();
        else
            mvpView.showPlayButton();
    }

    @Override
    public void clickOnPlaySongButton() {
        musicPlayController.playSong();
        if (fullScreenPlayer != null)
            fullScreenPlayer.showPauseButton();
        else
            mvpView.showPauseButton();
    }

    @Override
    public void clickOnNextSongButton() {
        if (musicFileAdapter.checkPositionIsLast_ListeningSong())
            return;

        MusicFile nextSong = musicFileAdapter.getNextSong();
        if (fullScreenPlayer != null) {
            setSongInfo_FullScreenPlayer(nextSong);
            fullScreenPlayer.showPauseButton();
        }else {
            setSongInfo_MiniPlayer(nextSong);
            mvpView.showPauseButton();
        }

        musicPlayController.playSong(nextSong);
        musicFileAdapter.positionOfPlayingSong++;
        playingSong = nextSong;
    }

    @Override
    public void clickOnPreviousSongButton() {
        if (musicFileAdapter.checkPositionIsFirst_ListeningSong())
            return;

        MusicFile previousSong = musicFileAdapter.getPreviousSong();
        if (fullScreenPlayer != null) {
            setSongInfo_FullScreenPlayer(previousSong);
            fullScreenPlayer.showPauseButton();
        }else {
            setSongInfo_MiniPlayer(previousSong);
            mvpView.showPauseButton();
        }

        musicPlayController.playSong(previousSong);
        musicFileAdapter.positionOfPlayingSong--;
        playingSong = previousSong;

    }

    @Override
    public void clickOnHideFullScreenPlayerButton() {
        setSongInfo_MiniPlayer(playingSong);
        if (musicPlayController.isPlay())
            mvpView.showPauseButton();
        else
            mvpView.showPlayButton();

        mvpView.showMiniPlayer();
        fullScreenPlayer.close();
        fullScreenPlayer = null;
    }

    @Override
    public void isCreated_FullScreenPlayer(Activity_FullScreenPlayer fullScreenPlayer) {
        this.fullScreenPlayer = fullScreenPlayer;
        setSongInfo_FullScreenPlayer(playingSong);

        if (musicPlayController.isPlay())
            fullScreenPlayer.showPauseButton();
        else
            fullScreenPlayer.showPlayButton();
    }

    @Override
    public void setSongInfo_MiniPlayer(MusicFile playingSong) {
        mvpView.setSongInfo(playingSong.getSongName(), playingSong.getSongAuthor(), playingSong.getAlbumArtUri());
    }

    @Override
    public void setSongInfo_FullScreenPlayer(MusicFile playingSong) {
        if (!musicPlayController.isPlay())
            fullScreenPlayer.showPlayButton();
        fullScreenPlayer.setSongInfo(playingSong.getSongName(), playingSong.getSongAuthor(), playingSong.getAlbumArtUri());
    }


}
