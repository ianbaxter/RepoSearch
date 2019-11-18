package com.degree53.android.reposearch.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.degree53.android.reposearch.network.Item;

import java.util.List;

public class MainViewModel extends ViewModel {

    private MutableLiveData<List<Item>> liveData = new MutableLiveData<>();

    public MutableLiveData<List<Item>> getLiveData() {
        return liveData;
    }

    public void setLiveData(List<Item> liveData) {
        this.liveData.setValue(liveData);
    }
}
