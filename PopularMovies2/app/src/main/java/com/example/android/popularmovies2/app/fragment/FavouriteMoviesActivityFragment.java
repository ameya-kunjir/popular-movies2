/*
 * Copyright (C) 2016 The Android Popular Movies Project
 */
package com.example.android.popularmovies2.app.fragment;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.popularmovies2.app.R;
import com.example.android.popularmovies2.app.adapter.MoviesGridCursorAdapter;
import com.example.android.popularmovies2.app.data.MoviesContract;

/**
 * A placeholder fragment containing a simple view.
 */
public class FavouriteMoviesActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>
        , MoviesGridCursorAdapter.FavouriteClickListener {

    public static final int NUMBER_OF_COLUMNS_ON_SCREEN = 2;
    private static final int MOVIE_LOADER = 0;
    private static final String[] MOVIE_COLUMNS = {
            MoviesContract.MovieEntry._ID,
            MoviesContract.MovieEntry.COLUMN_TITLE,
            MoviesContract.MovieEntry.COLUMN_POSTER,
            MoviesContract.MovieEntry.COLUMN_SYNOPSIS,
            MoviesContract.MovieEntry.COLUMN_USER_RATING,
            MoviesContract.MovieEntry.COLUMN_RELEASE_DATE
    };
    private MoviesGridCursorAdapter mMovieCursorAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private int mCursorPosition;
    private FavouriteMoviesActivityFragment favouriteMoviesActivityFragment;

    public FavouriteMoviesActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_favourite_movies, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.favourite_movies_list_recyclerview);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(getActivity(), NUMBER_OF_COLUMNS_ON_SCREEN);

        mRecyclerView.setLayoutManager(mLayoutManager);
        favouriteMoviesActivityFragment = this;

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String sortOrder = MoviesContract.MovieEntry._ID + " ASC";
        Uri uri = MoviesContract.CONTENT_URI;
        return new CursorLoader(getActivity(), uri, MOVIE_COLUMNS, null, null, sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mMovieCursorAdapter = new MoviesGridCursorAdapter(getActivity(), cursor, favouriteMoviesActivityFragment);
        mMovieCursorAdapter.swapCursor(cursor);
        mRecyclerView.setAdapter(mMovieCursorAdapter);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mMovieCursorAdapter.swapCursor(null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(MOVIE_LOADER, null, this);
    }

    @Override
    public void onFavouriteItemClick(View view, int pos) {

        long rowId = mMovieCursorAdapter.getItemId(pos);
        ((Callback) getActivity()).onItemSelected(MoviesContract.MovieEntry.buildMovieUri(rowId));
        mCursorPosition = pos;
    }

    public interface Callback {
        public void onItemSelected(Uri itemUri);
    }
}
