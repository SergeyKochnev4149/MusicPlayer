package com.example.musicplayer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;

import com.example.musicplayer.fragments.PlayingSong;
import com.example.musicplayer.fragments.SavedMusic;
import com.example.musicplayer.fragments.YoutubeMusic;


@SuppressLint("ResourceAsColor")

public class MainActivity extends AppCompatActivity {
    private boolean isActive_SavedMusic;
    ImageView ivYoutubeMusic, ivSavedMusic;
    TextView tvMusic, tvFiles, tvSoundtrackName;
    CardView cvMusic, cvFiles;
    int colour_activeFragment, colour_inactiveFragment, flFragmentID = R.id.flFragment;
    FragmentTransaction fragmentTransaction;
    YoutubeMusic youtubeMusicFragment;
    PlayingSong playingSongFragment;
    SavedMusic savedMusicFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        colour_activeFragment = getResources().getColor(R.color.icon_ActiveFragment);
        colour_inactiveFragment = getResources().getColor(R.color.white);
        cvMusic = findViewById(R.id.cvMusic);
        cvFiles = findViewById(R.id.cvFiles);
        ivYoutubeMusic = findViewById(R.id.ivYoutubeMusic);
        tvMusic = findViewById(R.id.tvMusic);
        ivSavedMusic = findViewById(R.id.ivSavedMusic);
        tvFiles = findViewById(R.id.tvFiles);
        findViewById(R.id.tvSoundtrackName).setSelected(true);
    }


    @Override
    protected void onStart() {
        super.onStart();
        setOnClickMethods();

        if (isActive_SavedMusic)
            goToSavedMusic();
        else
            goToYoutubeMusic();

    }

    public void goToYoutubeMusic() {
        isActive_SavedMusic = false;

        ivYoutubeMusic.setColorFilter(colour_activeFragment);
        tvMusic.setTextColor(colour_activeFragment);
        ivSavedMusic.setColorFilter(colour_inactiveFragment);
        tvFiles.setTextColor(colour_inactiveFragment);
        setFragment();
    }

    public void goToSavedMusic() {
        isActive_SavedMusic = true;

        ivYoutubeMusic.setColorFilter(colour_inactiveFragment);
        tvMusic.setTextColor(colour_inactiveFragment);
        ivSavedMusic.setColorFilter(colour_activeFragment);
        tvFiles.setTextColor(colour_activeFragment);
        setFragment();
    }


    private void setOnClickMethods() {
        cvMusic.setOnClickListener(v -> goToYoutubeMusic());
        cvFiles.setOnClickListener(v -> goToSavedMusic());
    }

    private void setFragment() {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (isActive_SavedMusic) {
            savedMusicFragment = new SavedMusic();
            fragmentTransaction.replace(flFragmentID, savedMusicFragment);
        } else {
            youtubeMusicFragment = new YoutubeMusic();
            fragmentTransaction.replace(flFragmentID, youtubeMusicFragment);
        }
        fragmentTransaction.commit();

    }
}