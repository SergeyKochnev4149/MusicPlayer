package com.example.musicplayer.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.musicplayer.MVP_Contract;
import com.example.musicplayer.R;
import com.example.musicplayer.logic.AppLogic;


@SuppressLint("ResourceAsColor")

public class AppUI extends AppCompatActivity implements MVP_Contract.MVP_View {
    MVP_Contract.MVP_Presenter mvpPresenter;
    private static final int REQUEST_CODE = 1;
    ImageView ivYoutubeMusic, ivSavedMusic;
    TextView tvMusic, tvFiles;
    CardView cvMusic, cvFiles, cvSongDescription;
    ConstraintLayout clMiniPlayer;
    int colour_activeFragmentButton, colour_inactiveFragmentButton, flFragmentID = R.id.flFragment;
    FragmentTransaction fragmentTransaction;
    private static String currentMethod; //used if more then 1 methods requires permissions


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvpPresenter = new AppLogic(AppUI.this, getApplicationContext());

        setContentView(R.layout.activity_main);
        initFragmentsControlPanel();

        clMiniPlayer = findViewById(R.id.clMiniPlayer);
        cvSongDescription = findViewById(R.id.cvMiniPlayer_SongInfo);
        findViewById(R.id.tvMiniPlayer_SoundtrackName).setSelected(true);
    }

    private void initFragmentsControlPanel() {
        colour_activeFragmentButton = getResources().getColor(R.color.icon_ActiveFragment);
        colour_inactiveFragmentButton = getResources().getColor(R.color.white);
        cvMusic = findViewById(R.id.cvMusic);
        cvFiles = findViewById(R.id.cvFiles);
        ivYoutubeMusic = findViewById(R.id.ivYoutubeMusic);
        tvMusic = findViewById(R.id.tvMusic);
        ivSavedMusic = findViewById(R.id.ivSavedMusic);
        tvFiles = findViewById(R.id.tvFiles);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setOnClickMethods();
    }


    private void setOnClickMethods() {
        cvMusic.setOnClickListener(v -> clickOnYoutubeMusic());
        cvFiles.setOnClickListener(v -> clickOnSavedMusic());
        cvSongDescription.setOnClickListener(v -> clickOnMusicDescription());
    }


// User action processing unit start

    private void clickOnSavedMusic() {
        String[] permission = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
        if (!hasPermissions(permission)) {
            currentMethod = "clickOnSavedMusic";
            getPermission(permission);
            return;
        }

        mvpPresenter.clickOnSavedMusic();
    }

    private void clickOnYoutubeMusic() {
        mvpPresenter.clickOnYoutubeMusic();
    }

    public void clickOnMusicDescription() {
        mvpPresenter.clickOnMusicDescription();
    }

// User action processing unit start


// Showing unit start

    public void changeColor_FragmentButtons(boolean isActive_SavedMusic) {
        if (isActive_SavedMusic) {
            ivYoutubeMusic.setColorFilter(colour_inactiveFragmentButton);
            tvMusic.setTextColor(colour_inactiveFragmentButton);
            ivSavedMusic.setColorFilter(colour_activeFragmentButton);
            tvFiles.setTextColor(colour_activeFragmentButton);

        } else {
            ivYoutubeMusic.setColorFilter(colour_activeFragmentButton);
            tvMusic.setTextColor(colour_activeFragmentButton);
            ivSavedMusic.setColorFilter(colour_inactiveFragmentButton);
            tvFiles.setTextColor(colour_inactiveFragmentButton);
        }
    }

    @Override
    public void showFragment(Fragment fragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(flFragmentID, fragment);
        fragmentTransaction.commit();
    }


    public void openFullScreenPlayer() {
        Intent intentFullScreenPlayer = new Intent(this, Activity_FullScreenPlayer.class);
        Activity_FullScreenPlayer.presenter = mvpPresenter;
        startActivity(intentFullScreenPlayer);
    }

    @Override
    public void showMiniPlayer() {
        clMiniPlayer.setVisibility(View.VISIBLE);
    }

    @Override
    public void searchMusic_Youtube() {

    }

    @Override
    public void searchMusic_SavedFiles() {

    }

//  Showing unit end.


// Permissions unit start

    public boolean hasPermissions(String[] neededPermissions) {
        for (String permission : neededPermissions) {
            if (checkCallingOrSelfPermission(permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    @Override
    public void getPermission(String[] neededPermissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(neededPermissions, REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(this, neededPermissions, REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //Checking whether the user has denied permission
                    // and whether the application can request permission again
                    if (shouldShowRequestPermissionRationale(permissions[i]))
                        checkSelfPermission(permissions[i]);
                    else {
                        String toast = "This functional can`t work without permission to read files. " +
                                "\nIf you need this function, you can change app permission in device settings";
                        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    getPermission(new String[]{permissions[i]});
                }
            }
        }

        //If app will be need permission from other methods, delete this and uncomment case
        currentMethod = null;
        clickOnSavedMusic();

//        switch (currentMethod) {
//            case "clickOnSavedMusic":
//                currentMethod = null;
//                clickOnSavedMusic();
//                break;
//        }

    }

//  Permissions unit end.


    //Is empty because without it, device close app when user press on system return button
    @Override
    public void onBackPressed() {
    }

}