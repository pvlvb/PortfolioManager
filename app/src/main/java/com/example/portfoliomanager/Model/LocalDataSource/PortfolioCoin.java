package com.example.portfoliomanager.Model.LocalDataSource;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "portfolio")
public class PortfolioCoin {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String ticker;

    private double amount;

    private double original_total_price;

    private double updated_total_price;

    public PortfolioCoin(String ticker, double amount, double original_total_price, double updated_total_price) {
        this.ticker = ticker;
        this.amount = amount;
        this.original_total_price = original_total_price;
        this.updated_total_price = updated_total_price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTicker() {
        return ticker;
    }

    public double getAmount() {
        return amount;
    }

    public double getOriginal_total_price() {
        return original_total_price;
    }

    public double getUpdated_total_price() {
        return updated_total_price;
    }
}
