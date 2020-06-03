package com.example.portfoliomanager.NewsFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.portfoliomanager.Model.LocalDataSource.News;
import com.example.portfoliomanager.Model.Repository.LoadingStatus;
import com.example.portfoliomanager.NewsFragment.NewsFragmentAdapters.EndlessScrollEventListener;
import com.example.portfoliomanager.NewsFragment.NewsFragmentAdapters.NewsAdapter;
import com.example.portfoliomanager.R;

import java.util.List;
import java.util.Objects;

public class NewsFragment extends Fragment {
    private NewsFragmentViewModel newsFragmentViewModel = null;
    private LiveData<List<News>> newsList = null;
    private MutableLiveData<LoadingStatus> loadingStatusNews = null;
    private RecyclerView recyclerView;
    private EndlessScrollEventListener endlessScrollEventListener;
    private LinearLayoutManager linearLayoutManager;
    private ProgressBar progressBar;
    private TextView no_news_to_load;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.news_fragment, container, false);

        //initializing elements
        recyclerView = view.findViewById(R.id.news_recyclerview);
        progressBar = view.findViewById(R.id.progress_bar);
        no_news_to_load = view.findViewById(R.id.no_news_msg);
        swipeRefreshLayout = view.findViewById(R.id.swiperefresh_news);

        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        final NewsAdapter newsAdapter = new NewsAdapter();
        recyclerView.setAdapter(newsAdapter);
        DividerItemDecoration itemDecor = new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecor);
        newsFragmentViewModel = new ViewModelProvider(this).get(NewsFragmentViewModel.class);
        newsFragmentViewModel.updateNews(1);
        newsList = newsFragmentViewModel.getNews();

        newsList.observe(getViewLifecycleOwner(), new Observer<List<News>>() {
            @Override
            public void onChanged(List<News> news) {
                newsAdapter.setNews(news);
            }
        });

        //loading animations
        loadingStatusNews = newsFragmentViewModel.getStatus();
        loadingStatusNews.observe(getViewLifecycleOwner(), new Observer<LoadingStatus>() {
            @Override
            public void onChanged(LoadingStatus loadingStatus) {
                if (loadingStatus == LoadingStatus.FAILED) {
                    if (linearLayoutManager.getItemCount() == 0) {
                        no_news_to_load.setVisibility(View.VISIBLE);
                    }
                    Toast.makeText(getContext(), "Loading Failed", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (loadingStatus == LoadingStatus.SUCCESSFUL) {
                    no_news_to_load.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (loadingStatus == LoadingStatus.LOADING) {
                    if (!swipeRefreshLayout.isRefreshing()) {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        // infinite loading
        endlessScrollEventListener = new EndlessScrollEventListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int pageNum, RecyclerView recyclerView) {
                newsFragmentViewModel.updateNews(pageNum);
            }
        };
        recyclerView.addOnScrollListener(endlessScrollEventListener);

        //on click browser opener
        newsAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(News news) {
                if (news != null) {
                    String url = news.getUrl();
                    Intent open_browser = new Intent(Intent.ACTION_VIEW);
                    open_browser.setData(Uri.parse(url));
                    startActivity(open_browser);
                }
            }
        });

        //refresh on swipe up
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                newsFragmentViewModel.updateNews(1);
            }
        });

        return view;
    }
}
