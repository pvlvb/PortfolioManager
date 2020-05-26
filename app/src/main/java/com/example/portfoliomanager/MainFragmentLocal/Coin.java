package com.example.portfoliomanager.MainFragmentLocal;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "coin_table")
public class Coin {
    @PrimaryKey(autoGenerate = false)
    private int id;

    private String image;

    private String ticker;

    private double market_cap;

    private double price;

    private double change24h;

    public Coin(int id, String image, String ticker, double market_cap, double price, double change24h) {
        this.id = id;
        this.image = image;
        this.ticker = ticker;
        this.market_cap = market_cap;
        this.price = price;
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

    public double getMarket_cap(){ return market_cap; }

    public double getPrice() {
        return price;
    }

    public double getChange24h() {
        return change24h;
    }
}
