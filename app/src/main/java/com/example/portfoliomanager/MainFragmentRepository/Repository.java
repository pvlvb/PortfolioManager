package com.example.portfoliomanager.MainFragmentRepository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.portfoliomanager.LocalDataSource.Coin;
import com.example.portfoliomanager.LocalDataSource.CoinDAO;
import com.example.portfoliomanager.LocalDataSource.CoinDB;
import com.example.portfoliomanager.LocalDataSource.LocalDataSource;
import com.example.portfoliomanager.MainFragmentNetwork.RemoteDataSource;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private LocalDataSource localDataSource;
    private RemoteDataSource remoteDataSource;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private LiveData<List<Coin>> coins;

    public Repository(LocalDataSource localDataSource, RemoteDataSource remoteDataSource){
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    public LiveData<List<Coin>> refreshData(){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    localDataSource.putData(remoteDataSource.updateData());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return localDataSource.getData();
    }
}
