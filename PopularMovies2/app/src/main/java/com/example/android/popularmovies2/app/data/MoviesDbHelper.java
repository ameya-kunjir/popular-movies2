/*
 * Copyright (C) 2016 The Android Popular Movies Project
 */
package com.example.android.popularmovies2.app.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by AK00481127 on 12/19/2016.
 */

public class MoviesDbHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;

    public MoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE " + MoviesContract.MovieEntry.TABLE_NAME + " (" +

                MoviesContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                MoviesContract.MovieEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +

                MoviesContract.MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +

                MoviesContract.MovieEntry.COLUMN_POSTER + " BLOB NOT NULL, " +

                MoviesContract.MovieEntry.COLUMN_SYNOPSIS + " TEXT NOT NULL, " +

                MoviesContract.MovieEntry.COLUMN_USER_RATING + " REAL NOT NULL, " +

                MoviesContract.MovieEntry.COLUMN_RELEASE_DATE + " INTEGER NOT NULL );";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MoviesContract.MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
