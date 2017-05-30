/*
 * Copyright (C) 2016 The Android Popular Movies Project
 */
package com.example.android.popularmovies2.app.data;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

/**
 * Created by AK00481127 on 12/20/2016.
 */

public class TestProvider extends AndroidTestCase {

    public static final String LOG_TAG = TestProvider.class.getSimpleName();

    public void testProviderRegistry() {
        PackageManager pm = mContext.getPackageManager();

        ComponentName componentName = new ComponentName(mContext.getPackageName(), MoviesProvider.class.getName());

        try {
            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);

            assertEquals("Error: MoviesProvider registered with authority: " + providerInfo.authority +
                            " instead of authority: " + MoviesContract.CONTENT_AUTHORITY,
                    providerInfo.authority, MoviesContract.CONTENT_AUTHORITY);

        } catch (PackageManager.NameNotFoundException e) {

            assertTrue("Error: MoviesProvider not registered at " + mContext.getPackageName(), false);
        }
    }

    public void testGetType() {
        String type = mContext.getContentResolver().getType(MoviesContract.CONTENT_URI);

        assertEquals("Error: the MoviesContract CONTENT_URI should return MoviesEntry.CONTENT_TYPE",
                MoviesContract.MovieEntry.CONTENT_TYPE, type);

        int testMovieId = 1234;

        type = mContext.getContentResolver().getType(
                MoviesContract.buildFavouriteMovies(testMovieId));

        assertEquals("Error: the WeatherEntry CONTENT_URI with Movie ID should return MovieEntry.CONTENT_ITEM_TYPE",
                MoviesContract.MovieEntry.CONTENT_ITEM_TYPE, type);
    }


    public void testBasicWeatherQuery() {


        ContentValues testValues = TestUtilities.createMovieTestValues();

        /*SQLiteDatabase db = dbHelper.getWritableDatabase();



        long movieRowId;
        movieRowId = db.insert(MoviesContract.MovieEntry.TABLE_NAME, null, testValues);

        assertTrue("Unable to Insert MovieEntry into the Database", movieRowId != -1);

        db.close();*/

        Cursor movieCursor = mContext.getContentResolver().query(MoviesContract.CONTENT_URI, null, null, null, null);

        TestUtilities.validateCursor("testBasicWeatherQuery", movieCursor, testValues);

    }

    public void testGetMovieDetailsForMovieId() {
        int movieId = 120;

        ContentValues testValues = new ContentValues();
        testValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_ID, 120);
        testValues.put(MoviesContract.MovieEntry.COLUMN_TITLE, "My Title");
        testValues.put(MoviesContract.MovieEntry.COLUMN_SYNOPSIS, "Small Movie Synopsis");
        testValues.put(MoviesContract.MovieEntry.COLUMN_RELEASE_DATE, 23433);
        testValues.put(MoviesContract.MovieEntry.COLUMN_USER_RATING, 5.7f);
        testValues.put(MoviesContract.MovieEntry.COLUMN_POSTER, "Sample Movie Data");

        MoviesDbHelper dbHelper = new MoviesDbHelper(mContext);

        SQLiteDatabase db = dbHelper.getWritableDatabase();


        long movieRowId;
        movieRowId = db.insert(MoviesContract.MovieEntry.TABLE_NAME, null, testValues);

        assertTrue("Unable to Insert MovieEntry into the Database", movieRowId != -1);


        String moviesWhereClause = MoviesContract.MovieEntry.COLUMN_MOVIE_ID + " = ? ";

        Cursor movieCursor = mContext.getContentResolver().query(MoviesContract.CONTENT_URI.buildUpon().appendPath(String.valueOf(120)).build()
                , null
                , moviesWhereClause
                , new String[]{Integer.toString(movieId)}
                , null);

        TestUtilities.validateCursor("testBasicQuery", movieCursor, testValues);
        db.close();

    }


}
