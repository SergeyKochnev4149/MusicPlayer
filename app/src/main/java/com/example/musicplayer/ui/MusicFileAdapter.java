package com.example.musicplayer.ui;


import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicplayer.MVP_Contract;
import com.example.musicplayer.R;
import com.example.musicplayer.logic.music_control.MusicFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class MusicFileAdapter extends RecyclerView.Adapter<MusicFileAdapter.MusicViewHolder> {


    private final Context context;
    private final List<MusicFile> musicFiles;
    private final ContentResolver resolver;
    private final MVP_Contract.MVP_Presenter presenter;


    public MusicFileAdapter(Context context, List<MusicFile> musicFiles, MVP_Contract.MVP_Presenter presenter) {
        this.context = context;
        resolver = context.getContentResolver();
        this.musicFiles = musicFiles;
        this.presenter = presenter;
    }


    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.music_file_template, parent, false);
        return new MusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicViewHolder holder, int position) {
        holder.tvSongAuthor.setText(musicFiles.get(position).getSongAuthor());
        holder.tvSongName.setText(musicFiles.get(position).getSongName());

        Uri albumArtUri = musicFiles.get(position).getAlbumArtUri();

        //Checking whatever song have album art
        if (albumArtUri != null) {
            try (InputStream inputStream = resolver.openInputStream(albumArtUri)) {
                if (inputStream.available() == 0) {
                    albumArtUri = null;
                    musicFiles.get(position).setAlbumArtUri(null);
                }
            } catch (IOException e) {
                albumArtUri = null;
                musicFiles.get(position).setAlbumArtUri(null);
            }
        }

        if (albumArtUri != null)
            Glide.with(context).asDrawable().load(albumArtUri).into(holder.ivSoundtrackCover);
        else
            Glide.with(context).load(R.drawable.ic_music_sheet).into(holder.ivSoundtrackCover);
    }

    @Override
    public int getItemCount() {
        return musicFiles.size();
    }

    public class MusicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvSongName, tvSongAuthor;
        ImageView ivSoundtrackCover;
        ConstraintLayout item;

        public MusicViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSongAuthor = itemView.findViewById(R.id.tvSongAuthor);
            tvSongName = itemView.findViewById(R.id.tvSongName);
            ivSoundtrackCover = itemView.findViewById(R.id.ivSoundtrackCover);
            item = itemView.findViewById(R.id.clMusicFileTemplate);
            item.setOnClickListener(v -> onClick(itemView));
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION){
                clickOnSong(musicFiles.get(position));
            }
        }
    }


    private void clickOnSong(MusicFile musicFile){
        presenter.clickOnSong(musicFile);
    }





}
