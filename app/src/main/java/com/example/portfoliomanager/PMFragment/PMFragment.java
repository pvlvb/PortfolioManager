package com.example.portfoliomanager.PMFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.portfoliomanager.Model.LocalDataSource.PortfolioCoin;
import com.example.portfoliomanager.Model.LocalDataSource.PortfolioValues;
import com.example.portfoliomanager.Model.Repository.LoadingStatus;
import com.example.portfoliomanager.PMFragment.PMFragmentAdapters.PortfolioAdapter;
import com.example.portfoliomanager.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.util.List;

public class PMFragment extends Fragment {
    private PMFragmentViewModel pmFragmentViewModel;
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private PortfolioAdapter portfolioAdapter;
    private LiveData<List<PortfolioCoin>> portfolioCoinList;
    private LiveData<PortfolioValues> portfolioValuesLiveData;
    private AlertDialog coindAddDialog;
    private TextView total_portfolio_value;
    private TextView total_portfolio_profit;
    private MutableLiveData<LoadingStatus> coinAddingStatus;
    private EditText enter_ticker;
    private EditText enter_amount;
    private EditText enter_price_per_coin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.pm_fragment, container, false);
        floatingActionButton = view.findViewById(R.id.fab);
        total_portfolio_value = view.findViewById(R.id.total_value_num);
        total_portfolio_profit = view.findViewById(R.id.total_profit_num);
        //TODO Add custom singleton provider in application class
        pmFragmentViewModel = new ViewModelProvider(this).get(PMFragmentViewModel.class);
        portfolioCoinList = pmFragmentViewModel.getPortoflio();
        portfolioValuesLiveData = pmFragmentViewModel.getPortfolioValues();
        coinAddingStatus = pmFragmentViewModel.getAddCoinStatus();
        portfolioCoinList.observe(getViewLifecycleOwner(), new Observer<List<PortfolioCoin>>() {
            @Override
            public void onChanged(List<PortfolioCoin> portfolioCoins) {
                portfolioAdapter.setCoins(portfolioCoins);
            }
        });
        portfolioValuesLiveData.observe(getViewLifecycleOwner(), new Observer<PortfolioValues>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(PortfolioValues portfolioValues) {
                double updated_value = processNumbers(portfolioValues.getPortfolio_updated_value());
                double profit = processNumbers(portfolioValues.getPortfolio_updated_value()-portfolioValues.getPortfolio_start_value());
                total_portfolio_value.setText(Double.toString(updated_value)+"$");
                total_portfolio_profit.setText(Double.toString(profit) + "$");
                if(profit >= 0){
                    total_portfolio_profit.setTextColor(Color.parseColor("#85c98c"));
                }
                else{
                    total_portfolio_profit.setTextColor(Color.parseColor("#c25959"));
                }
            }
        });
        coinAddingStatus.observe(getViewLifecycleOwner(), new Observer<LoadingStatus>() {
            @Override
            public void onChanged(LoadingStatus loadingStatus) {
                //TODO NOTIFICATIONS
            }
        });

        recyclerView = view.findViewById(R.id.pm_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        portfolioAdapter = new PortfolioAdapter();
        recyclerView.setAdapter(portfolioAdapter);

        coindAddDialog = createDialog();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        return view;
    }

    private AlertDialog createDialog() {

        View view = getActivity().getLayoutInflater().inflate(R.layout.coin_add_dialog, null);
        enter_ticker = view.findViewById(R.id.enter_ticker);
        enter_amount = view.findViewById(R.id.enter_amount);
        enter_price_per_coin = view.findViewById(R.id.enter_price_per_coin);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity()).setView(view);
        alertDialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //TODO validate input data .trim & isEmpty() && check if number and then show toast

                onPositiveClick(enter_ticker.getText().toString(),
                        Double.parseDouble(enter_amount.getText().toString()),
                        Double.parseDouble(enter_price_per_coin.getText().toString()));
                //TODO if request is successful, else left data
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                enter_ticker.setText("");
                enter_amount.setText("");
                enter_price_per_coin.setText("");
                closeDialog();
            }
        });
        return alertDialogBuilder.create();
    }


    private void onPositiveClick(String ticker, double amount, double price_per_coin) {
        pmFragmentViewModel.addPortfolioCoin(ticker, amount, price_per_coin);
    }

    private void closeDialog() {
        enter_ticker.setText("");
        enter_amount.setText("");
        enter_price_per_coin.setText("");
        coindAddDialog.dismiss();
    }

    private void showDialog() {
        coindAddDialog.show();
    }

    private double processNumbers(double num){
        DecimalFormat df = new DecimalFormat("#.##");
        double result = Double.parseDouble(df.format(num));
        return result;
    }
}
