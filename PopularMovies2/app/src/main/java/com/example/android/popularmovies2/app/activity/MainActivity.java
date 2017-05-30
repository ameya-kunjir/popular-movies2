/*
 * Copyright (C) 2016 The Android Popular Movies Project
 */

package com.example.android.popularmovies2.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.android.popularmovies2.app.R;
import com.example.android.popularmovies2.app.fragment.DetailActivityFragment;
import com.example.android.popularmovies2.app.fragment.MainActivityFragment;
import com.example.android.popularmovies2.app.model.Movie;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.Callback {

    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    public boolean mTwoPane;
    String mSortOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSortOrder = PreferenceManager.getDefaultSharedPreferences(this)
                .getString(getString(R.string.pref_sort_order_key), getString(R.string.pref_sort_order_default));


        if (findViewById(R.id.movie_detail_container) != null) {
            // it is a two-pane layout
            mTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.movie_detail_container, new DetailActivityFragment(), DETAILFRAGMENT_TAG).commit();
            }
        } else {
            mTwoPane = false;
        }
    }

    /*
    /  Creates new DetailActivity for One-Pane layout
    /  Creates DetailActivityFragment
    */
    @Override
    public void onItemSelected(Movie movie) {
        //DetailActivityFragment detailsFragment =  (DetailActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_detail);

        if (mTwoPane) {
            //TWO pane layout
            Bundle args = new Bundle();
            args.putString("MovieDetails", movie.toString());

            DetailActivityFragment fragment = new DetailActivityFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction().replace(R.id.movie_detail_container, fragment, DETAILFRAGMENT_TAG).commit();
        } else {
            //One pane layout
            Intent intent = new Intent(this, DetailActivity.class).putExtra(Intent.EXTRA_TEXT, movie.toString());
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        String latestSortOrder = PreferenceManager.getDefaultSharedPreferences(this)
                .getString(getString(R.string.pref_sort_order_key), getString(R.string.pref_sort_order_default));

        if (latestSortOrder != null && !latestSortOrder.equalsIgnoreCase(mSortOrder)) {
            MainActivityFragment mainActivityFragment = (MainActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_movies);
            if (mainActivityFragment != null) {
                mainActivityFragment.requestMovieData();
            }


        }


    }
}
