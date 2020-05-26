package com.example.portfoliomanager.MainFragmentAdapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.portfoliomanager.Model.LocalDataSource.Coin;
import com.example.portfoliomanager.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Top5GainersAdapter extends RecyclerView.Adapter<Top5GainersAdapter.CoinHolder> {
    private List<Coin> coins = new ArrayList<>();

    @NonNull
    @Override
    public Top5GainersAdapter.CoinHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mainfragment_coin_item_top5_gainers,parent,false);
        return new Top5GainersAdapter.CoinHolder(ItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CoinHolder holder, int position) {
        Coin curr = coins.get(position);
        Picasso.get().load(curr.getImage()).into(holder.icon);
        holder.textViewTicker.setText(curr.getTicker());
        holder.textViewPrice.setText("$" + String.valueOf(curr.getPrice()));
        holder.textViewChange.setText(String.valueOf(curr.getChange24h()) + "%");
        if(curr.getChange24h()>=0){
            holder.textViewChange.setTextColor(Color.parseColor("#85c98c"));
        }
        else{
            holder.textViewChange.setTextColor(Color.parseColor("#c25959"));
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
        private TextView textViewChange;
        public CoinHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.img);
            textViewTicker = itemView.findViewById(R.id.ticker);
            textViewPrice = itemView.findViewById(R.id.price);
            textViewChange = itemView.findViewById(R.id.change);
        }
    }


}
