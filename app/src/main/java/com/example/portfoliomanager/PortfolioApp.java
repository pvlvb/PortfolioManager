package com.example.portfoliomanager;

import android.app.Application;

import androidx.lifecycle.ViewModelProvider;

import com.example.portfoliomanager.Model.LocalDataSource.CoinDB;
import com.example.portfoliomanager.Model.LocalDataSource.LocalDataSource;
import com.example.portfoliomanager.Model.RemoteDataSource.BinanceAPI;
import com.example.portfoliomanager.Model.RemoteDataSource.CoinMarketCapAPI;
import com.example.portfoliomanager.Model.RemoteDataSource.CryptoPanicAPI;
import com.example.portfoliomanager.Model.RemoteDataSource.RemoteDataSource;
import com.example.portfoliomanager.Model.Repository.Repository;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PortfolioApp extends Application {
    private Repository repository;
    private static PortfolioApp instance;
    private CoinDB coinDB;
    Retrofit CMCretrofit;
    Retrofit CPretrofit;
    Retrofit BNretorift;
    private static CoinMarketCapAPI coinMarketCapAPI;
    private static CryptoPanicAPI cryptoPanicAPI;
    private static BinanceAPI binanceAPI;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        coinDB = CoinDB.getInstance(instance);
        CMCretrofit = new Retrofit.Builder().baseUrl("https://pro-api.coinmarketcap.com/").addConverterFactory(GsonConverterFactory.create()).build();
        coinMarketCapAPI = CMCretrofit.create(CoinMarketCapAPI.class);
        CPretrofit = new Retrofit.Builder().baseUrl("https://cryptopanic.com/").addConverterFactory(GsonConverterFactory.create()).build();
        cryptoPanicAPI = CPretrofit.create(CryptoPanicAPI.class);
        BNretorift = new Retrofit.Builder().baseUrl("https://api.binance.com/").addConverterFactory(GsonConverterFactory.create()).build();
        binanceAPI = BNretorift.create(BinanceAPI.class);
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
    public CoinMarketCapAPI getCMCApi(){return coinMarketCapAPI;}
    public CryptoPanicAPI getCPApi(){return cryptoPanicAPI;}
    public BinanceAPI getBinanceAPI(){ return binanceAPI; }

}
