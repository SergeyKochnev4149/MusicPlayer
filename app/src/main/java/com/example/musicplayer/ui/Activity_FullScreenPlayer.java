package com.example.musicplayer.ui;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.musicplayer.MVP_Contract;
import com.example.musicplayer.R;


public class Activity_FullScreenPlayer extends AppCompatActivity {
    ImageButton ibtnHideWindow;
    public static MVP_Contract.MVP_Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_player);
        ibtnHideWindow = findViewById(R.id.ibtnHideWindow);
    }

    @Override
    public void onStart() {
        super.onStart();
        setOnClickMethods();
    }

    private void setOnClickMethods(){
        ibtnHideWindow.setOnClickListener(v -> hideWindow());
    }




    private void hideWindow(){
        presenter.clickOnHideFullScreenPlayer();
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        presenter = null;
    }



    @Override
    public void onBackPressed() {
        hideWindow();
    }
}