package com.degree53.android.reposearch.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.degree53.android.reposearch.R;
import com.degree53.android.reposearch.network.Item;
import com.degree53.android.reposearch.network.Repo;

import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultHolder> {
    private List<Item> mData;
    private SearchResultClickListener searchResultClickListener;

    public SearchResultAdapter(Context context, List<Item> data) {
        mData = data;
        searchResultClickListener = (SearchResultClickListener) context;
    }

    // Click listener interface
    public interface SearchResultClickListener {
        void onResultItemClick(int adapterPosition);
    }

    // ViewHolder with click listener
    public class SearchResultHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private TextView textView;

        private SearchResultHolder(TextView view) {
            super(view);
            textView = view;
            textView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            searchResultClickListener.onResultItemClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public SearchResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView view = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search_result, parent, false);
        return new SearchResultHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultHolder holder, int position) {
        if (mData != null) {
            // Get the name of each repository item
            Item repoItem = mData.get(position);
            holder.textView.setText(repoItem.getName());
        }
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    public void setData(List<Item> data) {
        mData = data;
        notifyDataSetChanged();
    }
}
