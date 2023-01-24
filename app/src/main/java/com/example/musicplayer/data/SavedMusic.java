package com.example.musicplayer.data;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.musicplayer.logic.music_control.MusicFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SavedMusic {
    private static final Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    private static final String[] musicFileInfoTemplate = {
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.DATA, // for path
            MediaStore.Audio.Media.ALBUM_ID,

    };
    private static final String selection = "is_music == 1 & _size != 0";

    public static List<MusicFile> getAllSavedMusicFiles(Context context) {
        List<MusicFile> musicFiles = new ArrayList<>();

        Cursor cursor = context.getContentResolver().query(uri, musicFileInfoTemplate, selection, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String album = cursor.getString(0);
                String author = cursor.getString(1);
                String songName = cursor.getString(2);
                String duration = cursor.getString(3);
                String path = cursor.getString(4);
                long albumId = cursor.getLong(5);

                Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
                Uri albumArtUri = ContentUris.withAppendedId(sArtworkUri, albumId);

                MusicFile song = new MusicFile(album, author, songName, duration, path, albumArtUri);
                musicFiles.add(song);
            }
            cursor.close();
        }

        return musicFiles;
    }







}
