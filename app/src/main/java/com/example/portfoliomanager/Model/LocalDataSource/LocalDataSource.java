package com.example.portfoliomanager.Model.LocalDataSource;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.portfoliomanager.Model.RemoteDataSource.CMC_TopMarketCap_Converter.Datum;
import com.example.portfoliomanager.Model.RemoteDataSource.CMC_TopMarketCap_Converter.Result;
import com.example.portfoliomanager.PortfolioApp;

import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocalDataSource {
    private CoinDB db = PortfolioApp.getInstance().getCoinDB();
    private CoinDAO coinDAO = db.coinDao();
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static final int num = 5;

    public LiveData<List<Coin>> getData(){ return coinDAO.getAllCoins(num);}
    public LiveData<List<Coin>> getGainers() {
        return  coinDAO.getGainers(num);
    }
    public LiveData<List<Coin>> getLosers(){ return coinDAO.getLosers(num); }
    public LiveData<List<News>> getNews(int limit, int offset) {
        return coinDAO.getBlockOfNews(limit,offset);
    }

    public void putCoins(Result resp, int startId) {
            List<Datum> datumList = resp.getData();
            int tmp = startId;
            for (Datum t : datumList) {
                //data processing
                double price = t.getQuote().getUSD().getPrice();
                double percentchange = t.getQuote().getUSD().getPercentChange24h();
                DecimalFormat df = new DecimalFormat("#.##");
                if (price >= 1.0) {
                    df = new DecimalFormat("#.##");
                    price = Double.parseDouble(df.format(price));
                } else {
                    df = new DecimalFormat("#.###");
                    price = Double.parseDouble(df.format(price));
                }
                df = new DecimalFormat("#.##");
                percentchange = Double.parseDouble(df.format(percentchange));
                Log.e("HUI", "putData:" + tmp + "   " + t.getSymbol().toUpperCase());
                coinDAO.insertCoin(new Coin(tmp, "https://s2.coinmarketcap.com/static/img/coins/64x64/"+ t.getId() +".png", t.getSymbol().toUpperCase(), (t.getQuote().getUSD().getMarketCap()!=null)?t.getQuote().getUSD().getMarketCap():0, price, percentchange));
                tmp++;

            }
    }

    public void putNews(com.example.portfoliomanager.Model.RemoteDataSource.NewsConverter.News response){
        List<com.example.portfoliomanager.Model.RemoteDataSource.NewsConverter.Result> results = response.getResults();
        for(com.example.portfoliomanager.Model.RemoteDataSource.NewsConverter.Result t: results){

            coinDAO.insertNews(new News(t.getId(),t.getPublishedAt(),t.getTitle(),(t.getCurrencies()!=null)?t.getCurrencies().get(0).getCode():"",t.getUrl(), t.getVotes().getPositive(),t.getVotes().getDisliked()));
        }

    }



}


