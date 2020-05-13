package com.example.portfoliomanager.LocalDataSource;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "coin_table")
public class Coin {
    @PrimaryKey(autoGenerate = false)
    private int id;

    private String image;

    private String ticker;

    private double price;

    private double volume;

    private double change24h;

    public Coin(int id, String image, String ticker, double price, double volume, double change24h) {
        this.id = id;
        this.image = image;
        this.ticker = ticker;
        this.price = price;
        this.volume = volume;
        this.change24h = change24h;
    }



    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getTicker() {
        return ticker;
    }

    public double getPrice() {
        return price;
    }

    public double getVolume() {
        return volume;
    }

    public double getChange24h() {
        return change24h;
    }
}
