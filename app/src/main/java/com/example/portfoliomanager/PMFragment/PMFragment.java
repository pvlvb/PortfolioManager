package com.example.portfoliomanager.PMFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private PortfolioAdapter portfolioAdapter;
    private AlertDialog coindAddDialog;
    private AlertDialog coindEditDialog;


    private TextView total_portfolio_value;
    private TextView total_portfolio_profit;
    private EditText enter_ticker;
    private EditText enter_amount;
    private EditText enter_price_per_coin;
    private TextView edit_coin_ticker_ui;
    private TextView edit_coin_amount_ui;
    private EditText edit_coin_amount;
    private EditText edit_price_per_coin;
    private RadioGroup edit_dec_inc_choose;
    private RadioButton edit_increase;
    private RadioButton edit_decrease;
    private Boolean coin_edit_method;

    private LiveData<List<PortfolioCoin>> portfolioCoinList;
    private LiveData<PortfolioValues> portfolioValuesLiveData;
    private MutableLiveData<LoadingStatus> coinAddingStatus;





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.pm_fragment, container, false);
        floatingActionButton = view.findViewById(R.id.fab);
        total_portfolio_value = view.findViewById(R.id.total_value_num);
        total_portfolio_profit = view.findViewById(R.id.total_profit_num);
        swipeRefreshLayout = view.findViewById(R.id.pm_swiperefresh);

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
                String updated_value = processNumbers(portfolioValues.getPortfolio_updated_value());
                String profit = processNumbers(portfolioValues.getPortfolio_updated_value()-portfolioValues.getPortfolio_start_value());
                total_portfolio_value.setText((updated_value)+"$");
                total_portfolio_profit.setText((profit) + "$");
                if(portfolioValues.getPortfolio_updated_value()-portfolioValues.getPortfolio_start_value() >= 0){
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
                if(loadingStatus == LoadingStatus.SUCCESSFUL){
                    swipeRefreshLayout.setRefreshing(false);
                }
                if(loadingStatus == LoadingStatus.ADDED){
                    showMessage("Coin added");
                    enter_amount.setText("");
                    enter_price_per_coin.setText("");
                    enter_ticker.setText("");
                    closeAddCoinDialog();;
                }
                if(loadingStatus == LoadingStatus.EDITED){
                    showMessage("Coin edited");
                    edit_coin_amount.setText("");
                    edit_price_per_coin.setText("");
                    closeEditCoinDialog();
                }
                if(loadingStatus == LoadingStatus.TICKER_ERROR){
                    showMessage("Invalid ticker");

                }
                if(loadingStatus == LoadingStatus.FAILED){
                    showMessage("Loading failed");
                    swipeRefreshLayout.setRefreshing(true);
                }
            }
        });
        //recycler initialization
        recyclerView = view.findViewById(R.id.pm_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        portfolioAdapter = new PortfolioAdapter();
        recyclerView.setAdapter(portfolioAdapter);
        //edit dialog
        coindEditDialog = createEditCoinDialog();
        portfolioAdapter.setOnItemClickListenerPM(new PortfolioAdapter.OnItemClickListenerPM() {
            @Override
            public void onItemClick(PortfolioCoin portfolioCoin) {
                edit_coin_amount_ui.setText(processNumbers(portfolioCoin.getAmount()));
                edit_coin_ticker_ui.setText(portfolioCoin.getTicker());
                showEditCoinDialog();
            }
        });



        //fab add coin dialog
        coindAddDialog = createAddCoinDialog();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddCoinDialog();
            }
        });

        //refresh
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pmFragmentViewModel.updatePortfolioPrices();
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        return view;
    }



    private AlertDialog createAddCoinDialog() {

        View view = getActivity().getLayoutInflater().inflate(R.layout.coin_add_dialog, null);
        enter_ticker = view.findViewById(R.id.enter_ticker);
        enter_amount = view.findViewById(R.id.enter_amount);
        enter_price_per_coin = view.findViewById(R.id.enter_price_per_coin);
        AlertDialog alertDialogBuilder = new AlertDialog.Builder(getActivity()).setView(view).setNegativeButton("Cancel",null)
                .setPositiveButton("Add",null).create();
        alertDialogBuilder.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button pos_button = alertDialogBuilder.getButton(AlertDialog.BUTTON_POSITIVE);
                pos_button.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        if (TextUtils.isEmpty(enter_ticker.getText()) || TextUtils.isEmpty(enter_amount.getText()) || TextUtils.isEmpty(enter_price_per_coin.getText())) {
                            showMessage("All fields are required.");
                        }
                        else{
                            onPositiveAddCoinClick(enter_ticker.getText().toString(),
                                    Double.parseDouble(enter_amount.getText().toString()),
                                    Double.parseDouble(enter_price_per_coin.getText().toString()));
                        }
                    }
                });
                Button neg_button = alertDialogBuilder.getButton(AlertDialog.BUTTON_NEGATIVE);
                neg_button.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        enter_ticker.setText("");
                        enter_amount.setText("");
                        enter_price_per_coin.setText("");
                        closeAddCoinDialog();
                    }
                });
            }
        });
        return alertDialogBuilder;
    }

    private AlertDialog createEditCoinDialog() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.pm_edit_coin_dialog, null);

        edit_increase = view.findViewById(R.id.pm_increase);

        edit_decrease = view.findViewById(R.id.pm_decrease);

        edit_increase.setOnClickListener(radioGroupListener);

        edit_decrease.setOnClickListener(radioGroupListener);

        edit_coin_amount_ui = view.findViewById(R.id.edit_coin_amount_ui); //+
        edit_coin_ticker_ui = view.findViewById(R.id.edit_coin_ticker_ui); //+

        edit_coin_amount = view.findViewById(R.id.pm_enter_amount);

        edit_dec_inc_choose = view.findViewById(R.id.coin_action);

        edit_price_per_coin = view.findViewById(R.id.enter_price_per_coin);

        AlertDialog alertDialogBuilder = new AlertDialog.Builder(getActivity()).setView(view).setNegativeButton("Cancel",null)
                .setPositiveButton("Add",null).create();
        alertDialogBuilder.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button pos_button = alertDialogBuilder.getButton(AlertDialog.BUTTON_POSITIVE);
                pos_button.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        if(edit_dec_inc_choose.getCheckedRadioButtonId() == -1 || edit_coin_amount.getText().toString().isEmpty()){
                            showMessage("Please, decide what operation you want to do.");
                        }
                        else{
                            if(coin_edit_method && !edit_price_per_coin.getText().toString().isEmpty()){
                                onPositiveEditCoinClick(edit_coin_ticker_ui.getText().toString().toUpperCase(),Double.parseDouble(edit_coin_amount.getText().toString()),
                                        Double.parseDouble(edit_price_per_coin.getText().toString()),true);
                                closeEditCoinDialog();
                            }
                            else if (coin_edit_method && !edit_price_per_coin.getText().toString().isEmpty()){
                                showMessage("Please, enter per coin price.");
                            }
                            else{
                                if(Double.parseDouble(edit_coin_amount.getText().toString()) > 0 && Double.parseDouble(edit_coin_amount.getText().toString()) <= Double.parseDouble(edit_coin_amount_ui.getText().toString())){
                                    onPositiveEditCoinClick(edit_coin_ticker_ui.getText().toString(),Double.parseDouble(edit_coin_amount.getText().toString()),
                                            0,false);
                                }
                                else{
                                    showMessage("You can't remove more than you have.");
                                }

                            }
                        }
                    }
                });
                Button neg_button = alertDialogBuilder.getButton(AlertDialog.BUTTON_NEGATIVE);
                neg_button.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        enter_ticker.setText("");
                        enter_amount.setText("");
                        enter_price_per_coin.setText("");
                        closeEditCoinDialog();
                    }
                });
            }
        });

        return alertDialogBuilder;
    }

    private void showEditCoinDialog() {
        coindEditDialog.show();
    }

    private void onPositiveEditCoinClick(String ticker, double amount, double price_per_coin, boolean coin_edit_method) {
        if(coin_edit_method){
            pmFragmentViewModel.addPortfolioCoin(ticker,amount,price_per_coin);
            pmFragmentViewModel.updatePortfolioPrices();
        }
        else{
            pmFragmentViewModel.decreasePortfolioCoin(ticker,amount);
            pmFragmentViewModel.updatePortfolioPrices();
        }


    }

    private void closeEditCoinDialog() {
        edit_price_per_coin.setVisibility(View.GONE);
        edit_decrease.setChecked(false);
        edit_increase.setChecked(false);
        edit_price_per_coin.setText("");
        edit_coin_amount.setText("");
        coindEditDialog.dismiss();
    }

    private void onPositiveAddCoinClick(String ticker, double amount, double price_per_coin) {
        pmFragmentViewModel.addPortfolioCoin(ticker, amount, price_per_coin);
    }

    private void closeAddCoinDialog() {
        enter_ticker.setText("");
        enter_amount.setText("");
        enter_price_per_coin.setText("");
        coindAddDialog.dismiss();
    }

    private void showAddCoinDialog() {
        coindAddDialog.show();
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


    private View.OnClickListener radioGroupListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            RadioButton rb = (RadioButton) view;
            switch (rb.getId()){
                case R.id.pm_increase:
                    coin_edit_method = true;
                    edit_price_per_coin.setVisibility(View.VISIBLE);
                    break;
                case R.id.pm_decrease:
                    coin_edit_method = false;
                    edit_price_per_coin.setVisibility(View.GONE);
                    break;
            }
        }
    };

    private void showMessage(String text){
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

}
