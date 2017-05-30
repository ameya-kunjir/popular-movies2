/*
 * Copyright (C) 2016 The Android Popular Movies Project
 */
package com.example.android.popularmovies2.app.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by AK00481127 on 1/10/2017.
 */

public class MovieTrailer {

    @SerializedName("page")
    private int id;

    @SerializedName("key")
    private String key;

    @SerializedName("site")
    private String site;

    @SerializedName("type")
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
