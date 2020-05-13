package com.example.portfoliomanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.portfoliomanager.LocalDataSource.Coin;

import java.util.List;

public class MainFragment extends Fragment {
    private MainFragmentViewModel mainFragmentViewModel;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.top5_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final Top5CoinAdapter adapter = new Top5CoinAdapter();
        recyclerView.setAdapter(adapter);

        mainFragmentViewModel = new ViewModelProvider(this).get(MainFragmentViewModel.class);
        mainFragmentViewModel.getCoins().observe(getViewLifecycleOwner(), new Observer<List<Coin>>() {
            @Override
            public void onChanged(List<Coin> coins) {
                //TODO Cout
                adapter.setCoins(coins);
            }
        });
        return view;
    }
}
