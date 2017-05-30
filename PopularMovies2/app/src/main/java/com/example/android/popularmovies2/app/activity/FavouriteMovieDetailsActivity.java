/*
 * Copyright (C) 2016 The Android Popular Movies Project
 */

package com.example.android.popularmovies2.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.android.popularmovies2.app.R;

public class FavouriteMovieDetailsActivity extends AppCompatActivity {


    public static final String DETAIL_URI = "URI";
    private static final int MOVIE_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_movie_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.favourite_movie_details, new FavouriteMovieDetailsActivityFragment()).commit();
        }*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
