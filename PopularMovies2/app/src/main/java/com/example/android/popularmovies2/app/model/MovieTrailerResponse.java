/*
 * Copyright (C) 2016 The Android Popular Movies Project
 */
package com.example.android.popularmovies2.app.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by AK00481127 on 1/10/2017.
 */

public class MovieTrailerResponse {

    @SerializedName("id")
    private int id;

    @SerializedName("results")
    private List<MovieTrailer> movieTrailers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<MovieTrailer> getMovieTrailers() {
        return movieTrailers;
    }

    public void setMovieTrailers(List<MovieTrailer> movieTrailers) {
        this.movieTrailers = movieTrailers;
    }
}
