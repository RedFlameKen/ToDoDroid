package com.lacaba.tododroid.view.activity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lacaba.tododroid.R;
import com.lacaba.tododroid.controller.DashboardController;
import com.lacaba.tododroid.controller.auth.UserController;
import com.lacaba.tododroid.controller.todo.ToDoListController;
import com.lacaba.tododroid.model.ResourceRepository;
import com.lacaba.tododroid.view.dialog.CreateToDoListDialog;
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

    private ToDoListController todolistController;
    private DashboardController dashboardController;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todolistController = new ToDoListController();

        initComponents();

    }

    private void initComponents() {

        bottomNav = findViewById(R.id.main_bottom_bar);

        fragMan = getSupportFragmentManager();

        String userId = ResourceRepository.getInstance().getCurUser().getId();
        todolistController.getToDoLists(userId, todolists -> {
            dashboardController = new DashboardController(todolists);
            dashboardFrag = new DashboardFragment(dashboardController);
            profileFrag = new ProfileFragment(new UserController(this));

            showDashboard();

            addListeners();
        });

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
        bottomNav.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.main_action_home){
                showDashboard();
                return true;
            }
            if(item.getItemId() == R.id.main_action_create){
                CreateToDoListDialog dialog = new CreateToDoListDialog();
                dialog.setOnToDoListCreateListener(name -> dashboardController.addToDoList(name));
                dialog.show(getSupportFragmentManager(), CreateToDoListDialog.TAG);
                return false;
            }
            if(item.getItemId() == R.id.main_action_profile){
                showProfile();
                return true;
            }
            return false;
        });
    }
    
}
