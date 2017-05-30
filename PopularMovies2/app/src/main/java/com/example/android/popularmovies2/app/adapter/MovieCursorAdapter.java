/*
 * Copyright (C) 2016 The Android Popular Movies Project
 */

package com.example.android.popularmovies2.app.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies2.app.R;
import com.example.android.popularmovies2.app.data.MoviesContract;

import java.io.ByteArrayInputStream;

/**
 * Created by AK00481127 on 12/22/2016.
 */

public class MovieCursorAdapter extends CursorAdapter {

    public MovieCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie_poster, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        //ImageView imgView = (ImageView)view.findViewById(R.id.movie_poster_image);
        Bitmap imgPoster = convertCursorRowToUXFormat(cursor);
        viewHolder.posterImageView.setImageBitmap(imgPoster);

        int idx_movie_title = cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_TITLE);
        String title = cursor.getString(idx_movie_title);
        viewHolder.movieTitleTextView.setText(title);

    }

    /*
    Retrieves required data from Cursor for Poster Image
     */
    private Bitmap convertCursorRowToUXFormat(Cursor cursor) {
        // get row indices for our cursor
        int idx_poster_img = cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_POSTER);

        byte[] blob = cursor.getBlob(idx_poster_img);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(blob);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

        return bitmap;
    }


    public static class ViewHolder {

        public final ImageView posterImageView;
        public final TextView movieTitleTextView;
        public final TextView movieSynopsisTextView;
        public final TextView movieRatingTextView;
        public final TextView movieReleaseDateTextView;


        public ViewHolder(View view) {
            posterImageView = (ImageView) view.findViewById(R.id.movie_poster_img_view);
            movieTitleTextView = (TextView) view.findViewById(R.id.movie_title_text_view);
            movieSynopsisTextView = (TextView) view.findViewById(R.id.movie_overview_text_view);
            movieRatingTextView = (TextView) view.findViewById(R.id.movie_rating_text_view);
            movieReleaseDateTextView = (TextView) view.findViewById(R.id.movie_rel_date_text_view);

        }

    }


}
