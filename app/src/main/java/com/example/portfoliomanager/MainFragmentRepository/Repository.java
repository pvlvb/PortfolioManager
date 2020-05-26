package com.example.portfoliomanager.MainFragmentRepository;

import androidx.lifecycle.LiveData;

import com.example.portfoliomanager.MainFragmentLocal.Coin;
import com.example.portfoliomanager.MainFragmentLocal.LocalDataSource;
import com.example.portfoliomanager.MainFragmentRemote.RemoteDataSource;

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

    public LiveData<List<Coin>> refreshTOPMC(){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    localDataSource.putData(remoteDataSource.updateTOPMC(), true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return localDataSource.getData();
    }
    public LiveData<List<Coin>> refreshTOPGainers(){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    localDataSource.putData(remoteDataSource.updateTOPGainers(), false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return localDataSource.getGainers();
    }
    public LiveData<List<Coin>> refreshTOPLosers(){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    localDataSource.putData(remoteDataSource.updateTOPLosers(),false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return localDataSource.getLosers();
    }
}
