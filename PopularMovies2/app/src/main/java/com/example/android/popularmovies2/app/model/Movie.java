/*
 * Copyright (C) 2016 The Android Popular Movies Project
 */

package com.example.android.popularmovies2.app.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/*
 *  used to hold attributes of Movie received from MovieDB
 */
public class Movie {


    @SerializedName("id")
    private int movieId;

    @SerializedName("original_title")
    private String movieTitle;

    @SerializedName("poster_path")
    private String moviePosterURI;

    @SerializedName("overview")
    private String movieOverview;

    @SerializedName("vote_average")
    private float userRating;

    @SerializedName("release_date")
    private Date movieReleaseDate;

    public Movie(String mTitle, String mPosUri, String mOverview, float rating, Date mRelDt) {
        this.movieTitle = mTitle;
        this.moviePosterURI = mPosUri;
        this.movieOverview = mOverview;
        this.userRating = rating;
        this.movieReleaseDate = mRelDt;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMoviePosterURI() {
        return moviePosterURI;
    }

    public void setMoviePosterURI(String moviePosterURI) {
        this.moviePosterURI = moviePosterURI;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public void setMovieOverview(String movieOverview) {
        this.movieOverview = movieOverview;
    }

    public float getUserRating() {
        return userRating;
    }

    public void setUserRating(float userRating) {
        this.userRating = userRating;
    }

    public Date getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public void setMovieReleaseDate(Date movieReleaseDate) {
        this.movieReleaseDate = movieReleaseDate;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    @Override
    public String toString() {
        return this.movieId + ":" + this.movieTitle + ":" + this.moviePosterURI
                + ":" + this.movieOverview + ":" + this.userRating + ":" + this.movieReleaseDate;
    }

}
