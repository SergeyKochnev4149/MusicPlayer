package com.example.musicplayer.music_control;


import android.content.Context;
import android.media.MediaMetadataRetriever;
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
import java.util.ArrayList;


public class MusicFileAdapter extends RecyclerView.Adapter<MusicFileAdapter.MusicViewHolder> {


    private Context context;
    private ArrayList<MusicFile> musicFiles;


    public MusicFileAdapter(Context context, ArrayList<MusicFile> musicFiles) {
        this.context = context;
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
        holder.tvSongAuthor.setText(musicFiles.get(position).getSongAuthor());
        holder.tvSongName.setText(musicFiles.get(position).getSongName());
        byte[] image = getSongArt(musicFiles.get(position).getPath());
        if (image != null)
            Glide.with(context).asBitmap().load(image).into(holder.ivSoundtrackCover);


    }

    @Override
    public int getItemCount() {
        return musicFiles.size();
    }

    public class MusicViewHolder extends RecyclerView.ViewHolder{
        TextView tvSongName, tvSongAuthor;
        ImageView ivSoundtrackCover;

        public MusicViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSongAuthor = itemView.findViewById(R.id.tvSongAuthor);
            tvSongName = itemView.findViewById(R.id.tvSongName);
            ivSoundtrackCover = itemView.findViewById(R.id.ivSoundtrackCover);
        }
    }

    private byte[] getSongArt(String uri) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art = retriever.getEmbeddedPicture();
        try {
            retriever.release();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return art;
    }

}
