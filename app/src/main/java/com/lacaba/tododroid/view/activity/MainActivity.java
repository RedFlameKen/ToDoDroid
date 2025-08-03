package com.lacaba.tododroid.view.activity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lacaba.tododroid.R;
import com.lacaba.tododroid.controller.DashboardController;
import com.lacaba.tododroid.view.fragment.DashboardFragment;
import com.lacaba.tododroid.view.fragment.ProfileFragment;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

    private DashboardFragment dashboardFrag;
    private ProfileFragment profileFrag;

    private FragmentManager fragMan;
    private BottomNavigationView bottomNav;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();

    }

    private void initComponents() {
        DashboardController.getInstance(); // Initialize the DashboardController INSTANCE

        bottomNav = findViewById(R.id.main_bottom_bar);

        fragMan = getSupportFragmentManager();

        dashboardFrag = new DashboardFragment();
        profileFrag = new ProfileFragment();

        showDashboard();

        addListeners();

    }

    private void showDashboard(){
        fragMan.popBackStack();
        fragMan.beginTransaction()
            .replace(R.id.main_content_panel, dashboardFrag, DashboardFragment.TAG)
            .setReorderingAllowed(true)
            .commit();
    }
    
    private void showProfile(){
        fragMan.beginTransaction()
            .replace(R.id.main_content_panel, profileFrag, ProfileFragment.TAG)
            .setReorderingAllowed(true)
            .addToBackStack("main")
            .commit();
    }


    private void addListeners(){
        fragMan.addOnBackStackChangedListener(() -> {
            if(fragMan.findFragmentByTag(DashboardFragment.TAG) != null && dashboardFrag.isVisible()){
                bottomNav.setSelectedItemId(R.id.main_action_home);
            }
        });
        bottomNav.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.main_action_home){
                showDashboard();
                return true;
            }
            if(item.getItemId() == R.id.main_action_profile){
                showProfile();
                return true;
            }
            return false;
        });
    }

}
