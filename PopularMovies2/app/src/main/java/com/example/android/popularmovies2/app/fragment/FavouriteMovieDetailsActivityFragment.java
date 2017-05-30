/*
 * Copyright (C) 2016 The Android Popular Movies Project
 */
package com.example.android.popularmovies2.app.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies2.app.R;
import com.example.android.popularmovies2.app.activity.FavouriteMovieDetailsActivity;
import com.example.android.popularmovies2.app.data.MoviesContract;
import com.example.android.popularmovies2.app.utils.DbBitmapUtility;

//import android.app.LoaderManager;
//import android.content.CursorLoader;
//import android.content.Loader;

/**
 * A placeholder fragment containing a simple view.
 */
public class FavouriteMovieDetailsActivityFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int MOVIE_LOADER = 0;
    private static final String LOG_TAG = FavouriteMovieDetailsActivityFragment.class.getSimpleName();
    private static final String[] MOVIE_COLUMNS = {
            MoviesContract.MovieEntry._ID,
            MoviesContract.MovieEntry.COLUMN_MOVIE_ID,
            MoviesContract.MovieEntry.COLUMN_TITLE,
            MoviesContract.MovieEntry.COLUMN_POSTER,
            MoviesContract.MovieEntry.COLUMN_SYNOPSIS,
            MoviesContract.MovieEntry.COLUMN_USER_RATING,
            MoviesContract.MovieEntry.COLUMN_RELEASE_DATE
    };
    private TextView movieTitleTextView;
    private TextView movieSynopsisTextView;
    private TextView movieRatingTextView;
    private TextView movieRelDateTextView;
    private ImageView moviePosterImgView;
    private FavouriteMovieDetailsActivityFragment favouriteMovieDetailsActivityFragment;
    private Uri mUri;

    public FavouriteMovieDetailsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        if (arguments != null) {
            mUri = arguments.getParcelable(FavouriteMovieDetailsActivity.DETAIL_URI);
        }

        favouriteMovieDetailsActivityFragment = this;

        View rootView = inflater.inflate(R.layout.fragment_favourite_movie_details, container, false);
        TextView movieTitleTxtView = (TextView) rootView.findViewById(R.id.fav_movie_title_text_view);
        this.movieTitleTextView = movieTitleTxtView;

        TextView movieOverview = (TextView) rootView.findViewById(R.id.fav_movie_overview_text_view);
        movieSynopsisTextView = movieOverview;

        TextView movieRating = (TextView) rootView.findViewById(R.id.fav_movie_rating_text_view);
        movieRatingTextView = movieRating;

        TextView movieReleaseDate = (TextView) rootView.findViewById(R.id.fav_movie_rel_date_text_view);
        movieRelDateTextView = movieReleaseDate;

        ImageView movPosterImgView = (ImageView) rootView.findViewById(R.id.fav_movie_poster_img_view);
        moviePosterImgView = movPosterImgView;

        return rootView;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        Log.v(LOG_TAG, "In onCreateLoader ");
        Intent intent = getActivity().getIntent();
        Bundle args = getArguments();

        if (intent != null && intent.getData() != null) {
            return new CursorLoader(getActivity(), intent.getData(), MOVIE_COLUMNS, null, null, null);
        } else if (args != null && args.getParcelable(FavouriteMovieDetailsActivity.DETAIL_URI) != null) {
            return new CursorLoader(getActivity(), mUri, MOVIE_COLUMNS, null, null, null);
        } else {
            //when favourite details tab is loaded first time
            return null;

        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        //add code for setting up Values from table to views
        if (!cursor.moveToFirst()) {
            return;
        }

        String movieTitle = cursor.getString(cursor.getColumnIndex("title"));
        this.movieTitleTextView.setText(movieTitle);

        String movieSynopsis = cursor.getString(cursor.getColumnIndex("synopsis"));
        movieSynopsisTextView.setText(movieSynopsis);

        float movieRating = cursor.getFloat(cursor.getColumnIndex("user_rating"));
        movieRatingTextView.setText(String.valueOf(movieRating));

        String movieReleaseDate = cursor.getString(cursor.getColumnIndex("release_date"));
        movieRelDateTextView.setText(movieReleaseDate);

        byte[] posterImageInBytes = cursor.getBlob(cursor.getColumnIndex("poster"));
        Bitmap bitmap = DbBitmapUtility.getImage(posterImageInBytes);

        moviePosterImgView.setImageBitmap(bitmap);


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            getLoaderManager().initLoader(0, null, this);
        }
    }
}

