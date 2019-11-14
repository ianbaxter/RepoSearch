package com.degree53.android.reposearch.internet;

import com.google.gson.annotations.SerializedName;

public class Repo {

    @SerializedName("name")
    private String title;

    public Repo(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
