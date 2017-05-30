/*
 * Copyright (C) 2016 The Android Popular Movies Project
 */
package com.example.android.popularmovies2.app.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies2.app.R;
import com.example.android.popularmovies2.app.data.MoviesContract;
import com.squareup.picasso.Target;

import java.io.ByteArrayInputStream;

/**
 * Created by AK00481127 on 1/17/2017.
 */

public class MoviesGridCursorAdapter extends CursorRecyclerViewAdapter<MoviesGridCursorAdapter.ViewHolder> {

    private FavouriteClickListener clickListener;
    private Target mTarget;
    private Context con;

    public MoviesGridCursorAdapter(Context context, Cursor cursor, FavouriteClickListener clickListener) {
        super(context, cursor);
        con = context;
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie_poster, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {

        Bitmap imgPoster = convertCursorRowToUXFormat(cursor);
        viewHolder.mImageView.setImageBitmap(imgPoster);


    }

    /*
    Retrieves required information from Cursor for Poster Image
     */
    private Bitmap convertCursorRowToUXFormat(Cursor cursor) {
        // get row indices for our cursor
        int idx_poster_img = cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_POSTER);

        byte[] blob = cursor.getBlob(idx_poster_img);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(blob);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

        return bitmap;
    }

    public interface FavouriteClickListener {
        void onFavouriteItemClick(View view, int pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView mImageView;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            mImageView = (ImageView) view.findViewById(R.id.movie_poster_image);
            view.setClickable(true);
        }

        @Override
        public void onClick(View view) {

            if (clickListener != null) {
                clickListener.onFavouriteItemClick(view, getAdapterPosition());
            }
        }
    }
}
