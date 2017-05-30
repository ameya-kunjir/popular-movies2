/*
 * Copyright (C) 2016 The Android Popular Movies Project
 */
package com.example.android.popularmovies2.app.rest;

import com.example.android.popularmovies2.app.model.MovieReviewsResponse;
import com.example.android.popularmovies2.app.model.MovieTrailerResponse;
import com.example.android.popularmovies2.app.model.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by AK00481127 on 1/10/2017.
 */

public interface ApiInterface {

    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Call<MovieTrailerResponse> getMovieTrailer(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}/reviews")
    Call<MovieReviewsResponse> getMovieReviews(@Path("id") int id, @Query("api_key") String apiKey);


}
