package com.example.portfoliomanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.portfoliomanager.Model.RemoteDataSource.NewsConverter.News;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_menu);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.news:
                        if(!bottomNavigationView.getMenu().findItem(R.id.news).isChecked()){
                            bottomNavigationView.getMenu().findItem(R.id.news).setChecked(true);
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NewsFragment()).commit();
                        }
                        break;
                    case R.id.home:
                       if(!bottomNavigationView.getMenu().findItem(R.id.home).isChecked()){
                            bottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MainFragment()).commit();
                        }
                        break;
                    case R.id.portfolio:
//                        if(bottomNavigationView.getSelectedItemId() != R.id.portfolio){
//                            bottomNavigationView.setSelectedItemId(R.id.portfolio);
//                        }
                        break;
                }
                return false;
            }
        });
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new MainFragment()).commit();
        }
    }
}
