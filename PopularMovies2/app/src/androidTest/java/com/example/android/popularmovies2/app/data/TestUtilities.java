/*
 * Copyright (C) 2016 The Android Popular Movies Project
 */
package com.example.android.popularmovies2.app.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.test.AndroidTestCase;

import java.util.Map;
import java.util.Set;

/**
 * Created by AK00481127 on 12/19/2016.
 */

public class TestUtilities extends AndroidTestCase {

    static final String TEST_TITLE = "Chak De";
    static final String TEST_SYNOPSIS = "Short Synopsis";
    static final int TEST_DATE = 214341;
    static final float TEST_RATING = 4.7F;
    static final String TEST_POSTER = "Sample Image Text";
    static final int TEST_MOVIE_ID = 123;

    static ContentValues createMovieTestValues() {
        ContentValues testValues = new ContentValues();
        testValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_ID, TEST_MOVIE_ID);
        testValues.put(MoviesContract.MovieEntry.COLUMN_TITLE, TEST_TITLE);
        testValues.put(MoviesContract.MovieEntry.COLUMN_SYNOPSIS, TEST_SYNOPSIS);
        testValues.put(MoviesContract.MovieEntry.COLUMN_RELEASE_DATE, TEST_DATE);
        testValues.put(MoviesContract.MovieEntry.COLUMN_USER_RATING, TEST_RATING);
        testValues.put(MoviesContract.MovieEntry.COLUMN_POSTER, TEST_POSTER);

        return testValues;
    }

    static void validateCursor(String error, Cursor valueCursor, ContentValues expectedValues) {
        assertTrue("Empty cursor returned. " + error, valueCursor.moveToFirst());
        validateCurrentRecord(error, valueCursor, expectedValues);
        valueCursor.close();
    }

    static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals("Value '" + entry.getValue().toString() +
                    "' did not match the expected value '" +
                    expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
        }
    }


}
