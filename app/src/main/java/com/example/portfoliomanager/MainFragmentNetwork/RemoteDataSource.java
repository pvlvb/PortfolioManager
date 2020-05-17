package com.example.portfoliomanager.MainFragmentNetwork;

import android.util.Log;

import com.example.portfoliomanager.MainFragmentNetwork.CoinMarketCapAPI;
import com.example.portfoliomanager.MainFragmentNetwork.API2ObjectClasses.Result;
import com.example.portfoliomanager.MainFragmentNetwork.API2ObjectClasses.Status;
import com.example.portfoliomanager.PortfolioApp;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


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
