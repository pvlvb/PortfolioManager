package com.example.portfoliomanager;

import android.app.Application;

import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.example.portfoliomanager.LocalDataSource.CoinDB;
import com.example.portfoliomanager.LocalDataSource.LocalDataSource;
import com.example.portfoliomanager.MainFragmentNetwork.CoinMarketCapAPI;
import com.example.portfoliomanager.MainFragmentNetwork.RemoteDataSource;
import com.example.portfoliomanager.MainFragmentRepository.Repository;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PortfolioApp extends Application {
    private ViewModelProvider.Factory factory;
    private Repository repository;
    private static PortfolioApp instance;
    private CoinDB coinDB;
    Retrofit retrofit;
    private static CoinMarketCapAPI coinMarketCapAPI;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        factory = new MainFragmentViewModelProvider();
        coinDB = CoinDB.getInstance(instance);
        retrofit = new Retrofit.Builder().baseUrl("https://pro-api.coinmarketcap.com/").addConverterFactory(GsonConverterFactory.create()).build();
        coinMarketCapAPI = retrofit.create(CoinMarketCapAPI.class);
        repository = new Repository(new LocalDataSource(), new RemoteDataSource());

    }
    public Repository getRepository(){
        return repository;
    }
    public static PortfolioApp getInstance() {
        return instance;
    }
    public CoinDB getCoinDB(){
        return coinDB;
    }
    public ViewModelProvider.Factory getViewModelFactory(){
        return factory;
    }
    public CoinMarketCapAPI getApi(){return coinMarketCapAPI;}

}
