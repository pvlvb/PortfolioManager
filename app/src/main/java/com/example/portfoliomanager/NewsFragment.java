package com.example.portfoliomanager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;
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

import com.example.portfoliomanager.Model.LocalDataSource.Coin;
import com.example.portfoliomanager.Model.LocalDataSource.News;
import com.example.portfoliomanager.Model.Repository.LoadingStatus;
import com.example.portfoliomanager.NewsFragmentAdapters.EndlessScrollEventListener;
import com.example.portfoliomanager.NewsFragmentAdapters.NewsAdapter;

import java.util.List;
import java.util.Objects;

public class NewsFragment extends Fragment{
    private NewsFragmentViewModel newsFragmentViewModel = null;
    private LiveData<List<News>> newsList = null;
    private MutableLiveData<LoadingStatus> loadingStatusNews=null;
    private RecyclerView recyclerView;
    private EndlessScrollEventListener endlessScrollEventListener;
    private LinearLayoutManager linearLayoutManager;
    private ProgressBar progressBar;
    private TextView no_news_to_load;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.news_fragment, container, false);

        recyclerView = view.findViewById(R.id.news_recyclerview);
        progressBar = view.findViewById(R.id.progress_bar);
        no_news_to_load = view.findViewById(R.id.no_news_msg);

        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        final NewsAdapter newsAdapter = new NewsAdapter();
        recyclerView.setAdapter(newsAdapter);
        DividerItemDecoration itemDecor = new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecor);
        newsFragmentViewModel = new ViewModelProvider(this).get(NewsFragmentViewModel.class);
        newsList = newsFragmentViewModel.getNews(1);
        newsList.observe(getViewLifecycleOwner(), new Observer<List<News>>() {
            @Override
            public void onChanged(List<News> news) {
                newsAdapter.setNews(news);
            }
        });

        loadingStatusNews = newsFragmentViewModel.getStatus();
        loadingStatusNews.observe(getViewLifecycleOwner(), new Observer<LoadingStatus>() {
            @Override
            public void onChanged(LoadingStatus loadingStatus) {
                if(loadingStatus == LoadingStatus.FAILED){
                    //show toast
                    if(linearLayoutManager.getItemCount()==0){
                        no_news_to_load.setVisibility(View.VISIBLE);
                    }
                    Toast.makeText( getContext() , "Loading Failed", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
                if(loadingStatus == LoadingStatus.SUCCESSFUL) {
                    no_news_to_load.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                }
                if(loadingStatus  == LoadingStatus.LOADING) {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });


        endlessScrollEventListener = new EndlessScrollEventListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int pageNum, RecyclerView recyclerView) {
                Log.e("", "onLoadMore: " + pageNum );
                newsFragmentViewModel.getNews(pageNum);
            }
        };
        recyclerView.addOnScrollListener(endlessScrollEventListener);

        //// TODO: 27.05.2020 add divider +, positivie negative rates and time posted ago and infinite scrolling
        return view;
    }
}
