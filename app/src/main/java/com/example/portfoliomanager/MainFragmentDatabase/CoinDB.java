package com.example.portfoliomanager.MainFragmentDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = Coin.class, version = 1)
public abstract class CoinDB extends RoomDatabase {

    private static CoinDB instance;

    public abstract CoinDAO coinDao();

    public static synchronized CoinDB getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    CoinDB.class, "coin_DB").fallbackToDestructiveMigration().build();
        }
        return instance;
    }

}
