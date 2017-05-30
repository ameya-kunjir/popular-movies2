/*
 * Copyright (C) 2016 The Android Popular Movies Project
 */
package com.example.android.popularmovies2.app.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by AK00481127 on 12/19/2016.
 */

public class TestDB extends AndroidTestCase {

    public static final String LOG_TAG = TestDB.class.getSimpleName();

    void deleteTheDatabase() {
        mContext.deleteDatabase(MoviesDbHelper.DATABASE_NAME);
    }

    public void setUp() {
        deleteTheDatabase();
    }

    public void testCreateDb() throws Throwable {
        final HashSet<String> tableNameHashSet = new HashSet<String>();

        tableNameHashSet.add(MoviesContract.MovieEntry.TABLE_NAME);

        mContext.deleteDatabase(MoviesDbHelper.DATABASE_NAME);

        SQLiteDatabase db = new MoviesDbHelper(this.mContext).getWritableDatabase();

        assertEquals(true, db.isOpen());

        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        assertTrue("Error: This means that the database has not been created correctly", c.moveToFirst());

    }

    public void testMoviesTable() {
        MoviesDbHelper dbHelper = new MoviesDbHelper(mContext);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

       /* ContentValues testValues = new ContentValues();

        testValues.put(MoviesContract.MovieEntry.COLUMN_TITLE  , "Chak De");
        testValues.put(MoviesContract.MovieEntry.COLUMN_SYNOPSIS , "Shart Synopsis");
        testValues.put(MoviesContract.MovieEntry.COLUMN_RELEASE_DATE , 214341);
        testValues.put(MoviesContract.MovieEntry.COLUMN_USER_RATING , 4.5);
        testValues.put(MoviesContract.MovieEntry.COLUMN_POSTER , "sample image text");*/

        ContentValues testValues = TestUtilities.createMovieTestValues();

        long movieRowId;

        movieRowId = db.insert(MoviesContract.MovieEntry.TABLE_NAME, null, testValues);

        assertTrue(movieRowId != -1);

        Cursor cursor = db.query(MoviesContract.MovieEntry.TABLE_NAME,
                null, // all columns
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        assertTrue("Error: No Records returned from location query", cursor.moveToFirst());

        Set<Map.Entry<String, Object>> valueSet = testValues.valueSet();

        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = cursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. ", idx == -1);

            String expectedValue = entry.getValue().toString();
            assertEquals("Value '" + entry.getValue().toString() +
                    "' did not match the expected value '" +
                    expectedValue + "'. ", expectedValue, cursor.getString(idx));

        }
        cursor.close();
        db.close();

    }


}
