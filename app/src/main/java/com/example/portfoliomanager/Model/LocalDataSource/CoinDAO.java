package com.example.portfoliomanager.Model.LocalDataSource;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CoinDAO  {
    //coin_table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCoin(Coin coin);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCoins(List<Coin> coins);

    @Update
    void updateCoin(Coin coin);

    @Delete
    void deleteCoin(Coin coin);

    @Query("SELECT * FROM coin_table ORDER BY market_cap DESC limit :num")
    LiveData<List<Coin>> getAllCoins(int num);

    @Query("SELECT * from coin_table Order by change24h DESC limit :num")
    LiveData<List<Coin>> getGainers(int num);

    @Query("SELECT * FROM coin_table ORDER BY change24h ASC limit :num")
    LiveData<List<Coin>> getLosers(int num);

    @Query("DELETE FROM coin_table")
    void deleteAll();

    //news_table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNews(News news);

    @Delete
    void deleteNews(News news);

    //TODO check if we can sort by time
    @Query("Select * from news_table ORDER BY id DESC limit :num offset :offset")
    LiveData<List<News>> getBlockOfNews(int num, int offset);

}
