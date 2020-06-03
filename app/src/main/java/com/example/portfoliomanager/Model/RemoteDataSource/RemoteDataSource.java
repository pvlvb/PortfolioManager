package com.example.portfoliomanager.Model.RemoteDataSource;

import android.util.Log;

import com.example.portfoliomanager.Model.RemoteDataSource.BinanceConverter.BinanceCoin;
import com.example.portfoliomanager.Model.RemoteDataSource.CMC_TopMarketCap_Converter.Result;
import com.example.portfoliomanager.Model.RemoteDataSource.NewsConverter.News;
import com.example.portfoliomanager.PortfolioApp;

import java.io.IOException;

import retrofit2.Response;


public class RemoteDataSource {
    private static final String CMC_API_KEY = "41b30e2b-8e42-4ca3-a21d-abaacd519c19";
    private static final String CP_API_KEY = "394205651ba557660980b74ac07ec5d92a080849";
    private static final String BINANCE_TRADING_PAIR = "USDT";
    private CoinMarketCapAPI coinMarketCapAPI = PortfolioApp.getInstance().getCMCApi();
    private CryptoPanicAPI cryptoPanicAPI = PortfolioApp.getInstance().getCPApi();
    private BinanceAPI binanceAPI = PortfolioApp.getInstance().getBinanceAPI();

    public Result updateTOPMC() throws IOException {
        try {
            Response<Result> resp = coinMarketCapAPI.getData(1, 5, "USD", 0, "market_cap", "desc", CMC_API_KEY).execute();
            if (resp.isSuccessful()) {
                return resp.body();
            } else return null;
        } catch (Throwable throwable) {
            //Log.e("Error text", throwable.toString());
            return null;
        }
    }

    public Result updateTOPGainers() throws IOException {
        try {
            Response<Result> resp = coinMarketCapAPI.getData(1, 5, "USD", 100000000, "percent_change_24h", "desc", CMC_API_KEY).execute();
            if (resp.isSuccessful()) {
                return resp.body();
            } else return null;
        } catch (Throwable throwable) {
            return null;
        }

    }

    public Result updateTOPLosers() throws IOException {
        try {
            Response<Result> resp = PortfolioApp.getInstance().getCMCApi().getData(1, 5, "USD", 100000000, "percent_change_24h", "asc", CMC_API_KEY).execute();
            if (resp.isSuccessful()) {
                return resp.body();
            } else return null;
        } catch (Throwable throwable) {
            return null;
        }


    }

    public News updateNews(int page) throws IOException {
        try {
            Log.e("", "updateNews: " + page);
            Response<News> response = cryptoPanicAPI.getNews(CP_API_KEY, "en", page).execute();
            if (response.isSuccessful()) {
                return response.body();
            } else return null;
        } catch (Throwable throwable) {
            return null;
        }
    }

    public BinanceCoin getPrice(String ticker) throws IOException {
        try {
            Response<BinanceCoin> response = binanceAPI.getData(ticker + BINANCE_TRADING_PAIR).execute();
            if (response.isSuccessful()) {
                return response.body();
            } else return null;
        } catch (Throwable throwable) {
            return null;
        }
    }
}
