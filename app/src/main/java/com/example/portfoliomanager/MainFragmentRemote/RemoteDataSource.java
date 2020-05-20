package com.example.portfoliomanager.MainFragmentRemote;

import com.example.portfoliomanager.MainFragmentRemote.CMC_TopMarketCap_Converter.Result;
import com.example.portfoliomanager.PortfolioApp;

import java.io.IOException;

import retrofit2.Response;


public class RemoteDataSource {
    public static final String API_KEY = "41b30e2b-8e42-4ca3-a21d-abaacd519c19";
    public Result updateData() throws IOException {
        Response<Result> resp = PortfolioApp.getInstance().getApi().getData(1,5,"USD", "market_cap", API_KEY).execute();
        //Log.d("", "updateData: " + resp.body());
        if(resp.isSuccessful()){
            return (Result)resp.body();
        }
        else return null;
    }

}
