package com.example.musicplayer.ui;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.musicplayer.MVP_Contract;
import com.example.musicplayer.R;


public class Activity_FullScreenPlayer extends AppCompatActivity implements MVP_Contract.MVP_View.FullScreenPlayer {
    public static MVP_Contract.MVP_Presenter presenter;
    private ImageButton ibtnHideWindow, imgbtnPreviousSong, ibtnPlay, ibtnPause, ibtnNextSong;
    private ImageView ivSoundtrackCover;
    private TextView tvSoundtrackAuthor, tvSoundtrackName;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_player);

        ibtnHideWindow = findViewById(R.id.ibtnHideWindow);

        // playing control buttons
        imgbtnPreviousSong = findViewById(R.id.imgbtnPreviousSong);
        ibtnPlay = findViewById(R.id.ibtnPlay);
        ibtnPause = findViewById(R.id.ibtnPause);
        ibtnNextSong = findViewById(R.id.ibtnNextSong);

        // song description
        ivSoundtrackCover = findViewById(R.id.ivSoundtrackCover);
        tvSoundtrackAuthor = findViewById(R.id.tvSoundtrackAuthor);
        tvSoundtrackName = findViewById(R.id.tvSoundtrackName);
        findViewById(R.id.tvSoundtrackName).setSelected(true);

        presenter.setFullScreenPlayer(this);
        presenter.onShowFullScreenPlayer();
    }

    @Override
    public void onStart() {
        super.onStart();
        setOnClickMethods();
    }

    @Override
    public void finish() {
        super.finish();
        presenter = null;
    }

    private void setOnClickMethods() {
        ibtnHideWindow.setOnClickListener(v -> clickOnHideFullScreenPlayerButton());

        imgbtnPreviousSong.setOnClickListener(v -> clickOnPreviousSong());
        ibtnPlay.setOnClickListener(v -> clickOnPlaySong());
        ibtnPause.setOnClickListener(v -> clickOnPauseSong());
        ibtnNextSong.setOnClickListener(v -> clickOnNextSong());
    }


// User action processing unit start

    private void clickOnPreviousSong() {
        presenter.clickOnPreviousSongButton();
    }

    private void clickOnPlaySong() {
        presenter.clickOnPlaySongButton();
    }

    private void clickOnPauseSong() {
        presenter.clickOnPauseSongButton();
    }

    private void clickOnNextSong() {
        presenter.clickOnNextSongButton();
    }

    private void clickOnHideFullScreenPlayerButton() {
        presenter.clickOnHideFullScreenPlayerButton();
        finish();
    }

// User action processing unit end.


//  Showing unit start

    @Override
    public void showPlayButton() {
        ibtnPause.setVisibility(View.GONE);
        ibtnPlay.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPauseButton() {
        ibtnPlay.setVisibility(View.GONE);
        ibtnPause.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSongDescription(String songName, String songAuthor, Uri albumArtUri) {
        tvSoundtrackName.setText(songName);
        tvSoundtrackAuthor.setText(songAuthor);

        if (albumArtUri != null)
            Glide.with(this).asDrawable().load(albumArtUri).into(ivSoundtrackCover);
        else
            Glide.with(this).load(R.drawable.ic_music_sheet).into(ivSoundtrackCover);
    }

//  Showing unit end.


    @Override
    public void onBackPressed() {
        clickOnHideFullScreenPlayerButton();
    }


}