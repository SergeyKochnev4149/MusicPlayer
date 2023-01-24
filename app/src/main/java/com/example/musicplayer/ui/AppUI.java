package com.example.musicplayer.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.musicplayer.MVP_Contract;
import com.example.musicplayer.R;
import com.example.musicplayer.logic.AppLogic;
import com.example.musicplayer.ui.fragments.Fragment_FullScreenPlayer;
import com.example.musicplayer.ui.fragments.Fragment_SavedMusic;
import com.example.musicplayer.ui.fragments.Fragment_YoutubeMusic;


@SuppressLint("ResourceAsColor")

public class AppUI extends AppCompatActivity implements MVP_Contract.MVP_View {
    MVP_Contract.MVP_Presenter mvpPresenter;
    private static final int REQUEST_CODE = 1;
    ImageView ivYoutubeMusic, ivSavedMusic;
    TextView tvMusic, tvFiles;
    CardView cvMusic, cvFiles, cvSongDescription;
    int colour_activeFragment, colour_inactiveFragment, flFragmentID = R.id.flFragment;
    FragmentTransaction fragmentTransaction;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvpPresenter = new AppLogic(AppUI.this, getApplicationContext());
        setContentView(R.layout.activity_main);
        colour_activeFragment = getResources().getColor(R.color.icon_ActiveFragment);
        colour_inactiveFragment = getResources().getColor(R.color.white);
        cvMusic = findViewById(R.id.cvMusic);
        cvFiles = findViewById(R.id.cvFiles);
        ivYoutubeMusic = findViewById(R.id.ivYoutubeMusic);
        tvMusic = findViewById(R.id.tvMusic);
        ivSavedMusic = findViewById(R.id.ivSavedMusic);
        tvFiles = findViewById(R.id.tvFiles);
        cvSongDescription = findViewById(R.id.cvMiniPlayer_SongInfo);
        findViewById(R.id.tvMiniPlayer_SoundtrackName).setSelected(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setOnClickMethods();
    }

    @Override
    public void searchMusic_Youtube() {

    }

    @Override
    public void searchMusic_SavedFiles() {

    }

    @Override
    public void getPermission(String[] neededPermissions) {
        ActivityCompat.requestPermissions(this, neededPermissions, REQUEST_CODE);
    }



    public void changeColor_FragmentButtons(boolean isActive_SavedMusic ) {
        if (isActive_SavedMusic) {
            ivYoutubeMusic.setColorFilter(colour_inactiveFragment);
            tvMusic.setTextColor(colour_inactiveFragment);
            ivSavedMusic.setColorFilter(colour_activeFragment);
            tvFiles.setTextColor(colour_activeFragment);

        }else {
            ivYoutubeMusic.setColorFilter(colour_activeFragment);
            tvMusic.setTextColor(colour_activeFragment);
            ivSavedMusic.setColorFilter(colour_inactiveFragment);
            tvFiles.setTextColor(colour_inactiveFragment);
        }
    }

    public void clickOnSavedMusic() {
        mvpPresenter.clickOnSavedMusic();
    }

    public void clickOnYoutubeMusic() {
        mvpPresenter.clickOnYoutubeMusic();
    }

    @Override
    public void showFragment(Fragment fragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(flFragmentID, fragment);
        fragmentTransaction.commit();
    }


    private void setOnClickMethods() {
        cvMusic.setOnClickListener(v -> clickOnYoutubeMusic());
        cvFiles.setOnClickListener(v -> clickOnSavedMusic());
        cvSongDescription.setOnClickListener(v -> openFullScreenPlayer());
    }



    public void openFullScreenPlayer() {
        fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        Fragment_FullScreenPlayer playingSong = new Fragment_FullScreenPlayer();
        fragmentTransaction.replace(R.id.clMain, playingSong);
        fragmentTransaction.addToBackStack("main");
        fragmentTransaction.commit();
    }




}