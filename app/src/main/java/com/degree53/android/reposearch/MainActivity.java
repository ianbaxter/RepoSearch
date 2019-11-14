package com.degree53.android.reposearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.degree53.android.reposearch.adapters.SearchResultAdapter;
import com.degree53.android.reposearch.internet.Repo;
import com.degree53.android.reposearch.internet.RetrofitInstance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class MainActivity extends AppCompatActivity
        implements SearchResultAdapter.SearchResultClickListener {

    private List<Repo> data;
    private SearchResultAdapter viewAdapter;

    private static final String SEARCH_DATA = "search_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView.LayoutManager viewManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = findViewById(R.id.recycler_view_main);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(viewManager);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        viewAdapter = new SearchResultAdapter(this, data);
        recyclerView.setAdapter(viewAdapter);

    }

    public void search(View view) {
        String query = findViewById(R.id.et_search_main).toString();
        getData(query);
        viewAdapter.setData(data);
    }

    private List<Repo> getData(String query) {
//        data = new int[20];
//        for (int i = 0; i < data.length; i++) {
//            data[i] = i;
//        }

        Retrofit retrofit = new RetrofitInstance().getRetrofitInstance();

        GitHubService service = retrofit.create(GitHubService.class);

        Call<List<Repo>> searchResults = service.listRepos(query);
        return searchResults;
    }

    @Override
    public void onSearchResultClick(int adapterPosition) {
        Log.d("TAG", "Positon clicked: " + adapterPosition);
        Intent intent = new Intent(this, DetailActivity.class);
//        Bundle bundle = new Bundle()
        intent.putExtra(SEARCH_DATA, adapterPosition);
        startActivity(intent);
    }

    public interface GitHubService {
        @GET("search/repositories/{repo}/")
        Call<List<Repo>> listRepos(@Path("repo") String repo);
    }
}
