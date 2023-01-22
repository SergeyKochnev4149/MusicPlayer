package com.example.musicplayer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import com.example.musicplayer.fragments.PlayingSong;
import com.example.musicplayer.fragments.SavedMusic;
import com.example.musicplayer.fragments.YoutubeMusic;
import com.example.musicplayer.music_control.MusicFile;

import java.util.ArrayList;


@SuppressLint("ResourceAsColor")

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    private boolean isActive_SavedMusic;
    ImageView ivYoutubeMusic, ivSavedMusic;
    TextView tvMusic, tvFiles, tvSoundtrackName;
    CardView cvMusic, cvFiles, cvSongDescription;
    int colour_activeFragment, colour_inactiveFragment, flFragmentID = R.id.flFragment;
    FragmentTransaction fragmentTransaction;
    YoutubeMusic youtubeMusicFragment;
    PlayingSong playingSongFragment;
    SavedMusic savedMusicFragment;
    public static ArrayList<MusicFile> musicFiles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        musicFiles = new ArrayList<>();
        getPermission();
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

    private void getPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat
                    .requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
        } else
            getAllMusicFiles(this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED)
                ActivityCompat
                        .requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
            else getAllMusicFiles(this);

        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        setOnClickMethods();

        if (isActive_SavedMusic)
            openSavedMusic();
        else
            openYoutubeMusic();

    }

    public void openYoutubeMusic() {
        isActive_SavedMusic = false;

        ivYoutubeMusic.setColorFilter(colour_activeFragment);
        tvMusic.setTextColor(colour_activeFragment);
        ivSavedMusic.setColorFilter(colour_inactiveFragment);
        tvFiles.setTextColor(colour_inactiveFragment);
        setFragment();
    }

    public void openSavedMusic() {
        isActive_SavedMusic = true;

        ivYoutubeMusic.setColorFilter(colour_inactiveFragment);
        tvMusic.setTextColor(colour_inactiveFragment);
        ivSavedMusic.setColorFilter(colour_activeFragment);
        tvFiles.setTextColor(colour_activeFragment);
        setFragment();
    }


    private void setOnClickMethods() {
        cvMusic.setOnClickListener(v -> openYoutubeMusic());
        cvFiles.setOnClickListener(v -> openSavedMusic());
        cvSongDescription.setOnClickListener(v -> openSongDescriptionScreen());
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

    private void openSongDescriptionScreen() {
        fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        PlayingSong playingSong = new PlayingSong();
        fragmentTransaction.replace(R.id.clMain, playingSong);
        fragmentTransaction.addToBackStack("main");
        fragmentTransaction.commit();

    }

    public static void getAllMusicFiles(Context context) {
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA, // path
        };

        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        Cursor cursor = context.getContentResolver().query(uri, projection, selection, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String album = cursor.getString(0);
                String author = cursor.getString(1);
                String songName = cursor.getString(2);
                String duration = cursor.getString(3);
                String path = cursor.getString(4);


                MusicFile song = new MusicFile(album, author, songName, duration, path);
                musicFiles.add(song);
            }
            cursor.close();
        }
    }

}