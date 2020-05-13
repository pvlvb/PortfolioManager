package com.example.portfoliomanager;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.portfoliomanager.LocalDataSource.Coin;
import com.example.portfoliomanager.MainFragmentRepository.Repository;

import java.util.List;

public class MainFragmentViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<Coin>> coins;


    public MainFragmentViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        coins = repository.getAllCoins();
    }

    public void insert(List<Coin> coins){
        repository.insert(coins);
    }

    public LiveData<List<Coin>> getCoins(){
        return coins;
    }
}
