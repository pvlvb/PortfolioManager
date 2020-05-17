package com.example.portfoliomanager.MainFragmentNetwork;

import com.example.portfoliomanager.MainFragmentNetwork.API2ObjectClasses.Result;

import java.util.List;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;


public interface CoinMarketCapAPI {
    @GET("v1/cryptocurrency/listings/latest")
    Call<Result> getData(@Query("start") Integer Num, @Query("limit") Integer lim, @Query("convert") String currency, @Query("sort") String order, @Header("X-CMC_PRO_API_KEY") String api_key);


}
