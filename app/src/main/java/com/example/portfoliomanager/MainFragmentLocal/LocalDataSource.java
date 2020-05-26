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
    public static final int num = 5;

    public LiveData<List<Coin>> getData(){ return coinDAO.getAllCoins(num);}
    public LiveData<List<Coin>> getGainers() {
        return  coinDAO.getGainers(num);
    }
    public LiveData<List<Coin>> getLosers(){ return coinDAO.getLosers(num); }

    //TODO put data methods
    public void putData(Result resp,boolean clearData) {
        if (resp != null) {
            if(clearData) coinDAO.deleteAll();
            List<Datum> datumList = resp.getData();
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

                coinDAO.insertCoin(new Coin(t.getId(), "https://s2.coinmarketcap.com/static/img/coins/64x64/"+ t.getId() +".png", t.getSymbol().toUpperCase(), (t.getQuote().getUSD().getMarketCap()!=null)?t.getQuote().getUSD().getMarketCap():0, price, percentchange));
            }
        }
        else{
            //TODO add notification on data unsuccessful loading
        }
    }


}


