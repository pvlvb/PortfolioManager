package com.example.portfoliomanager.MainFragment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.portfoliomanager.Model.LocalDataSource.Coin;
import com.example.portfoliomanager.Model.Repository.LoadingStatus;
import com.example.portfoliomanager.Model.Repository.Repository;
import com.example.portfoliomanager.PortfolioApp;

import java.util.List;

public class MainFragmentViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<Coin>> coins;


    public MainFragmentViewModel(@NonNull Application application) {
        super(application);
        repository = PortfolioApp.getInstance().getRepository();
    }

    public LiveData<List<Coin>> getTOPMC() {
        return repository.refreshTOPMC();
    }

    public LiveData<List<Coin>> getGainers() {
        return repository.refreshTOPGainers();
    }

    public LiveData<List<Coin>> getLosers() {
        return repository.refreshTOPLosers();
    }

    public MutableLiveData<LoadingStatus> getStatus() {
        return repository.getStatus();
    }
}
