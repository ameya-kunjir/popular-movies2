/*
 * Copyright (C) 2016 The Android Popular Movies Project
 */

package com.example.android.popularmovies2.app.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.android.popularmovies2.app.R;
import com.example.android.popularmovies2.app.fragment.FavouriteMovieDetailsActivityFragment;
import com.example.android.popularmovies2.app.fragment.FavouriteMoviesActivityFragment;

public class FavouriteMoviesActivity extends AppCompatActivity implements FavouriteMoviesActivityFragment.Callback {

    private static final String FAVOURITE_MOVIE_DETAILFRAGMENT_TAG = "FDFTAG";
    private boolean mFavouriteTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_movies);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Checks if it is two pane layout or not
        if (findViewById(R.id.favourite_movie_details) != null) {
            // it is a two-pane layout
            mFavouriteTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.favourite_movie_details, new FavouriteMovieDetailsActivityFragment(), FAVOURITE_MOVIE_DETAILFRAGMENT_TAG).commit();
            }
        } else {
            mFavouriteTwoPane = false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //Checks if Settings was selected from menu
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /*
    /   On Click handler for RecyclerView
    /   for one pane layout it launches new DetailsActivity
    /   for two pane layout it replaces DetailsActivityFragment
    */
    @Override
    public void onItemSelected(Uri itemUri) {
        if (mFavouriteTwoPane) {
            Bundle args = new Bundle();
            args.putParcelable(FavouriteMovieDetailsActivity.DETAIL_URI, itemUri);

            FavouriteMovieDetailsActivityFragment favouriteMovieDetailsActivityFragment =
                    new FavouriteMovieDetailsActivityFragment();
            favouriteMovieDetailsActivityFragment.setArguments(args);

            getSupportFragmentManager().beginTransaction().replace(R.id.favourite_movie_details
                    , favouriteMovieDetailsActivityFragment, FAVOURITE_MOVIE_DETAILFRAGMENT_TAG)
                    .commit();
        } else {
            Intent intent = new Intent(this, FavouriteMovieDetailsActivity.class)
                    .setData(itemUri);
            startActivity(intent);
        }
    }


}
