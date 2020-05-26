package com.example.portfoliomanager.Model.RemoteDataSource;

import com.example.portfoliomanager.Model.RemoteDataSource.CMC_TopMarketCap_Converter.Result;
import com.example.portfoliomanager.PortfolioApp;

import java.io.IOException;

import retrofit2.Response;


public class RemoteDataSource {
    public static final String API_KEY = "41b30e2b-8e42-4ca3-a21d-abaacd519c19";
    public Result updateTOPMC() throws IOException {
        Response<Result> resp = PortfolioApp.getInstance().getApi().getData(1,5,"USD", 0,"market_cap", "desc", API_KEY).execute();
        //Log.d("", "updateData: " + resp.body());
        if(resp.isSuccessful()){
            return (Result)resp.body();
        }
        else return null;
    }
    public Result updateTOPGainers() throws IOException {
        Response<Result> resp = PortfolioApp.getInstance().getApi().getData(1,5,"USD", 100000000, "percent_change_24h", "desc", API_KEY).execute();
        //Log.d("", "updateData: " + resp.body());
        if(resp.isSuccessful()){
            return (Result)resp.body();
        }
        else return null;
    }

    public Result updateTOPLosers() throws IOException {
        Response<Result> resp = PortfolioApp.getInstance().getApi().getData(1,5,"USD", 100000000, "percent_change_24h", "asc", API_KEY).execute();
        //Log.d("", "updateData: " + resp.body());
        if(resp.isSuccessful()){
            return (Result)resp.body();
        }
        else return null;
    }
}
