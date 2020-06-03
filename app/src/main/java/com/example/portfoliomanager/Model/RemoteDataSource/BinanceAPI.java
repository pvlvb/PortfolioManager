package com.example.portfoliomanager.Model.RemoteDataSource;

import com.example.portfoliomanager.Model.RemoteDataSource.BinanceConverter.BinanceCoin;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface BinanceAPI {
    @GET("api/v3/ticker/price")
    Call<BinanceCoin> getData(@Query("symbol") String trading_pair);
}

