/*
 * Copyright (C) 2016 The Android Popular Movies Project
 */
package com.example.android.popularmovies2.app.fragment;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies2.app.R;
import com.example.android.popularmovies2.app.adapter.MovieReviewsAdapter;
import com.example.android.popularmovies2.app.adapter.MovieTrailersAdapter;
import com.example.android.popularmovies2.app.data.MoviesContract;
import com.example.android.popularmovies2.app.data.MoviesDbHelper;
import com.example.android.popularmovies2.app.model.MovieReview;
import com.example.android.popularmovies2.app.model.MovieReviewsResponse;
import com.example.android.popularmovies2.app.model.MovieTrailer;
import com.example.android.popularmovies2.app.model.MovieTrailerResponse;
import com.example.android.popularmovies2.app.rest.ApiClient;
import com.example.android.popularmovies2.app.rest.ApiInterface;
import com.example.android.popularmovies2.app.utils.DbBitmapUtility;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment implements MovieTrailersAdapter.TrailerClickListener {

    private static final String LOG_TAG = DetailActivityFragment.class.getSimpleName();
    private String YOUTUBE_BASE_URI;
    private List<MovieTrailer> mMovieTrailers;
    private List<MovieReview> mMovieReviews;
    private ShareActionProvider mShareActionProvider;
    private RecyclerView mRecyclerViewTrailers;
    private RecyclerView.Adapter mAdapterTrailers;
    private RecyclerView.LayoutManager mLayoutManagerTrailers;
    private RecyclerView mRecyclerViewReviews;
    private RecyclerView.Adapter mAdapterReviews;
    private RecyclerView.LayoutManager mLayoutManagerReviews;
    private ImageView moviePoster;
    private String mMovieTrailerUrl;
    private DetailActivityFragment detailActivityFragment;

    public DetailActivityFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        YOUTUBE_BASE_URI = getString(R.string.you_tube_base_uri);
        //Checks if any data is received from MainActivity via intent
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            String movieData = intent.getStringExtra(Intent.EXTRA_TEXT);
            final String[] movieDataSplitStr = movieData.split(":");

            setupMovieDetailsUI(movieDataSplitStr, rootView);

        } else {
            Bundle arguments = getArguments();
            if (arguments != null) {
                String movieDetails = arguments.getString("MovieDetails");
                final String[] movieDataSplitStr = movieDetails.split(":");
                setupMovieDetailsUI(movieDataSplitStr, rootView);
            }

        }
        detailActivityFragment = this;


        return rootView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_detail, menu);

        MenuItem menuItem = menu.findItem(R.id.action_share);

        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        if (mShareActionProvider != null) {
            Intent shareIntent = createShareMovieTrailerIntent();
            mShareActionProvider.setShareIntent(shareIntent);

        } else {
            Log.d(LOG_TAG, "Share Action Provider is null ?");
        }
    }

    /*
    Retrieves Data from MoviesDb using retrofit library
     */
    public void requestMovieTrailer(int movieId) {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);


        Call<MovieTrailerResponse> call = apiService.getMovieTrailer(movieId, getString(R.string.movie_api_key));

        if (call != null) {
            call.enqueue(new Callback<MovieTrailerResponse>() {
                @Override
                public void onResponse(Call<MovieTrailerResponse> call, Response<MovieTrailerResponse> response) {
                    List<MovieTrailer> movieTrailers = response.body().getMovieTrailers();
                    mMovieTrailers = movieTrailers;
                    mAdapterTrailers = new MovieTrailersAdapter(movieTrailers, getActivity(), detailActivityFragment);
                    mRecyclerViewTrailers.setAdapter(mAdapterTrailers);
                    MovieTrailer movieTrailer = mMovieTrailers.get(0);
                    if (movieTrailer != null) {
                        mMovieTrailerUrl = YOUTUBE_BASE_URI + movieTrailer.getKey();
                    }

                }

                @Override
                public void onFailure(Call<MovieTrailerResponse> call, Throwable throwable) {
                    Log.e(LOG_TAG, throwable.toString());
                }
            });
        }


    }

    /*
    Retrieves movie reviews data from MovieDB API for given Movie Id
     */
    public void requestMovieReviews(int movieId) {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<MovieReviewsResponse> call = apiService.getMovieReviews(movieId, getString(R.string.movie_api_key));

        if (call != null) {
            call.enqueue(new Callback<MovieReviewsResponse>() {
                @Override
                public void onResponse(Call<MovieReviewsResponse> call, Response<MovieReviewsResponse> response) {
                    List<MovieReview> movieReviews = response.body().getMovieReviews();
                    mMovieReviews = movieReviews;
                    mAdapterReviews = new MovieReviewsAdapter(getActivity(), mMovieReviews);
                    mRecyclerViewReviews.setAdapter(mAdapterReviews);
                }

                @Override
                public void onFailure(Call<MovieReviewsResponse> call, Throwable throwable) {
                    Log.e(LOG_TAG, throwable.toString());
                }
            });
        }

    }

    /*
    Launches Trailer for given movie using Youtube app or browser
     */

    public void launchTrailerVideo(int position) {
        MovieTrailer movieTrailer = mMovieTrailers.get(position);

        String videoURi = YOUTUBE_BASE_URI + movieTrailer.getKey();
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + movieTrailer.getKey()));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(videoURi));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }

    }

    /*
    inserts movie details in Movies Table
     */
    public void addFavouriteMovie(String[] movieData) {
        if (movieData == null) {
            Log.e(LOG_TAG, "Movie Data String array is empty");
            Toast.makeText(getActivity(), "Error Occured. Movie Could Not be added to favourites", Toast.LENGTH_LONG).show();
        } else {

            ContentValues cv = new ContentValues();
            cv.put(MoviesContract.MovieEntry.COLUMN_MOVIE_ID, movieData[0]);
            cv.put(MoviesContract.MovieEntry.COLUMN_TITLE, movieData[1]);


            BitmapDrawable drawable = (BitmapDrawable) moviePoster.getDrawable();
            Bitmap bitmap = drawable.getBitmap();

            cv.put(MoviesContract.MovieEntry.COLUMN_POSTER, DbBitmapUtility.getBytes(bitmap));
            cv.put(MoviesContract.MovieEntry.COLUMN_USER_RATING, movieData[4]);
            cv.put(MoviesContract.MovieEntry.COLUMN_SYNOPSIS, movieData[3]);
            cv.put(MoviesContract.MovieEntry.COLUMN_RELEASE_DATE, movieData[5]);

            SQLiteDatabase db = getDbConnection();

            db.insert(MoviesContract.MovieEntry.TABLE_NAME, null, cv);

            Toast.makeText(getActivity(), "Movie " + movieData[1] + " added successfully to favourites", Toast.LENGTH_LONG).show();

            db.close();
        }

    }


    public SQLiteDatabase getDbConnection() {
        SQLiteOpenHelper databaseHelper = new MoviesDbHelper(getActivity());
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        return database;
    }


    public boolean checkMovieAlreadyMarkedFavourite(int movieId) {
        Cursor moviesCursor = getActivity().getContentResolver().query(
                MoviesContract.CONTENT_URI,
                new String[]{"movie_id"},
                MoviesContract.MovieEntry.COLUMN_MOVIE_ID + " = ?",
                new String[]{String.valueOf(movieId)},
                null
        );

        if (moviesCursor.moveToFirst()) {

            return true;
        } else {
            return false;
        }
    }


    public void deleteFavouriteMovie(String[] movieData) {
        if (movieData == null) {
            Log.e(LOG_TAG, "Movie Data String array is empty");
            Toast.makeText(getActivity(), "Error Occured. Movie Could Not be deleted from favourites", Toast.LENGTH_LONG).show();
        } else {
            int movieId = Integer.parseInt(movieData[0]);
            SQLiteDatabase db = getDbConnection();
            String movieSelection =
                    MoviesContract.MovieEntry.COLUMN_MOVIE_ID + " = ? ";
            db.delete(MoviesContract.MovieEntry.TABLE_NAME, movieSelection, new String[]{String.valueOf(movieId)});
            Toast.makeText(getActivity(), "Movie " + movieData[1] + " removed successfully from favourites", Toast.LENGTH_LONG).show();
            db.close();
        }
    }

    public void setupMovieDetailsUI(final String[] movieDataSplitStr, View rootView) {


        int movieId = Integer.parseInt(movieDataSplitStr[0]);


        mRecyclerViewTrailers = (RecyclerView) rootView.findViewById(R.id.movie_trailer_recycler_view);

        mRecyclerViewTrailers.setHasFixedSize(true);

        mLayoutManagerTrailers = new LinearLayoutManager(getActivity());

        mRecyclerViewTrailers.setLayoutManager(mLayoutManagerTrailers);

        requestMovieTrailer(movieId);

        mRecyclerViewReviews = (RecyclerView) rootView.findViewById(R.id.movie_reviews_recycler_view);

        mRecyclerViewReviews.setHasFixedSize(true);

        mLayoutManagerReviews = new LinearLayoutManager(getActivity());

        mRecyclerViewReviews.setLayoutManager(mLayoutManagerReviews);

        requestMovieReviews(movieId);

        TextView movieTitleTxtView = (TextView) rootView.findViewById(R.id.movie_title_text_view);
        movieTitleTxtView.setText(movieDataSplitStr[1]);
        ImageView movPosterImgView = (ImageView) rootView.findViewById(R.id.movie_poster_img_view);


        Picasso.with(getActivity()).load(getActivity().getString(R.string.base_url) + movieDataSplitStr[2].substring(1)).into(movPosterImgView);
        moviePoster = movPosterImgView;
        moviePoster.buildDrawingCache(true);
        TextView movieReleaseDate = (TextView) rootView.findViewById(R.id.movie_rel_date_text_view);
        movieReleaseDate.setText(movieDataSplitStr[5]);
        TextView movieRating = (TextView) rootView.findViewById(R.id.movie_rating_text_view);
        movieRating.setText(movieDataSplitStr[4]);
        TextView movieOverview = (TextView) rootView.findViewById(R.id.movie_overview_text_view);
        movieOverview.setText(movieDataSplitStr[3]);

        Button addMovieButton = (Button) rootView.findViewById(R.id.mark_favourite_movie_button);

        Button deleteMovieButton = (Button) rootView.findViewById(R.id.unmark_favourite_movie_button);

        if (checkMovieAlreadyMarkedFavourite(movieId)) {
            addMovieButton.setEnabled(false);
            deleteMovieButton.setEnabled(true);
        } else {
            addMovieButton.setEnabled(true);
            deleteMovieButton.setEnabled(false);
        }


        addMovieButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addFavouriteMovie(movieDataSplitStr);
            }
        });

        deleteMovieButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                deleteFavouriteMovie(movieDataSplitStr);
            }
        });


    }

    /*
    Using Share Intent for sharing Movie Youtube URL
     */
    private Intent createShareMovieTrailerIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, mMovieTrailerUrl);
        //startActivity(shareIntent);
        return shareIntent;
    }

    @Override
    public void onClick(View v, int pos) {
        launchTrailerVideo(pos);
    }
}
