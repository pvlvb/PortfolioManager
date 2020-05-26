package com.example.portfoliomanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.portfoliomanager.MainFragmentAdapters.Top5CoinAdapter;
import com.example.portfoliomanager.MainFragmentAdapters.Top5GainersAdapter;
import com.example.portfoliomanager.Model.LocalDataSource.Coin;
import com.example.portfoliomanager.Model.Repository.LoadingStatus;

import java.util.List;

public class MainFragment extends Fragment {
    private MainFragmentViewModel mainFragmentViewModel = null;
    private LiveData<List<Coin>> topMC;
    private LiveData<List<Coin>> topGainers;
    private LiveData<List<Coin>> topLosers;
    private MutableLiveData<LoadingStatus> loadingStatus;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.main_fragment, container, false);

        //swipe refresh
        swipeRefreshLayout = view.findViewById(R.id.swiperefresh);



        //recyclers
        RecyclerView topMCRecyclerView = view.findViewById(R.id.top5_recyclerview);
        RecyclerView topGainersRecyclerView = view.findViewById(R.id.gainers_recyclerview);
        RecyclerView topLosersRecyclerView = view.findViewById(R.id.losers);

        topMCRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically(){
                return false;
            }
        });
        topGainersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically(){
                return false;
            }
        });
        topLosersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically(){
                return false;
            }
        });

        final Top5CoinAdapter top5CoinAdapter = new Top5CoinAdapter();
        final Top5GainersAdapter top5GainersAdapter = new Top5GainersAdapter();
        final Top5GainersAdapter top5LosersAdapter = new Top5GainersAdapter();

        topMCRecyclerView.setAdapter(top5CoinAdapter);
        topGainersRecyclerView.setAdapter(top5GainersAdapter);
        topLosersRecyclerView.setAdapter(top5LosersAdapter);

        topMCRecyclerView.setHasFixedSize(true);
        DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        topMCRecyclerView.addItemDecoration(itemDecor);
        topGainersRecyclerView.addItemDecoration(itemDecor);
        topLosersRecyclerView.addItemDecoration(itemDecor);
        //recyclers end

        mainFragmentViewModel = new ViewModelProvider(this).get(MainFragmentViewModel.class);

        topMC = mainFragmentViewModel.getTOPMC();
        topGainers = mainFragmentViewModel.getGainers();
        topLosers = mainFragmentViewModel.getLosers();
        loadingStatus = mainFragmentViewModel.getStatus();

        topMC.observe(getViewLifecycleOwner(), top5CoinAdapter::setCoins);
        topGainers.observe(getViewLifecycleOwner(), top5GainersAdapter::setCoins);
        topLosers.observe(getViewLifecycleOwner(), top5LosersAdapter::setCoins);
        loadingStatus.observe(getViewLifecycleOwner(), new Observer<LoadingStatus>() {
            @Override
            public void onChanged(LoadingStatus loadingStatus) {
                if(loadingStatus == LoadingStatus.FAILED){
                    Toast.makeText( getContext() , "Loading failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener (() -> {
            topMC = mainFragmentViewModel.getTOPMC();
            topGainers = mainFragmentViewModel.getGainers();
            topLosers = mainFragmentViewModel.getLosers();
            swipeRefreshLayout.setRefreshing(false);
        });
        //TODO learn about dagger and about its usability in this app
        return view;
    }
}
