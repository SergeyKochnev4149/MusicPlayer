package com.example.musicplayer;

import android.content.Context;
import android.net.Uri;

import androidx.fragment.app.Fragment;

import com.example.musicplayer.logic.music_control.MusicFile;
import com.example.musicplayer.ui.Activity_FullScreenPlayer;

import java.util.List;

public interface MVP_Contract {

    interface MVP_Model{
        List<MusicFile> getAllSavedMusicFiles(Context context);
        void getYoutubeMusicFiles();
    }

    interface MVP_View{
        interface AppUI {
            void showPlayButton();

            void showPauseButton();


            void searchMusic_Youtube();

            void searchMusic_SavedFiles();

            void getPermission(String[] neededPermissions);

            void showFullScreenPlayer();

            void showFragment(Fragment fragment);

            void changeColor_FragmentButtons(boolean isActive_savedMusic);

            void showMiniPlayer();

            void setSongInfo(String songName, String songAuthor, Uri albumArtUri);
        }

        interface FullScreenPlayer{
            void showPlayButton();

            void showPauseButton();

            void setSongDescription(String songName, String songAuthor, Uri albumArtUri);
        }
    }

    interface MVP_Presenter{

        void clickOnSavedMusicButton();

        void clickOnYoutubeMusicButton();

        void clickOnSong(MusicFile musicFile);

        void clickOnPlaySongButton();

        void clickOnNextSongButton();

        void clickOnPreviousSongButton();

        void clickOnPauseSongButton();

        void clickOnHideFullScreenPlayerButton();

        void clickOnMusicDescription();

        void setSongInfo_MiniPlayer(MusicFile playingSong);

        void setSongInfo_FullScreenPlayer(MusicFile playingSong);

        void isCreated_FullScreenPlayer(Activity_FullScreenPlayer activity_fullScreenPlayer);
    }
}
