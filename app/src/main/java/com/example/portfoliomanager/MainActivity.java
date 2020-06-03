package com.example.portfoliomanager;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.portfoliomanager.MainFragment.MainFragment;
import com.example.portfoliomanager.NewsFragment.NewsFragment;
import com.example.portfoliomanager.PMFragment.PMFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.news:
                        if (!bottomNavigationView.getMenu().findItem(R.id.news).isChecked()) {
                            bottomNavigationView.getMenu().findItem(R.id.news).setChecked(true);
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NewsFragment()).commit();
                        }
                        break;
                    case R.id.home:
                        if (!bottomNavigationView.getMenu().findItem(R.id.home).isChecked()) {
                            bottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MainFragment()).commit();
                        }
                        break;
                    case R.id.portfolio:
                        if (!bottomNavigationView.getMenu().findItem(R.id.portfolio).isChecked()) {
                            bottomNavigationView.getMenu().findItem(R.id.portfolio).setChecked(true);
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PMFragment()).commit();
                        }
                        break;
                }
                return false;
            }
        });
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new MainFragment()).commit();
            bottomNavigationView.setSelectedItemId(R.id.home);
        } else {
            int m = savedInstanceState.getInt("menu_chosen");
            Log.e("", "onCreate: restore instance");
            switch (m) {
                case R.id.news:
                    bottomNavigationView.getMenu().findItem(R.id.news).setChecked(true);
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new NewsFragment()).commit();
                    break;
                case R.id.home:
                    bottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new MainFragment()).commit();
                    break;
                case R.id.portfolio:
                    bottomNavigationView.getMenu().findItem(R.id.portfolio).setChecked(true);
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new PMFragment()).commit();
                    break;
            }

        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt("menu_chosen", bottomNavigationView.getSelectedItemId());

    }
}
