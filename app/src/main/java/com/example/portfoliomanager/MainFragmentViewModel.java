package com.example.portfoliomanager;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.portfoliomanager.MainFragmentLocal.Coin;
import com.example.portfoliomanager.MainFragmentRepository.Repository;

import java.util.List;

public class MainFragmentViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<Coin>> coins;


    public MainFragmentViewModel(@NonNull Application application) {
        super(application);
        repository = PortfolioApp.getInstance().getRepository();
    }

    public LiveData<List<Coin>> getTOPMC(){
        return repository.refreshTOPMC();
    }
    public LiveData<List<Coin>> getGainers(){
        return repository.refreshTOPGainers();
    }
    public LiveData<List<Coin>> getLosers(){
        return repository.refreshTOPLosers();
    }
}
