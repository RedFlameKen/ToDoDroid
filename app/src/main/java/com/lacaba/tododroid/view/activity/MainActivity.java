package com.lacaba.tododroid.view.activity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.lacaba.tododroid.R;
import com.lacaba.tododroid.controller.DashboardController;
import com.lacaba.tododroid.controller.auth.UserController;
import com.lacaba.tododroid.model.ResourceRepository;
import com.lacaba.tododroid.model.user.User;
import com.lacaba.tododroid.view.fragment.DashboardFragment;
import com.lacaba.tododroid.view.fragment.ProfileFragment;

import android.content.res.Configuration;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

    private DashboardFragment dashboardFrag;
    private ProfileFragment profileFrag;

    private FragmentManager fragMan;
    private BottomNavigationView bottomNav;

    private UserController userController;
    private ResourceRepository resourceRepository;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userController = new UserController(this);

        resourceRepository = ResourceRepository.getInstance();

        initComponents();
    }

    private void initComponents() {
        DashboardController.getInstance(); // Initialize the DashboardController INSTANCE

        bottomNav = findViewById(R.id.main_bottom_bar);

        fragMan = getSupportFragmentManager();

        dashboardFrag = new DashboardFragment();

        addListeners();

        addFrags();
    }

    @Override
    public void onConfigurationChanged(Configuration config){
        super.onConfigurationChanged(config);

        if(bottomNav.getSelectedItemId() == R.id.main_action_home){
            showDashboard();
            fragMan.clearBackStack(null);
        }

        if(bottomNav.getSelectedItemId() == R.id.main_action_profile){
            fragMan.popBackStack();
            showProfile();
        }

    }

    private void addFrags(){
        fragMan.beginTransaction()
            .add(R.id.main_content_panel, dashboardFrag, null)
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
            .remove(profileFrag)
            .setCustomAnimations(
                    R.anim.slide_in_right,
                    R.anim.slide_out_right,
                    R.anim.slide_in_left,
                    R.anim.slide_out_left
            )
            .show(dashboardFrag)
            .commit();
    }
    
    private Bundle createArgs(){
        User user = resourceRepository.getCurUser();
        String username = user.getUsername();
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        Bundle args = new Bundle();
        args.putString("username", username);
        args.putString("email", email);
        return args;
    }

    private void showProfile(){
        profileFrag = new ProfileFragment();
        profileFrag.setArguments(createArgs());
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
            .add(R.id.main_content_panel, profileFrag)
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
