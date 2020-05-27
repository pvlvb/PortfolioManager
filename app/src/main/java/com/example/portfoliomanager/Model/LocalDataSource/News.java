package com.example.portfoliomanager.Model.LocalDataSource;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "news_table")
public class News {

    @PrimaryKey(autoGenerate = false)
    private int id;

    private String time_posted;

    private String title;

    private String ticker;

    private String url;

    private int positive;

    private int negative;

    public News(int id, String time_posted, String title, String ticker, String url, int positive, int negative) {
        this.id = id;
        this.time_posted = time_posted;
        this.title = title;
        this.ticker = ticker;
        this.url = url;
        this.positive = positive;
        this.negative = negative;
    }

    public int getId() {
        return id;
    }

    public String getTime_posted() {
        return time_posted;
    }

    public String getTitle() {
        return title;
    }

    public String getTicker() {
        return ticker;
    }

    public String getUrl() {
        return url;
    }

    public int getPositive() {
        return positive;
    }

    public int getNegative() {
        return negative;
    }
}
