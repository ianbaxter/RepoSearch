package com.degree53.android.reposearch;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    private static final String REPO_NAME = "name";
    private static final String REPO_DESCRIPTION = "description";
    private static final String REPO_FORKS = "forks";
    private static final String REPO_OPEN_ISSUES = "open_issues";
    private static final String REPO_WATCHERS = "watchers";
    private static final String REPO_HTML_URL = "html_url";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setupViews();
    }

    private void setupViews() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String name = bundle.getString(REPO_NAME);
            String description = bundle.getString(REPO_DESCRIPTION);
            String forks = bundle.getString(REPO_FORKS);
            String openIssues = bundle.getString(REPO_OPEN_ISSUES);
            String watchers = bundle.getString(REPO_WATCHERS);
            String htmlUrl = bundle.getString(REPO_HTML_URL);

            TextView tvDescription = findViewById(R.id.tv_description_content);
            tvDescription.setText(description);
            TextView tvForks = findViewById(R.id.tv_forks_content);
            tvForks.setText(forks);
            TextView tvOpenIssues = findViewById(R.id.tv_open_issues_content);
            tvOpenIssues.setText(openIssues);
            TextView tvWatchers = findViewById(R.id.tv_watchers_content);
            tvWatchers.setText(watchers);
            TextView tvHtmlUrl = findViewById(R.id.tv_html_url_content);
            tvHtmlUrl.setText(htmlUrl);

            getSupportActionBar().setTitle(name);
        }
    }
}
