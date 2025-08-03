package com.lacaba.tododroid.view.activity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lacaba.tododroid.R;
import com.lacaba.tododroid.controller.DashboardController;
import com.lacaba.tododroid.view.fragment.DashboardFragment;
import com.lacaba.tododroid.view.fragment.ProfileFragment;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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

        addListeners();

        addFrags();
    }

    private void addFrags(){
        fragMan.beginTransaction()
            .add(R.id.main_content_panel, dashboardFrag, null)
            .add(R.id.main_content_panel, profileFrag, null)
            .hide(profileFrag)
            .commit();
    }

    private void showDashboard(){
        fragMan.beginTransaction()
            .setCustomAnimations(
                    R.anim.slide_in_left,
                    R.anim.slide_out_left,
                    R.anim.slide_in_left,
                    R.anim.slide_out_left
            )
            .hide(profileFrag)
            .setCustomAnimations(
                    R.anim.slide_in_right,
                    R.anim.slide_out_right,
                    R.anim.slide_in_left,
                    R.anim.slide_out_left
            )
            .show(dashboardFrag)
            .commit();
    }
    
    private void showProfile(){
        fragMan.beginTransaction()
            .setCustomAnimations(
                    R.anim.slide_in_right,
                    R.anim.slide_out_right,
                    R.anim.slide_in_right,
                    R.anim.slide_out_right
            )
            .hide(dashboardFrag)
            .setCustomAnimations(
                    R.anim.slide_in_left,
                    R.anim.slide_out_left,
                    R.anim.slide_in_right,
                    R.anim.slide_out_right
            )
            .show(profileFrag)
            .addToBackStack(null)
            .commit();
    }


    private void addListeners(){
        fragMan.addOnBackStackChangedListener(() -> {
            if(fragMan.getBackStackEntryCount() == 0){
                bottomNav.setSelectedItemId(R.id.main_action_home);
            }
        });
        bottomNav.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.main_action_home){
                showDashboard();
                fragMan.popBackStack();
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
