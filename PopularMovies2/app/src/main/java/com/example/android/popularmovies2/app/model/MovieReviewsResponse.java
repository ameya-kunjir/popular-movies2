/*
 * Copyright (C) 2016 The Android Popular Movies Project
 */
package com.example.android.popularmovies2.app.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by AK00481127 on 1/10/2017.
 */

public class MovieReviewsResponse {

    @SerializedName("id")
    private int id;

    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private List<MovieReview> movieReviews;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("total_results")
    private int totalResults;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<MovieReview> getMovieReviews() {
        return movieReviews;
    }

    public void setMovieReviews(List<MovieReview> movieReviews) {
        this.movieReviews = movieReviews;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
}
