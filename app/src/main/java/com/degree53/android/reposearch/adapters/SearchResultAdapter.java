package com.degree53.android.reposearch.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.degree53.android.reposearch.R;
import com.degree53.android.reposearch.internet.Repo;

import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultHolder> {
    private Context mContext;
    private List<Repo> mData;
    private SearchResultClickListener searchResultClickListener;

    public SearchResultAdapter(Context context, List<Repo> data) {
        mContext = context;
        mData = data;
        searchResultClickListener = (SearchResultClickListener) mContext;
    }

    public interface SearchResultClickListener {
        void onSearchResultClick(int adapterPosition);
    }

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
            searchResultClickListener.onSearchResultClick(getAdapterPosition());
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
        holder.textView.setText(String.valueOf(mData.get(position)));
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    public void setData(List<Repo> data) {
        mData = data;
        notifyDataSetChanged();
    }
}
