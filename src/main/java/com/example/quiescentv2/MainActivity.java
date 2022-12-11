package com.example.quiescentv2;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNav; //Bottom Navigation tool

    //Fragments in the bottom navigation bar
    MainFragment mainFrag;
    TimerFragment timerFrag;
    MeditateFragment meditateFrag;
    SchedulerFragment schedulerFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize the user's preferences and the view model
        BreathePreferences.init(this);
        MainViewModel.init();

        //All fragments in the bottom navigation bar
        timerFrag = TimerFragment.newInstance();
        mainFrag = MainFragment.newInstance();
        meditateFrag = MeditateFragment.newInstance();
        schedulerFrag = SchedulerFragment.newInstance();

        bottomNav = findViewById(R.id.bottom_nav);

        bottomNav.setOnNavigationItemSelectedListener(this);

        //Create new instance of the main fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flFragment, mainFrag)
                    .commitNow();
        }
    }

    //Swap fragments on the relevant click on the bottom navigation bar
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, mainFrag).commit();
                return true;
            case R.id.timer:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, timerFrag).commit();
                return true;
            case R.id.meditation:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, meditateFrag).commit();
                return true;
            case R.id.scheduler:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, schedulerFrag).commit();
                return true;
        }
        return false;
    }
}