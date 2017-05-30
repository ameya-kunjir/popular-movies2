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

import com.example.android.popularmovies2.app.R;
import com.example.android.popularmovies2.app.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Defines custom adapter which defines layout
 * for each data element
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private List<Movie> mMoviesList;
    private String baseUri;
    private Context con;
    private ClickListener clickListener;


    public MovieAdapter(List<Movie> movies, Context con, ClickListener listener) {
        this.mMoviesList = movies;
        this.con = con;
        this.baseUri = con.getString(R.string.base_url);
        this.clickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie_poster, parent, false);

        ViewHolder vh = new ViewHolder((ImageView) v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Movie movie = mMoviesList.get(position);
        Picasso.with(con).load(baseUri + movie.getMoviePosterURI().substring(1)).into(holder.mImageView);
        // holder.itemView.setSelected(true);
    }


    @Override
    public int getItemCount() {
        return mMoviesList.size();
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View v, int pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public ImageView mImageView;

        public ViewHolder(View v) {
            super(v);

            v.setOnClickListener(this);
            setClickListener(clickListener);
            mImageView = (ImageView) v;
            v.setClickable(true);
        }

        @Override
        public void onClick(View view) {

            if (clickListener != null) {
                clickListener.onItemClick(view, getAdapterPosition());

            }
        }
    }


}