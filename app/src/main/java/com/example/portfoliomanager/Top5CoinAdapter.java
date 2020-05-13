package com.example.portfoliomanager;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.portfoliomanager.LocalDataSource.Coin;

import java.util.ArrayList;
import java.util.List;

public class Top5CoinAdapter extends RecyclerView.Adapter<Top5CoinAdapter.CoinHolder> {
    private List<Coin> coins = new ArrayList<>();

    @NonNull
    @Override
    public CoinHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mainfragment_coin_teim,parent,false);
        return new CoinHolder(ItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CoinHolder holder, int position) {
        Coin curr = coins.get(position);
        holder.textViewTicker.setText(curr.getTicker());
        holder.textViewPrice.setText(String.valueOf(curr.getPrice()));
        holder.textViewVolume.setText(String.valueOf(curr.getVolume()));
        holder.textViewChange.setText(String.valueOf(curr.getChange24h()) + "%");
        if(curr.getChange24h()>=0){
            holder.linearLayout.setBackgroundColor(Color.parseColor("#85c98c"));
        }
        else{
            holder.linearLayout.setBackgroundColor(Color.parseColor("#c25959"));
        }
    }

    @Override
    public int getItemCount() {
        return coins.size();
    }

    public void setCoins(List<Coin> coins){
        this.coins = coins;
        //TODO change this method
        notifyDataSetChanged();
    }

    class CoinHolder extends RecyclerView.ViewHolder{
        private ImageView icon;
        private TextView textViewTicker;
        private TextView textViewPrice;
        private TextView textViewVolume;
        private TextView textViewChange;
        private LinearLayout linearLayout;

        public CoinHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.img);
            textViewTicker = itemView.findViewById(R.id.ticker);
            textViewPrice = itemView.findViewById(R.id.price);
            textViewVolume = itemView.findViewById(R.id.volume);
            textViewChange = itemView.findViewById(R.id.change);
            linearLayout = itemView.findViewById(R.id.linear_layout);
        }
    }
}
