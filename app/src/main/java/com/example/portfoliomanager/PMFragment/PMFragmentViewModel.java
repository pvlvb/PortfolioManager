package com.example.portfoliomanager.PMFragment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.portfoliomanager.Model.LocalDataSource.PortfolioCoin;
import com.example.portfoliomanager.Model.LocalDataSource.PortfolioValues;
import com.example.portfoliomanager.Model.Repository.LoadingStatus;
import com.example.portfoliomanager.Model.Repository.Repository;
import com.example.portfoliomanager.PortfolioApp;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PMFragmentViewModel extends AndroidViewModel {
    private Repository repository;

    public PMFragmentViewModel(@NonNull Application application) {
        super(application);
        repository = PortfolioApp.getInstance().getRepository();
    }

    public void addPortfolioCoin(String ticker, double amount, double price_per_coin){
        repository.addPortfolioCoin(ticker,amount,price_per_coin);
    }

    public LiveData<List<PortfolioCoin>> getPortoflio(){
        return repository.getPortfolio();
    }

    public LiveData<PortfolioValues> getPortfolioValues(){ return repository.getPortfolioValues(); }

    public MutableLiveData<LoadingStatus> getAddCoinStatus(){
        return repository.getAddCoinStatus();
    }

}
