package com.example.portfoliomanager.NewsFragmentAdapters;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.portfoliomanager.Model.LocalDataSource.News;
import com.example.portfoliomanager.R;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {
    private List<News> newsList = new ArrayList<>();
    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_fragment_news_item,parent,false);
        return new NewsHolder(ItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        News curr = newsList.get(position);
        if(curr!=null) {
            holder.textViewTicker.setText(curr.getTicker());
            holder.textViewTitle.setText(curr.getTitle());
            holder.textViewTime.setText(curr.getTime_posted() + "m");
            holder.textViewLiked.setText(curr.getPositive() + "  ");
            holder.textViewDisliked.setText(String.valueOf(curr.getNegative()));
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void setNews(List<News> newsList){
        this.newsList = newsList;
        notifyDataSetChanged();
    }

    class NewsHolder extends RecyclerView.ViewHolder{
        private TextView textViewTime;
        private TextView textViewTitle;
        private TextView textViewTicker;
        private TextView textViewLiked;
        private TextView textViewDisliked;
        public NewsHolder(@NonNull View itemView) {
            super(itemView);
            textViewTime = itemView.findViewById(R.id.time);
            textViewTitle = itemView.findViewById(R.id.title);
            textViewTicker = itemView.findViewById(R.id.news_ticker);
            textViewLiked = itemView.findViewById(R.id.news_like);
            textViewDisliked = itemView.findViewById(R.id.news_dislike);
        }
    }


}
