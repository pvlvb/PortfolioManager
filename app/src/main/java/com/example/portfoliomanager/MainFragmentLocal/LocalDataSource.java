package com.example.portfoliomanager.MainFragmentLocal;

import androidx.lifecycle.LiveData;

import com.example.portfoliomanager.MainFragmentRemote.CMC_TopMarketCap_Converter.Datum;
import com.example.portfoliomanager.MainFragmentRemote.CMC_TopMarketCap_Converter.Result;
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
            Integer id = 0; // adding custom id, because CMC have their own ids
            for (Datum t : datumList) {

                double price = t.getQuote().getUSD().getPrice();
                double percentchange = t.getQuote().getUSD().getPercentChange24h();
                if (price >= 1.0) {
                    DecimalFormat df = new DecimalFormat("#.##");
                    price = Double.parseDouble(df.format(price));
                } else {
                    DecimalFormat df = new DecimalFormat("#.###");
                    price = Double.parseDouble(df.format(price));
                }
                DecimalFormat df = new DecimalFormat("#.##");
                percentchange = Double.parseDouble(df.format(percentchange));


                coinDAO.insertCoin(new Coin(id, "https://s2.coinmarketcap.com/static/img/coins/64x64/"+ t.getId() +".png", t.getName(), price, percentchange));
                id++;
            }
        }
        else{
            //TODO add notification on data unsuccessful loading
        }
    }
}


