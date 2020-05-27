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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.portfoliomanager.Model.LocalDataSource.Coin;
import com.example.portfoliomanager.Model.LocalDataSource.News;
import com.example.portfoliomanager.NewsFragmentAdapters.NewsAdapter;

import java.util.List;

public class NewsFragment extends Fragment {
    private NewsFragmentViewModel newsFragmentViewModel = null;
    private LiveData<List<News>> newsList;
    private RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.news_fragment, container, false);
        recyclerView = view.findViewById(R.id.news_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final NewsAdapter newsAdapter = new NewsAdapter();
        recyclerView.setAdapter(newsAdapter);
        newsFragmentViewModel = new ViewModelProvider(this).get(NewsFragmentViewModel.class);
        newsList = newsFragmentViewModel.getNews();
        newsList.observe(getViewLifecycleOwner(), new Observer<List<News>>() {
            @Override
            public void onChanged(List<News> news) {
                newsAdapter.setNews(news);
            }
        });
        //// TODO: 27.05.2020 add divider, positivie negative rates and time posted ago and infinite scrolling 
        return view;
    }
}
