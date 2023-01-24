package com.example.musicplayer;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.example.musicplayer.logic.music_control.MusicFile;
import com.example.musicplayer.ui.fragments.Fragment_SavedMusic;

import java.util.List;

public interface MVP_Contract {

    interface MVP_Model{
        List<MusicFile> getAllSavedMusicFiles(Context context);
        void getYoutubeMusicFiles();
    }

    interface MVP_View{
        void searchMusic_Youtube();
        void searchMusic_SavedFiles();
        void getPermission(String[] neededPermissions);
        void openFullScreenPlayer();
        void showFragment(Fragment fragment);
        void changeColor_FragmentButtons(boolean isActive_savedMusic);
    }

    interface MVP_Presenter{
        boolean checkPermission();

        void clickOnSavedMusic();

        void clickOnYoutubeMusic();
    }
}
