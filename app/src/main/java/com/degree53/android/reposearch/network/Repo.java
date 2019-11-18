package com.degree53.android.reposearch.network;

import java.util.List;

public class Repo {
    private final List<Item> items;

    public Repo(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }
}

