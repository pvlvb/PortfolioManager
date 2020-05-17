package com.example.portfoliomanager;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.example.portfoliomanager.LocalDataSource.Coin;
import com.example.portfoliomanager.MainFragmentRepository.Repository;

import java.util.List;

public class MainFragmentViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<Coin>> coins;


    public MainFragmentViewModel(@NonNull Application application) {
        super(application);
        repository = PortfolioApp.getInstance().getRepository();
    }

    public LiveData<List<Coin>> getData(){
        return repository.refreshData();
    }
}
