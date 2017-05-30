/*
 * Copyright (C) 2016 The Android Popular Movies Project
 */

package com.example.android.popularmovies2.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.popularmovies2.app.R;
import com.example.android.popularmovies2.app.activity.FavouriteMoviesActivity;
import com.example.android.popularmovies2.app.activity.SettingsActivity;
import com.example.android.popularmovies2.app.adapter.MovieAdapter;
import com.example.android.popularmovies2.app.model.Movie;
import com.example.android.popularmovies2.app.model.MoviesResponse;
import com.example.android.popularmovies2.app.rest.ApiClient;
import com.example.android.popularmovies2.app.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements MovieAdapter.ClickListener {

    public static final String SORT_ORDER_POPULAR = "popular";
    public static final String SORT_ORDER_TOP_RATED = "top_rated";
    public static final int TWO_COLUMNS_ON_SCREEN = 2;
    public static final String TAG = MainActivityFragment.class.getSimpleName();
    public static MainActivityFragment mainFragment;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Movie> mMovieList;
    private int mItemPosition;


    public MainActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_show_fav_movies) {
            startActivity(new Intent(getActivity(), FavouriteMoviesActivity.class));
            return true;
        } else if (id == R.id.action_settings) {
            startActivity(new Intent(getActivity(), SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);


        mAdapter = new MovieAdapter(new ArrayList<Movie>(), getActivity(), this);

        mainFragment = this;

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.movie_list_recycler_view);

        mRecyclerView.setHasFixedSize(true);


        mLayoutManager = new GridLayoutManager(getActivity(), TWO_COLUMNS_ON_SCREEN);


        mRecyclerView.setLayoutManager(mLayoutManager);

        if (savedInstanceState != null && savedInstanceState.containsKey("selectedItem")) {
            mItemPosition = savedInstanceState.getInt("selectedItem");
        }

        return rootView;
    }

    /**
     * Querying Network Data on Activity Start up
     */
    @Override
    public void onStart() {
        super.onStart();
        //checking if network connection is available
        if (isOnline()) {

            requestMovieData();
        } else {
            Toast.makeText(getActivity(), "No Network Connection!", Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public void onItemClick(View v, int pos) {

        if (mMovieList != null) {
            Movie movie = mMovieList.get(pos);
            ((Callback) getActivity()).onItemSelected(movie);
            mItemPosition = pos;
        }
    }

    /*
    Retrieve MovieDb API data using Retrofit library
     */
    public void requestMovieData() {

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        String sortOrder = PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getString(getString(R.string.pref_sort_order_key), getString(R.string.pref_sort_order_default));


        Call<MoviesResponse> call = null;

        if (sortOrder.equalsIgnoreCase(SORT_ORDER_POPULAR)) {
            call = apiService.getPopularMovies(getString(R.string.movie_api_key));
        } else if (sortOrder.equalsIgnoreCase(SORT_ORDER_TOP_RATED)) {
            call = apiService.getTopRatedMovies(getString(R.string.movie_api_key));
        }

        if (call != null) {

            call.enqueue(new retrofit2.Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    if (response != null) {
                        List<Movie> movies = response.body().getResults();
                        mAdapter = new MovieAdapter(movies, getActivity(), mainFragment);
                        mMovieList = movies;
                        mRecyclerView.setAdapter(mAdapter);

                        // Log.d(TAG, "Number of movies received: " + movies.size());
                    } else {
                        Log.e(TAG, "Response Object is null");
                    }


                }

                @Override
                public void onFailure(Call<MoviesResponse> call, Throwable throwable) {
                    Log.e(TAG, throwable.toString());
                }
            });
        }
    }


    /**
     * Method to check internet connectivity
     */
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        if (mItemPosition != RecyclerView.NO_POSITION) {
            outState.putInt("selectedItem", mItemPosition);
        }
        super.onSaveInstanceState(outState);
    }


    public interface Callback {
        public void onItemSelected(Movie movie);
    }
}
