package com.example.portfoliomanager.Model.RemoteDataSource;

import com.example.portfoliomanager.Model.RemoteDataSource.NewsConverter.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface CryptoPanicAPI {
    @GET("api/v1/posts/")
    Call<News> getNews(@Query("auth_token") String API_KEY, @Query("regions") String reg, @Query("page") int page);
}
