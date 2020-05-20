package com.example.portfoliomanager.MainFragmentRemote;

import com.example.portfoliomanager.MainFragmentRemote.CMC_TopMarketCap_Converter.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;


public interface CoinMarketCapAPI {
    @GET("v1/cryptocurrency/listings/latest")
    Call<Result> getData(@Query("start") Integer Num, @Query("limit") Integer lim, @Query("convert") String currency, @Query("sort") String order, @Header("X-CMC_PRO_API_KEY") String api_key);
}
