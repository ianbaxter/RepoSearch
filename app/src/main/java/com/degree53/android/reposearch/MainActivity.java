package com.degree53.android.reposearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.degree53.android.reposearch.adapters.SearchResultAdapter;
import com.degree53.android.reposearch.network.GitHubService;
import com.degree53.android.reposearch.network.Item;
import com.degree53.android.reposearch.network.Repo;
import com.degree53.android.reposearch.network.RetrofitInstance;
import com.degree53.android.reposearch.viewmodels.MainViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity
        implements SearchResultAdapter.SearchResultClickListener {

    protected static final String REPO_NAME = "repo_name";
    protected static final String REPO_DESCRIPTION = "repo_description";
    protected static final String REPO_FORKS = "repo_forks";
    protected static final String REPO_OPEN_ISSUES = "repo_open_issues";
    protected static final String REPO_WATCHERS = "repo_watchers";
    protected static final String REPO_HTML_URL = "repo_html_url";

    private static final String TAG = MainActivity.class.getSimpleName();

    private MainViewModel viewModel;
    private List<Item> data;
    private SearchResultAdapter viewAdapter;
    private ProgressBar progressBar;
    private EditText editText;
    private GitHubService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViewModel();
        setupViews();
        setupRetroFit();
    }

    private void setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getLiveData().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                data = items;
                viewAdapter.setData(data);
            }
        });
    }

    private void setupViews() {
        editText = findViewById(R.id.et_search_main);
        progressBar = findViewById(R.id.indeterminate_progress_bar);
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

    private void setupRetroFit() {
        Retrofit retrofit = new RetrofitInstance().getRetrofitInstance();
        service = retrofit.create(GitHubService.class);
    }

    public void searchButton(View view) {
        // Use entered query to get a list of repositories from github
        String query = editText.getText().toString();
        if (!query.equals("")) {
            progressBar.setVisibility(View.VISIBLE);
            getData(query);
            collapseKeyboard();
        }
    }

    private void getData(String query) {
        Call<Repo> searchResults = service.listRepos(query);

        searchResults.enqueue(new Callback<Repo>() {
            @Override
            public void onResponse(Call<Repo> call, Response<Repo> response) {
                progressBar.setVisibility(View.GONE);
                if (response.body() != null) {
                    viewModel.setLiveData(response.body().getItems());
                }
            }

            @Override
            public void onFailure(Call<Repo> call, Throwable t) {
                Log.e(TAG, "Failed to get data: " + t.toString());
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this,
                        "Something went wrong, try again later", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void collapseKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onResultItemClick(int adapterPosition) {
        // Get data from selected repository
        Item repoItem = data.get(adapterPosition);
        String name = repoItem.getName();
        String description = repoItem.getDescription();
        String forks = String.valueOf(repoItem.getForks());
        String openIssues = String.valueOf(repoItem.getOpenIssues());
        String watchers = String.valueOf(repoItem.getWatchers());
        String htmlUrl = repoItem.getHtml_url();

        // Send data to detail activity
        Intent detailIntent = new Intent(this, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(REPO_NAME, name);
        bundle.putString(REPO_DESCRIPTION, description);
        bundle.putString(REPO_FORKS, forks);
        bundle.putString(REPO_OPEN_ISSUES, openIssues);
        bundle.putString(REPO_WATCHERS, watchers);
        bundle.putString(REPO_HTML_URL, htmlUrl);
        detailIntent.putExtras(bundle);

        startActivity(detailIntent);
    }
}
