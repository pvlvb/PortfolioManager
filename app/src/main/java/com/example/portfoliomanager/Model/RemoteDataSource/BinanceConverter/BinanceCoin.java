package com.example.portfoliomanager.Model.RemoteDataSource.BinanceConverter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BinanceCoin {
    @SerializedName("symbol")
    @Expose
    private String symbol;
    @SerializedName("price")
    @Expose
    private String price;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
