package com.example.musicplayer.logic;

import android.net.Uri;


public class MusicFile {
    private String songAuthor, songName, album, duration, path;
    private Uri albumArtUri;

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Uri getAlbumArtUri() {
        return albumArtUri;
    }

    public void setAlbumArtUri(Uri albumArtUri) {
        this.albumArtUri = albumArtUri;
    }

    public MusicFile(String album, String author, String songName, String duration, String path, Uri albumArtUri) {
        this.album = album;
        this.songAuthor = author;
        this.songName = songName;
        this.duration = duration;
        this.path = path;
        this.albumArtUri = albumArtUri;
    }

    public String getSongAuthor() {
        return songAuthor;
    }

    public void setSongAuthor(String songAuthor) {
        this.songAuthor = songAuthor;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }
}
