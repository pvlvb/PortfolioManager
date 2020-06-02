package com.example.portfoliomanager.PMFragment.PMFragmentAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.portfoliomanager.Model.LocalDataSource.PortfolioCoin;
import com.example.portfoliomanager.Model.RemoteDataSource.BinanceConverter.BinanceCoin;
import com.example.portfoliomanager.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PortfolioAdapter extends RecyclerView.Adapter<PortfolioAdapter.CoinHolder> {
    private List<PortfolioCoin> coins = new ArrayList<>();
    private OnItemClickListenerPM onItemClickListener;

    @NonNull
    @Override
    public PortfolioAdapter.CoinHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pm_fragment_recyclerview_item,parent,false);
        return new PortfolioAdapter.CoinHolder(ItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PortfolioAdapter.CoinHolder holder, int position) {
        PortfolioCoin curr = coins.get(position);
        holder.textViewTicker.setText(curr.getTicker());
        holder.textViewQuantity.setText((processNumbers(curr.getAmount())));
        holder.textViewTotalPrice.setText((processNumbers(curr.getUpdated_total_price())));
    }

    @Override
    public int getItemCount() {
        return coins.size();
    }

    public void setCoins(List<PortfolioCoin> coins){
        this.coins = coins;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListenerPM listener) {
        onItemClickListener = listener;
    }
    class CoinHolder extends RecyclerView.ViewHolder{
        private TextView textViewTicker;
        private TextView textViewQuantity;
        private TextView textViewTotalPrice;
        public CoinHolder(@NonNull View itemView) {
            super(itemView);
            textViewTicker = itemView.findViewById(R.id.pm_ticker);
            textViewQuantity = itemView.findViewById(R.id.pm_quantity);
            textViewTotalPrice = itemView.findViewById(R.id.total_price);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(onItemClickListener != null && pos != RecyclerView.NO_POSITION){
                        onItemClickListener.onItemClick(coins.get(pos));
                    }
                }
            });
        }
    }

    public interface OnItemClickListenerPM {
        void onItemClick(PortfolioCoin portfolioCoin);
    }
    public void setOnItemClickListenerPM(OnItemClickListenerPM listener) {
        onItemClickListener = listener;
    }

    private String processNumbers(double num){
        DecimalFormat df = new DecimalFormat("#.####");
        String result;
        if(num >= 10E8){
            num /= 10E8;
            result = Double.toString(num) + "B";
        }
        else{
            result = df.format(num);
        }

        return result;
    }
}
