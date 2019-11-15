package com.degree53.android.reposearch.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GitHubService {
    @GET("search/repositories")
    Call<Repo> listRepos(@Query("q") String repo);
}