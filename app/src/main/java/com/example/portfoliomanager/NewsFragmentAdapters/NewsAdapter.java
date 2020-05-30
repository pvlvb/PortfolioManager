package com.example.portfoliomanager.NewsFragmentAdapters;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    private OnItemClickListener onItemClickListener;

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ItemView;
        if(viewType==0){
            ItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_fragment_news_item,parent,false);
        }
        else{
            ItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.no_more_news_item,parent,false);
        }
        return new NewsHolder(ItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        News curr = newsList.get(position);
        if(curr!=null) {
            holder.textViewTicker.setText(curr.getTicker());
            holder.textViewTitle.setText(curr.getTitle());
            String time_posted;
            long t = curr.getTime_posted();
            if(t >= 60){
                time_posted = t/60 + "h";
            }
            else if(t >= 1440){
                time_posted = t/1440+"d";
            }
            else{
                time_posted = t + "m";
            }
            holder.textViewTime.setText(time_posted);
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
        if(this.newsList.size()==200){
            addEndOfList();
        }
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(onItemClickListener != null && pos != RecyclerView.NO_POSITION){
                        onItemClickListener.onItemClick(newsList.get(pos));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(News news);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if(newsList.get(position) != null) return 0;
        else return 1;
    }

    public void addEndOfList(){
        newsList.add(null);
        notifyItemInserted(newsList.size()-1);
    }
}
