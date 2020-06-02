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

    @Query("Delete from news_table")
    void deleteAllNews();

    @Query("Select * from news_table ORDER BY time_posted ASC")
    LiveData<List<News>> getBlockOfNews();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPortfolioCoin(PortfolioCoin portfolioCoin);

    @Query("UPDATE portfolio SET amount = amount + :amount, original_total_price = original_total_price + :original_price," +
            " updated_total_price = updated_total_price + :updated_price where ticker = :ticker")
    void updatePortfolioCoin(String ticker, double amount, double original_price, double updated_price);

    @Query("SELECT * FROM portfolio ORDER BY updated_total_price DESC")
    LiveData<List<PortfolioCoin>> getPortfolio();

    @Query("Select sum(original_total_price) as portfolio_start_value,sum(updated_total_price) as portfolio_updated_value from portfolio")
    LiveData<PortfolioValues> getValues();

    @Query("Select ticker from portfolio where ticker = :ticker")
    List<String> checkForTickerExistence(String ticker);

    @Query("Select ticker from portfolio")
    List<String> getTickers();

    @Query("Select * from portfolio where ticker = :ticker")
    PortfolioCoin getOriginalCoinPrice(String ticker);

    @Query("UPDATE portfolio SET " +
            " updated_total_price = :updated_price where ticker = :ticker")
    void updatePortfolioCoinPrice(String ticker, double updated_price);

    @Query("UPDATE portfolio SET amount = amount - :amount, original_total_price = original_total_price - :amount*:original_price where ticker = :ticker")
    void decreasePortfolioCoin(String ticker, double amount, double original_price);

    @Query("DELETE from portfolio where ticker = :ticker")
    void deletePortfolioCoin(String ticker);




}
