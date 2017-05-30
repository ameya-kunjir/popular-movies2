/*
 * Copyright (C) 2016 The Android Popular Movies Project
 */
package com.example.android.popularmovies2.app.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by AK00481127 on 12/20/2016.
 */

public class MoviesProvider extends ContentProvider {

    static final int MOVIES = 100;
    static final int MOVIE_WITH_ID = 101;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private static final SQLiteQueryBuilder sPopularMoviesQueryBuilder;
    //Movies.id = ?
    private static final String sMovieSelection =
            MoviesContract.MovieEntry.TABLE_NAME +
                    "." + MoviesContract.MovieEntry._ID + " = ? ";

    static {
        sPopularMoviesQueryBuilder = new SQLiteQueryBuilder();

        sPopularMoviesQueryBuilder.setTables(MoviesContract.MovieEntry.TABLE_NAME);

    }

    private MoviesDbHelper mMoviesDbHelper;

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        final String authority = MoviesContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MoviesContract.PATH_MOVIE, MOVIES);

        matcher.addURI(authority, MoviesContract.PATH_MOVIE + "/#", MOVIE_WITH_ID);

        return matcher;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case MOVIES:
                return MoviesContract.MovieEntry.CONTENT_TYPE;

            case MOVIE_WITH_ID:
                return MoviesContract.MovieEntry.CONTENT_ITEM_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;

        switch (sUriMatcher.match(uri)) {
            //"/movies"
            case MOVIES: {
                retCursor = mMoviesDbHelper.getWritableDatabase().query(MoviesContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            //"/movies/#"
            case MOVIE_WITH_ID: {
                retCursor = getMovieWithId(uri, selection, selectionArgs);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);

        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }

    @Override
    public boolean onCreate() {
        mMoviesDbHelper = new MoviesDbHelper(getContext());
        return true;
    }


    public Cursor getMovieWithId(Uri uri, String selection, String[] selectionArgs) {
        int movieId = MoviesContract.MovieEntry.getMovieIdFromUri(uri);
        String[] s = null;
        return sPopularMoviesQueryBuilder.query(mMoviesDbHelper.getReadableDatabase(),
                null,
                sMovieSelection,
                new String[]{Integer.toString(movieId)},
                null,
                null,
                null);
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        SQLiteDatabase db = mMoviesDbHelper.getWritableDatabase();
        Uri returnUri = null;
        long _id = db.insert(MoviesContract.MovieEntry.TABLE_NAME, null, contentValues);

        if (_id > 0)
            returnUri = MoviesContract.MovieEntry.buildMovieUri(_id);
        else
            throw new android.database.SQLException("Failed to insert row into " + uri);

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }


    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mMoviesDbHelper.getWritableDatabase();

        int rowsUpdated;

        rowsUpdated = db.update(MoviesContract.MovieEntry.TABLE_NAME, contentValues, selection, selectionArgs);

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mMoviesDbHelper.getWritableDatabase();

        int rowsDeleted;

        rowsDeleted = db.delete(
                MoviesContract.MovieEntry.TABLE_NAME, selection, selectionArgs);

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mMoviesDbHelper.getWritableDatabase();

        db.beginTransaction();
        int returnCount = 0;

        try {
            for (ContentValues value : values) {

                long _id = db.insert(MoviesContract.MovieEntry.TABLE_NAME, null, value);
                if (_id != -1) {
                    returnCount++;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnCount;
    }
}
