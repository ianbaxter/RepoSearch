package com.degree53.android.reposearch;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    private String SEARCH_DATA = "search_data";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        int data = intent.getIntExtra(SEARCH_DATA, 0);

        TextView textView = findViewById(R.id.tv_repo_detail);
        textView.setText(String.valueOf(data));
    }
}
