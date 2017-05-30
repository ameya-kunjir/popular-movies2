/*
 * Copyright (C) 2016 The Android Popular Movies Project
 */
package com.example.android.popularmovies2.app.data;

import android.content.UriMatcher;
import android.net.Uri;
import android.test.AndroidTestCase;

/**
 * Created by AK00481127 on 12/20/2016.
 */

public class TestUriMatcher extends AndroidTestCase {

    public static final Uri TEST_MOVIES_DIR = MoviesContract.CONTENT_URI;

    public static final Uri TEST_MOVIES_ITEM = MoviesContract.CONTENT_URI.buildUpon().appendPath(String.valueOf(123)).build();
    ;

    public void testUriMatcher() {
        UriMatcher testMatcher = MoviesProvider.buildUriMatcher();

        System.out.println(TEST_MOVIES_ITEM);

        assertEquals("Error! Incorrect movie uri", testMatcher.match(TEST_MOVIES_DIR), MoviesProvider.MOVIES);

        assertEquals("Error! Incorrect movie uri item", testMatcher.match(TEST_MOVIES_ITEM), MoviesProvider.MOVIE_WITH_ID);


    }
}
