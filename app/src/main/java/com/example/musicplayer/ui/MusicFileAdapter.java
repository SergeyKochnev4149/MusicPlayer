package com.example.musicplayer.ui;


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
import com.example.musicplayer.logic.MusicFile;

import java.util.List;


public class MusicFileAdapter extends RecyclerView.Adapter<MusicFileAdapter.MusicViewHolder> {


    private final Context context;
    private final List<MusicFile> musicFiles;
    private final MVP_Contract.MVP_Presenter presenter;
    public int positionOfPlayingSong;


    public MusicFileAdapter(Context context, List<MusicFile> musicFiles, MVP_Contract.MVP_Presenter presenter) {
        this.context = context;
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

//      Set image
        Glide.with(context).asDrawable()
                .load(albumArtUri)
                .error(R.drawable.ic_music_sheet) // set standard image when album art cant load
                .placeholder(R.drawable.ic_music_sheet) // show standard image while album arl load
                .into(holder.ivSoundtrackCover);


    }

    @Override
    public int getItemCount() {
        return musicFiles.size();
    }

    public class MusicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
            if (position != RecyclerView.NO_POSITION) {
                clickOnSong(musicFiles.get(position));
                positionOfPlayingSong = position;
            }
        }
    }


    private void clickOnSong(MusicFile musicFile) {
        presenter.clickOnSong(musicFile);
    }

    public boolean checkPositionIsLast_ListeningSong(){
        return positionOfPlayingSong == musicFiles.size() - 1;
    }

    public boolean checkPositionIsFirst_ListeningSong(){
        return positionOfPlayingSong == 0;
    }

    public MusicFile getNextSong(){
            return musicFiles.get(positionOfPlayingSong + 1);
    }

    public MusicFile getPreviousSong(){
        return musicFiles.get(positionOfPlayingSong - 1);
    }






}
