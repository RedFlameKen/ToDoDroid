package com.lacaba.tododroid.view.activity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lacaba.tododroid.R;
import com.lacaba.tododroid.controller.DashboardController;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragMan;
    private BottomNavigationView bottomNav;
    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();

    }

    private void initComponents() {
        DashboardController.getInstance(); // Initialize the DashboardController INSTANCE
        fragMan = getSupportFragmentManager();


        NavHostFragment navHostFragment = (NavHostFragment) fragMan.findFragmentById(R.id.main_content_panel);

        navController = navHostFragment.getNavController();
        bottomNav = findViewById(R.id.main_bottom_bar);

        // NavigationUI.setupWithNavController(bottomNav, navController);
        addListeners();
    }

    private void addListeners(){
        bottomNav.setOnItemSelectedListener(item -> {
            if(bottomNav.getSelectedItemId() == item.getItemId())
                return false;
            if(item.getItemId() == R.id.main_action_home){
                navController.navigate(R.id.mainnav_profile_action);
                return true;
            }
            if(item.getItemId() == R.id.main_action_profile){
                navController.navigate(R.id.mainnav_dashboard_action);
                return true;
            }
            return false;
        });
    }

}
