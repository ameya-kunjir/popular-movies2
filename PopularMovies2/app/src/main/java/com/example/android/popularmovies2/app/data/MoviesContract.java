/*
 * Copyright (C) 2016 The Android Popular Movies Project
 */
package com.example.android.popularmovies2.app.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by AK00481127 on 12/19/2016.
 */

public class MoviesContract {

    public static final String CONTENT_AUTHORITY = "com.example.android.popularmovies2.app";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIE = "movies";

    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

    public static Uri buildFavouriteMovies(int movieId) {
        return CONTENT_URI.buildUpon().appendPath(String.valueOf(movieId)).build();
    }

    public static final class MovieEntry implements BaseColumns {

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        public static final String TABLE_NAME = "movies";

        public static final String COLUMN_MOVIE_ID = "movie_id";

        public static final String COLUMN_TITLE = "title";

        public static final String COLUMN_POSTER = "poster";

        public static final String COLUMN_SYNOPSIS = "synopsis";

        public static final String COLUMN_USER_RATING = "user_rating";

        public static final String COLUMN_RELEASE_DATE = "release_date";


        public static int getMovieIdFromUri(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }


        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }


}
