package com.example.musicplayer.logic.music_control;


import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicplayer.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MusicFileAdapter extends RecyclerView.Adapter<MusicFileAdapter.MusicViewHolder> {


    private Context context;
    private List<MusicFile> musicFiles;
    ContentResolver resolver;


    public MusicFileAdapter(Context context, List<MusicFile> musicFiles) {
        this.context = context;
        resolver = context.getContentResolver();
        this.musicFiles = musicFiles;
    }


    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.music_file_template, parent, false);

        return new MusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicViewHolder holder, int position) {
        Uri albumArtUri = musicFiles.get(position).getAlbumArtUri();
        try (InputStream inputStream = resolver.openInputStream(albumArtUri)) {
            if (inputStream.available() == 0)
                albumArtUri = null;
        } catch (IOException e) {
            albumArtUri = null;
        }


        if (albumArtUri != null)
            Glide.with(context).asDrawable().load(albumArtUri).into(holder.ivSoundtrackCover);
        else
            Glide.with(context).load(R.drawable.ic_music_sheet).into(holder.ivSoundtrackCover);

        holder.tvSongAuthor.setText(musicFiles.get(position).getSongAuthor());
        holder.tvSongName.setText(musicFiles.get(position).getSongName());
    }

    @Override
    public int getItemCount() {
        return musicFiles.size();
    }

    public class MusicViewHolder extends RecyclerView.ViewHolder {
        TextView tvSongName, tvSongAuthor;
        ImageView ivSoundtrackCover;

        public MusicViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSongAuthor = itemView.findViewById(R.id.tvSongAuthor);
            tvSongName = itemView.findViewById(R.id.tvSongName);
            ivSoundtrackCover = itemView.findViewById(R.id.ivSoundtrackCover);
        }
    }

}
