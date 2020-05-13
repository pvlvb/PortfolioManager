package com.example.portfoliomanager.MainFragmentRepository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.portfoliomanager.LocalDataSource.Coin;
import com.example.portfoliomanager.LocalDataSource.CoinDAO;
import com.example.portfoliomanager.LocalDataSource.CoinDB;

import java.util.List;

public class Repository {
    private CoinDAO coinDAO;
    private LiveData<List<Coin>> coins;

    public Repository(Application application){
        CoinDB db = CoinDB.getInstance(application);
        coinDAO = db.coinDao();
        coins = coinDAO.getAllCoins();
    }

    public void insert(List<Coin> coins){
        new InsertCoinsAsyncTask(coinDAO).execute(coins);
    }

//    public void update(Coin coin){
//
//    }
//
//    public void delete(Coin coin){
//
//    }
//
//    public void deleteAllCoins(){
//
//    }

    public LiveData<List<Coin>> getAllCoins(){
        return coins;
    }

    private static class InsertCoinsAsyncTask extends AsyncTask<List<Coin>, Void, Void>{
        private CoinDAO coinDAO;

        private InsertCoinsAsyncTask(CoinDAO coinDAO){
            this.coinDAO = coinDAO;
        }
        @Override
        protected Void doInBackground(List<Coin>... lists) {
            coinDAO.insertCoins(lists[0]);
            return null;
        }
    }
}
