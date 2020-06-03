package com.example.portfoliomanager;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.portfoliomanager.MainFragment.MainFragmentViewModel;

public class VMProviderFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.getName() == "MainFragmentViewModel"){
            return (T) new MainFragmentViewModel(PortfolioApp.getInstance());
        }
        return null;
    }
}
