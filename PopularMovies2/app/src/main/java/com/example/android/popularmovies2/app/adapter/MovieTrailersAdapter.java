/*
 * Copyright (C) 2016 The Android Popular Movies Project
 */
package com.example.android.popularmovies2.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.popularmovies2.app.R;
import com.example.android.popularmovies2.app.model.MovieTrailer;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by AK00481127 on 1/12/2017.
 */

public class MovieTrailersAdapter extends RecyclerView.Adapter<MovieTrailersAdapter.ViewHolder> {

    private List<MovieTrailer> movieTrailers;
    private Context con;
    private TrailerClickListener clickListener;

    public MovieTrailersAdapter(List<MovieTrailer> movieTrailers, Context con, TrailerClickListener listener) {
        this.movieTrailers = movieTrailers;
        this.con = con;
        this.clickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie_trailer, parent, false);
        ViewHolder vh = new ViewHolder((LinearLayout) v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Picasso.with(con).load(R.mipmap.trailericon).into(holder.trailerImage);
        holder.trailerText.setText("Trailer " + position + 1);
    }

    @Override
    public int getItemCount() {
        return movieTrailers.size();
    }

    public void setClickListener(TrailerClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface TrailerClickListener {
        void onClick(View v, int pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private ImageView trailerImage;
        private TextView trailerText;
        private LinearLayout layout;


        private ViewHolder(View linearLayout) {
            super(linearLayout);
            this.layout = (LinearLayout) linearLayout;
            this.trailerImage = (ImageView) layout.getChildAt(0);
            this.trailerText = (TextView) layout.getChildAt(1);
            //trailerText.setOnClickListener(this);
            linearLayout.setOnClickListener(this);
            trailerImage.setClickable(true);
            setClickListener(clickListener);
        }

        @Override
        public void onClick(View view) {

            if (clickListener != null) {
                clickListener.onClick(view, getAdapterPosition());

            }
        }
    }
}
