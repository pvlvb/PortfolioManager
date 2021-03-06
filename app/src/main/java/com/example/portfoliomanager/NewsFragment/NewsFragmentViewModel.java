package com.example.portfoliomanager.NewsFragment;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.portfoliomanager.Model.LocalDataSource.News;
import com.example.portfoliomanager.Model.Repository.LoadingStatus;
import com.example.portfoliomanager.Model.Repository.Repository;
import com.example.portfoliomanager.PortfolioApp;

import java.util.List;

public class NewsFragmentViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<News>> coins;

    public NewsFragmentViewModel(@NonNull Application application) {
        super(application);
        repository = PortfolioApp.getInstance().getRepository();
    }

    public void updateNews(int page_number) {
        repository.refreshNews(page_number);
    }

    public LiveData<List<News>> getNews() {
        return repository.getNews();
    }

    public MutableLiveData<LoadingStatus> getStatus() {
        return repository.getNewsStatus();
    }

}
