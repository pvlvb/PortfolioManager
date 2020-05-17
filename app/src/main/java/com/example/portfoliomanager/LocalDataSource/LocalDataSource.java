package com.example.portfoliomanager.LocalDataSource;

import androidx.lifecycle.LiveData;

import com.example.portfoliomanager.MainFragmentNetwork.API2ObjectClasses.Datum;
import com.example.portfoliomanager.MainFragmentNetwork.API2ObjectClasses.Result;
import com.example.portfoliomanager.PortfolioApp;

import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocalDataSource {
    private CoinDB db = PortfolioApp.getInstance().getCoinDB();
    private CoinDAO coinDAO = db.coinDao();
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public LiveData<List<Coin>> getData(){ return coinDAO.getAllCoins();}

    //TODO put data methods
    public void putData(Result resp) {
        if (resp != null) {
            coinDAO.deleteAll();
            List<Datum> datumList = resp.getData();
            for (Datum t : datumList) {
                double price = t.getQuote().getUSD().getPrice();

                if (price >= 1.0) {
                    DecimalFormat df = new DecimalFormat("#.##");
                    price = Double.parseDouble(df.format(price));
                } else {
                    DecimalFormat df = new DecimalFormat("#.###");
                    price = Double.parseDouble(df.format(price));
                }
                double percentchange = t.getQuote().getUSD().getPercentChange24h();
                DecimalFormat df = new DecimalFormat("#.##");
                percentchange = Double.parseDouble(df.format(percentchange));
                coinDAO.insertCoin(new Coin(t.getId(), "", t.getName(), price, percentchange));
            }
        }
        else{
            //TODO add notification on data unsucessful loading
        }
    }
}


