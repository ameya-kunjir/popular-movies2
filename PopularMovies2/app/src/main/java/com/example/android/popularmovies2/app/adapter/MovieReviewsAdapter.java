/*
 * Copyright (C) 2016 The Android Popular Movies Project
 */
package com.example.android.popularmovies2.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies2.app.R;
import com.example.android.popularmovies2.app.model.MovieReview;

import java.util.List;

/**
 * Created by AK00481127 on 1/13/2017.
 */

public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsAdapter.ViewHolder> {

    Context context;
    List<MovieReview> movieReviews;


    public MovieReviewsAdapter(Context con, List<MovieReview> reviews) {
        this.context = con;
        this.movieReviews = reviews;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie_review, parent, false);
        ViewHolder vh = new ViewHolder((TextView) v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MovieReview movieReview = movieReviews.get(position);
        holder.review.setText(movieReview.getAuthor() + "\n" + movieReview.getContent());
    }

    @Override
    public int getItemCount() {
        return movieReviews.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        public TextView review;

        public ViewHolder(TextView review) {
            super(review);
            review.setOnClickListener(this);
            this.review = review;
        }

        @Override
        public void onClick(View view) {
            //Log.i(MovieReviewsAdapter.class.getSimpleName() , "Item clicked at position " + getAdapterPosition());
        }
    }
}
