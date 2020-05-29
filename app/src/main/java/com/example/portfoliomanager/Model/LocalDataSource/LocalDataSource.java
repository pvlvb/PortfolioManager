package com.example.portfoliomanager.Model.LocalDataSource;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.portfoliomanager.Model.RemoteDataSource.CMC_TopMarketCap_Converter.Datum;
import com.example.portfoliomanager.Model.RemoteDataSource.CMC_TopMarketCap_Converter.Result;
import com.example.portfoliomanager.PortfolioApp;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
    public LiveData<List<News>> getNews() {
        return coinDAO.getBlockOfNews();
    }
    public void clearNews(){
        coinDAO.deleteAllNews();
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
                coinDAO.insertCoin(new Coin(tmp, "https://s2.coinmarketcap.com/static/img/coins/64x64/"+ t.getId() +".png", t.getSymbol().toUpperCase(), (t.getQuote().getUSD().getMarketCap()!=null)?t.getQuote().getUSD().getMarketCap():0, price, percentchange));
                tmp++;

            }
    }

    public void putNews(com.example.portfoliomanager.Model.RemoteDataSource.NewsConverter.News response){
        List<com.example.portfoliomanager.Model.RemoteDataSource.NewsConverter.Result> results = response.getResults();
        LocalDateTime now = LocalDateTime.now();
        for(com.example.portfoliomanager.Model.RemoteDataSource.NewsConverter.Result t: results){
            String posted = t.getPublishedAt();
            posted = posted.substring(0,posted.length()-1);
            LocalDateTime dateTime = LocalDateTime.parse(posted);
            long minutes = Math.abs(ChronoUnit.MINUTES.between(dateTime,now));
            coinDAO.insertNews(new News(t.getId(),minutes,t.getTitle(),(t.getCurrencies()!=null)?t.getCurrencies().get(0).getCode():"", t.getUrl(),
                    t.getVotes().getPositive()+t.getVotes().getLiked() + t.getVotes().getImportant(),
                    t.getVotes().getDisliked()+t.getVotes().getToxic()+t.getVotes().getNegative()));
        }
    }



}


