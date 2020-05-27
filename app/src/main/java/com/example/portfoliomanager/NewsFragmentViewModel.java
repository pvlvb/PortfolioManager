package com.example.portfoliomanager;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.portfoliomanager.Model.LocalDataSource.News;
import com.example.portfoliomanager.Model.Repository.Repository;

import java.util.List;

public class NewsFragmentViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<News>> coins;

    public NewsFragmentViewModel(@NonNull Application application) {
        super(application);
        repository = PortfolioApp.getInstance().getRepository();
    }

    public LiveData<List<News>> getNews(){
        return repository.refreshNews();
    }
    //TODO add news getting methods
}
