package com.example.portfoliomanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.portfoliomanager.LocalDataSource.Coin;

import java.util.List;
import java.util.Objects;

public class MainFragment extends Fragment {
    private MainFragmentViewModel mainFragmentViewModel = null;
    private LiveData<List<Coin>> liveData;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.top5_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final Top5CoinAdapter adapter = new Top5CoinAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecor);

        mainFragmentViewModel = new ViewModelProvider(this).get(MainFragmentViewModel.class);
        // TODO Fix problems with ViewModelProvider
        //mainFragmentViewModel = new ViewModelProvider(this, (((PortfolioApp) Objects.requireNonNull(getActivity()).getApplication()).getViewModelFactory())).get(MainFragmentViewModel.class);

        liveData = mainFragmentViewModel.getData();
        liveData.observe(getViewLifecycleOwner(), new Observer<List<Coin>>() {
            @Override
            public void onChanged(List<Coin> coins) {
                //TODO Cout
                adapter.setCoins(coins);
            }
        });
        return view;
    }
}
