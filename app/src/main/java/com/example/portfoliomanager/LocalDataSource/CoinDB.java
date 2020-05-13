package com.example.portfoliomanager.LocalDataSource;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Coin.class, version = 1)
public abstract class CoinDB extends RoomDatabase {

    private static CoinDB instance;

    public abstract CoinDAO coinDao();

    public static synchronized CoinDB getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    CoinDB.class, "coin_DB").fallbackToDestructiveMigration().addCallback(roomCallback).build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new FillDB(instance).execute();
        }

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new FillDB(instance).execute();
        }
    };

    private static class FillDB extends AsyncTask<Void,Void,Void>{
        private CoinDAO coinDAO;
        private FillDB(CoinDB db){
            coinDAO = db.coinDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            coinDAO.insertCoin(new Coin(1,"IMG URL", "BTC",10000.00, 1.00,10));
            coinDAO.insertCoin(new Coin(2,"IMG URL", "ETH",180.32, 1.00,-11));
            coinDAO.insertCoin(new Coin(3,"IMG URL", "LTC",80.01, 1.00,10));
            coinDAO.insertCoin(new Coin(4,"IMG URL", "XRP",0.22, 1.00,10));
            coinDAO.insertCoin(new Coin(5,"IMG URL", "BNB",8.99, 1.00,10));
            return null;
        }
    }
}
