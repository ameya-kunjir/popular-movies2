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

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

/*
        if (savedInstanceState == null) {
            DetailActivityFragment detailFragment = new DetailActivityFragment();

            detailFragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction().add(R.id.fragment_detail, detailFragment).commit();
        }*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (id == R.id.action_show_fav_movies) {
            startActivity(new Intent(this, FavouriteMoviesActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


