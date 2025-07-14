package com.lacaba.tododroid.view.activity;

import com.lacaba.tododroid.R;

import com.lacaba.tododroid.view.fragment.DashboardFragment;
import com.lacaba.tododroid.view.fragment.ProfileFragment;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

    private DashboardFragment dashboardFrag;
    private ProfileFragment profileFrag;
    private FragmentManager fragMan;

    private Button dashboardButton;
    private Button profileButton;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    private void initComponents() {

        dashboardButton = findViewById(R.id.main_dashboard_bottom_button);
        profileButton = findViewById(R.id.main_profile_bottom_button);

        fragMan = getSupportFragmentManager();

        dashboardFrag = new DashboardFragment();
        profileFrag = new ProfileFragment();

        showDashboard();

        addListeners();

    }

    private void showDashboard(){
        fragMan.popBackStack();
        fragMan.beginTransaction()
            .replace(R.id.main_content_panel, dashboardFrag)
            .setReorderingAllowed(true)
            .commit();
    }
    
    private void showProfile(){
        fragMan.beginTransaction()
            .replace(R.id.main_content_panel, profileFrag)
            .setReorderingAllowed(true)
            .addToBackStack("main")
            .commit();
    }


    private void addListeners(){
        dashboardButton.setOnClickListener(view -> {
            if(fragMan.getBackStackEntryCount() > 0)
                showDashboard();
        });
        profileButton.setOnClickListener(view -> {
            if(fragMan.getBackStackEntryCount() == 0)
                showProfile();
        });
    }
    
}
