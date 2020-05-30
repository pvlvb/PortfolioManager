package com.example.portfoliomanager.Model.Repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.portfoliomanager.Model.LocalDataSource.Coin;
import com.example.portfoliomanager.Model.LocalDataSource.LocalDataSource;
import com.example.portfoliomanager.Model.LocalDataSource.News;
import com.example.portfoliomanager.Model.RemoteDataSource.CMC_TopMarketCap_Converter.Result;
import com.example.portfoliomanager.Model.RemoteDataSource.RemoteDataSource;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private LocalDataSource localDataSource;
    private RemoteDataSource remoteDataSource;
    private ExecutorService executorService = Executors.newFixedThreadPool(4);
    private LiveData<List<Coin>> coins;
    private MutableLiveData<LoadingStatus> status = new MutableLiveData<LoadingStatus>();
    private MutableLiveData<LoadingStatus> newsStatus = new MutableLiveData<LoadingStatus>();

    public Repository(LocalDataSource localDataSource, RemoteDataSource remoteDataSource){
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    public MutableLiveData<LoadingStatus> getStatus(){
        return status;
    }

    public LiveData<List<Coin>> refreshTOPMC(){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Result tmp = remoteDataSource.updateTOPMC();
                    if(tmp != null){
                        localDataSource.putCoins(tmp, 0);
                        status.postValue(LoadingStatus.SUCCESSFUL);
                    }
                    else{
                        status.postValue(LoadingStatus.FAILED);

                    }
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
                    Result tmp = remoteDataSource.updateTOPGainers();
                    if(tmp != null){
                        localDataSource.putCoins(tmp, 5);
                        status.postValue(LoadingStatus.SUCCESSFUL);
                    }
                    else{
                        status.postValue(LoadingStatus.FAILED);
                    }
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
                    Result tmp = remoteDataSource.updateTOPLosers();
                    if(tmp != null){
                        localDataSource.putCoins(tmp,10);
                        status.postValue(LoadingStatus.SUCCESSFUL);
                    }
                    else{
                        status.postValue(LoadingStatus.FAILED);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return localDataSource.getLosers();
    }


    public LiveData<List<News>> refreshNews(int page){
        newsStatus.postValue(LoadingStatus.LOADING);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                if(page == 1) localDataSource.clearNews();
                try{
                    //Log.e("", "repository: " + page );
                    com.example.portfoliomanager.Model.RemoteDataSource.NewsConverter.News tmp = remoteDataSource.updateNews(page);
                    if(tmp!=null){
                        newsStatus.postValue(LoadingStatus.SUCCESSFUL);
                        localDataSource.putNews(tmp);
                    }
                    else{
                        newsStatus.postValue(LoadingStatus.FAILED);
                    }
                } catch (IOException e){
                    e.printStackTrace();;
                }
            }
        });
        return localDataSource.getNews();
    }

    public MutableLiveData<LoadingStatus> getNewsStatus() {
        return newsStatus;
    }

}
