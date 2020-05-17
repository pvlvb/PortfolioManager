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
    // database creation
    public static CoinDB getInstance(Context context){
        if(instance == null){
            synchronized (CoinDB.class){
                if(instance==null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            CoinDB.class, "coin_DB").build();
                }
            }

        }
        return instance;
    }
}
