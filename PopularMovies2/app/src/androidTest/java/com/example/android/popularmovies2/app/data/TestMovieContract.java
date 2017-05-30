/*
 * Copyright (C) 2016 The Android Popular Movies Project
 */
package com.example.android.popularmovies2.app.data;

import android.net.Uri;
import android.test.AndroidTestCase;

/**
 * Created by AK00481127 on 12/20/2016.
 */

public class TestMovieContract extends AndroidTestCase {

    private static final int TEST_FAVOURITE_MOVIES = 123;


    public void testBuildFavouriteMovies() {
        Uri favouriteMoviesUri = MoviesContract.buildFavouriteMovies(TEST_FAVOURITE_MOVIES);

        assertNotNull("Error! Null URI returned", favouriteMoviesUri);

        assertEquals("Error! Favourite Movies not appended  to Uri", String.valueOf(TEST_FAVOURITE_MOVIES), favouriteMoviesUri.getLastPathSegment());

        assertEquals("Error! Favourite Movies does not match expected result", favouriteMoviesUri.toString(),
                "content://com.example.android.popularmovies2.app/movies/123");
    }


}
