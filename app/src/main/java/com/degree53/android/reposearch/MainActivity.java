package com.degree53.android.reposearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.degree53.android.reposearch.adapters.SearchResultAdapter;
import com.degree53.android.reposearch.network.GitHubService;
import com.degree53.android.reposearch.network.Repo;
import com.degree53.android.reposearch.network.RetrofitInstance;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity
        implements SearchResultAdapter.SearchResultClickListener {
    private static final String REPO_NAME = "name";
    private static final String REPO_DESCRIPTION = "description";
    private static final String REPO_FORKS = "forks";
    private static final String REPO_OPEN_ISSUES = "open_issues";
    private static final String REPO_WATCHERS = "watchers";
    private static final String REPO_HTML_URL = "html_url";

    private List<Repo.Item> data;
    private SearchResultAdapter viewAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.indeterminate_progress_bar);

        setupViewModel();
    }

    private void setupViewModel() {
        viewAdapter = new SearchResultAdapter(this, data);
        RecyclerView.LayoutManager viewManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = findViewById(R.id.recycler_view_main);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(viewManager);
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(viewAdapter);
    }

    public void search(View view) {
        progressBar.setVisibility(View.VISIBLE);

        EditText editText = findViewById(R.id.et_search_main);
        String query = editText.getText().toString();
        getData(query);
        collapseKeyboard();
    }

    private void getData(String query) {
        Retrofit retrofit = new RetrofitInstance().getRetrofitInstance();
        GitHubService service = retrofit.create(GitHubService.class);
        Call<Repo> searchResults = service.listRepos(query);

        searchResults.enqueue(new Callback<Repo>() {
            @Override
            public void onResponse(Call<Repo> call, Response<Repo> response) {
                progressBar.setVisibility(View.GONE);

                if (response.body() != null) {
                    data = response.body().getItems();
                    viewAdapter.setData(data);
                }
            }

            @Override
            public void onFailure(Call<Repo> call, Throwable t) {
                Log.e("TAG", "Failed to get data: " + t.toString());
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Something went wrong, try again later", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void collapseKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onResultItemClick(int adapterPosition) {
        Repo.Item repoItem = data.get(adapterPosition);

        String name = repoItem.getName();
        String description = repoItem.getDescription();
        String forks = String.valueOf(repoItem.getForks());
        String openIssues = String.valueOf(repoItem.getOpenIssues());
        String watchers = String.valueOf(repoItem.getWatchers());
        String htmlUrl = repoItem.getHtml_url();

        Intent intent = new Intent(this, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(REPO_NAME, name);
        bundle.putString(REPO_DESCRIPTION, description);
        bundle.putString(REPO_FORKS, forks);
        bundle.putString(REPO_OPEN_ISSUES, openIssues);
        bundle.putString(REPO_WATCHERS, watchers);
        bundle.putString(REPO_HTML_URL, htmlUrl);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
