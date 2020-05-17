package com.example.portfoliomanager;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MainFragmentViewModelProvider implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.getName() == "MainFragmentViewModel"){
            return (T)(new MainFragmentViewModel(PortfolioApp.getInstance()));
        }
        return null;
    }


}
