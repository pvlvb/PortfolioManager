package com.example.portfoliomanager.Model.RemoteDataSource;

import com.example.portfoliomanager.Model.RemoteDataSource.CMC_TopMarketCap_Converter.Result;
import com.example.portfoliomanager.Model.RemoteDataSource.NewsConverter.News;
import com.example.portfoliomanager.PortfolioApp;

import java.io.IOException;

import retrofit2.Response;


public class RemoteDataSource {
    private static final String CMC_API_KEY = "41b30e2b-8e42-4ca3-a21d-abaacd519c19";
    public static final String CP_API_KEY = "394205651ba557660980b74ac07ec5d92a080849";
    private CoinMarketCapAPI retrofit = PortfolioApp.getInstance().getCMCApi();
    public Result updateTOPMC() throws IOException {
        Response<Result> resp = PortfolioApp.getInstance().getCMCApi().getData(1,5,"USD", 0,"market_cap", "desc", CMC_API_KEY).execute();
        //Log.d("", "updateData: " + resp.body());
        if(resp.isSuccessful()){
            return (Result)resp.body();
        }
        else return null;
    }
    public Result updateTOPGainers() throws IOException {
        Response<Result> resp = PortfolioApp.getInstance().getCMCApi().getData(1,5,"USD", 100000000, "percent_change_24h", "desc", CMC_API_KEY).execute();
        //Log.d("", "updateData: " + resp.body());
        if(resp.isSuccessful()){
            return (Result)resp.body();
        }
        else return null;
    }

    public Result updateTOPLosers() throws IOException {
        Response<Result> resp = PortfolioApp.getInstance().getCMCApi().getData(1,5,"USD", 100000000, "percent_change_24h", "asc", CMC_API_KEY).execute();
        //Log.d("", "updateData: " + resp.body());
        if(resp.isSuccessful()){
            return (Result)resp.body();
        }
        else return null;
    }

    public News updateNews(int page) throws IOException{
        Response<News> response = PortfolioApp.getInstance().getCPApi().getNews(CP_API_KEY,"en", page).execute();
        if(response.isSuccessful()){
            return (News)response.body();
        }
        else return null;
    }
}
