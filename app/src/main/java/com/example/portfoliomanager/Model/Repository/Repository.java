package com.example.portfoliomanager.Model.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.portfoliomanager.Model.LocalDataSource.Coin;
import com.example.portfoliomanager.Model.LocalDataSource.LocalDataSource;
import com.example.portfoliomanager.Model.LocalDataSource.News;
import com.example.portfoliomanager.Model.LocalDataSource.PortfolioCoin;
import com.example.portfoliomanager.Model.LocalDataSource.PortfolioValues;
import com.example.portfoliomanager.Model.RemoteDataSource.BinanceConverter.BinanceCoin;
import com.example.portfoliomanager.Model.RemoteDataSource.CMC_TopMarketCap_Converter.Result;
import com.example.portfoliomanager.Model.RemoteDataSource.RemoteDataSource;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private LocalDataSource localDataSource;
    private RemoteDataSource remoteDataSource;
    private ExecutorService executorService = Executors.newFixedThreadPool(4);
    private LiveData<List<Coin>> coins;
    private MutableLiveData<LoadingStatus> status = new MutableLiveData<LoadingStatus>();
    private MutableLiveData<LoadingStatus> newsStatus = new MutableLiveData<LoadingStatus>();
    private MutableLiveData<LoadingStatus> addCoinStatus = new MutableLiveData<LoadingStatus>();

    public Repository(LocalDataSource localDataSource, RemoteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    public MutableLiveData<LoadingStatus> getStatus() {
        return status;
    }

    public LiveData<List<Coin>> refreshTOPMC() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Result tmp = remoteDataSource.updateTOPMC();
                    if (tmp != null) {
                        localDataSource.putCoins(tmp, 0);
                        status.postValue(LoadingStatus.SUCCESSFUL);
                    } else {
                        status.postValue(LoadingStatus.FAILED);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return localDataSource.getData();
    }

    public LiveData<List<Coin>> refreshTOPGainers() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Result tmp = remoteDataSource.updateTOPGainers();
                    if (tmp != null) {
                        localDataSource.putCoins(tmp, 5);
                        status.postValue(LoadingStatus.SUCCESSFUL);
                    } else {
                        status.postValue(LoadingStatus.FAILED);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return localDataSource.getGainers();
    }

    public LiveData<List<Coin>> refreshTOPLosers() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Result tmp = remoteDataSource.updateTOPLosers();
                    if (tmp != null) {
                        localDataSource.putCoins(tmp, 10);
                        status.postValue(LoadingStatus.SUCCESSFUL);
                    } else {
                        status.postValue(LoadingStatus.FAILED);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return localDataSource.getLosers();
    }


    public void refreshNews(int page) {
        newsStatus.setValue(LoadingStatus.LOADING);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (page == 1) localDataSource.clearNews();
                    com.example.portfoliomanager.Model.RemoteDataSource.NewsConverter.News tmp = remoteDataSource.updateNews(page);
                    if (tmp != null) {
                        newsStatus.postValue(LoadingStatus.SUCCESSFUL);
                        localDataSource.putNews(tmp);
                    } else {
                        newsStatus.postValue(LoadingStatus.FAILED);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public LiveData<List<News>> getNews() {
        return localDataSource.getNews();
    }

    public MutableLiveData<LoadingStatus> getNewsStatus() {
        return newsStatus;
    }

    public void addPortfolioCoin(String ticker, double amount, double price_per_coin) {
        addCoinStatus.postValue(LoadingStatus.LOADING);

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                List<String> existence = localDataSource.checkForExistence(ticker);
                if (existence.size() == 0) {
                    try {
                        BinanceCoin result = remoteDataSource.getPrice(ticker);
                        if (result == null) {
                            addCoinStatus.postValue(LoadingStatus.TICKER_ERROR);
                        } else {
                            localDataSource.putPortfolioCoin(ticker, amount, price_per_coin, Double.parseDouble(result.getPrice()));
                            addCoinStatus.postValue(LoadingStatus.ADDED);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        BinanceCoin result = remoteDataSource.getPrice(ticker);
                        if (result == null) {
                            addCoinStatus.postValue(LoadingStatus.FAILED);
                        } else {
                            localDataSource.updatePortfolioCoin(ticker, amount, price_per_coin, Double.parseDouble(result.getPrice()));
                            addCoinStatus.postValue(LoadingStatus.EDITED);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

    }

    public void updatePortfolioPrices() {
        addCoinStatus.postValue(LoadingStatus.LOADING);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                List<String> tickers = localDataSource.getTickers();
                for (int i = 0; i < tickers.size(); ++i) {
                    try {
                        BinanceCoin t = remoteDataSource.getPrice(tickers.get(i));
                        PortfolioCoin portfolioCoin = localDataSource.getOriginalCoinPrice(tickers.get(i));
                        if (t == null) {
                            addCoinStatus.postValue(LoadingStatus.FAILED);
                            return;
                        } else {
                            localDataSource.updatePortfolioCoinPrice(tickers.get(i), portfolioCoin.getAmount(), Double.parseDouble(t.getPrice()));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                addCoinStatus.postValue(LoadingStatus.SUCCESSFUL);
            }
        });
    }

    public LiveData<List<PortfolioCoin>> getPortfolio() {
        return localDataSource.getPortfolio();
    }

    public LiveData<PortfolioValues> getPortfolioValues() {
        return localDataSource.getPortfolioValues();
    }

    public MutableLiveData<LoadingStatus> getAddCoinStatus() {
        return addCoinStatus;
    }


    public void decreasePortfolioCoin(String ticker, double amount) {
        addCoinStatus.postValue(LoadingStatus.LOADING);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                PortfolioCoin t = localDataSource.getOriginalCoinPrice(ticker);
                if (amount == t.getAmount()) {
                    localDataSource.deletePortfolioCoin(ticker);
                } else {
                    localDataSource.decreasePortfolioCoin(ticker, amount, t.getOriginal_total_price() / t.getAmount());
                }
                addCoinStatus.postValue(LoadingStatus.EDITED);
            }
        });

    }


    public void clearNews() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                localDataSource.clearNews();
            }
        });

    }
}
