package com.example.portfoliomanager.Model.Repository;

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
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private LiveData<List<Coin>> coins;
    private MutableLiveData<LoadingStatus> status = new MutableLiveData<LoadingStatus>();

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
                        //TODO notification
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
                        //TODO unsuccessful loading notification
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return localDataSource.getLosers();
    }


    public LiveData<List<News>> refreshNews(){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    com.example.portfoliomanager.Model.RemoteDataSource.NewsConverter.News tmp = remoteDataSource.updateNews(1);
                    if(tmp!=null){
                        localDataSource.putNews(tmp);
                        //TODO loading successful
                    }
                    else{
                        //// TODO: 27.05.2020 loading unsuccessful
                    }
                } catch (IOException e){
                    e.printStackTrace();;
                }
            }
        });
        return localDataSource.getNews(20,0);
    }




    //// TODO: 27.05.2020 check internet connection before loading
}
