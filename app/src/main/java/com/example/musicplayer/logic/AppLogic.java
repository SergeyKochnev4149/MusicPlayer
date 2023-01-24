package com.example.musicplayer.logic;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.musicplayer.MVP_Contract;
import com.example.musicplayer.data.AppData;
import com.example.musicplayer.logic.music_control.MusicFile;
import com.example.musicplayer.logic.music_control.MusicFileAdapter;
import com.example.musicplayer.ui.fragments.Fragment_SavedMusic;

import java.util.List;

public class AppLogic implements MVP_Contract.MVP_Presenter {
    private MVP_Contract.MVP_View mvpView;
    private MVP_Contract.MVP_Model mvpModel;
    private Context appContext;
    private int countOfGetPermission;
    Fragment_SavedMusic savedMusicFragment;
    private static final String[] neededPermissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    public static boolean isActive_SavedMusic;


    public AppLogic(MVP_Contract.MVP_View mvpView, Context appContext) {
        this.mvpView = mvpView;
        this.appContext = appContext;
        mvpModel = new AppData();
    }

    public void startApp() {

    }


    //  Return true if app have a needed permissions and false if haven`t
    @Override
    public boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(appContext, neededPermissions[0]) == PackageManager.PERMISSION_GRANTED)
            return true;
        else if (countOfGetPermission < 2) {
            mvpView.getPermission(neededPermissions);
            countOfGetPermission++;
            return checkPermission();
        } else {
            String toast_HaveNotPermission =
                    "The application does not have permission to read audio from the device. " +
                            "\nPlease change the permission status in the device settings";

            Toast.makeText(appContext, toast_HaveNotPermission, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    public void clickOnSavedMusic() {

        if (!checkPermission()) {
            return;
        }

        if (savedMusicFragment == null) {
            List<MusicFile> savedMusicFiles = mvpModel.getAllSavedMusicFiles(appContext);
            MusicFileAdapter musicFileAdapter = new MusicFileAdapter(appContext, savedMusicFiles);
            savedMusicFragment = new Fragment_SavedMusic(musicFileAdapter);
        }

        isActive_SavedMusic = true;
        mvpView.changeColor_FragmentButtons(isActive_SavedMusic);
        mvpView.showFragment(savedMusicFragment);
    }

    @Override
    public void clickOnYoutubeMusic() {

    }


}
