package com.degree53.android.reposearch.network;

public class Item {
    private final String name;
    private final String description;
    private final int forks;
    private final int openIssues;
    private final int watchers;
    private final String html_url;

    public Item(String name, String description, int forks, int openIssues, int watchers, String html_url) {
        this.name = name;
        this.description = description;
        this.forks = forks;
        this.openIssues = openIssues;
        this.watchers = watchers;
        this.html_url = html_url;
    }

    public String getName() {
        return name;
    }

    public String getDescription() { return description; }

    public int getForks() {
        return forks;
    }

    public int getOpenIssues() {
        return openIssues;
    }

    public int getWatchers() { return watchers; }

    public String getHtml_url() { return html_url; }
}
