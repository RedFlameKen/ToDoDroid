package com.lacaba.tododroid.view.fragment;

import com.lacaba.tododroid.R;
import com.lacaba.tododroid.controller.auth.UserController;
import com.lacaba.tododroid.model.ResourceRepository;
import com.lacaba.tododroid.view.component.RowButton;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private TextView usernameLabel;
    private LinearLayout buttonPanel;

    private ResourceRepository resourceRepository;
    private UserController userController;

    public ProfileFragment(UserController userController){
        super(R.layout.fragment_profile);
        resourceRepository = ResourceRepository.getInstance();
        this.userController = userController;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        usernameLabel = view.findViewById(R.id.profile_username_label);
        buttonPanel = view.findViewById(R.id.profile_buttons_panel);

        usernameLabel.setText(resourceRepository.getCurUser().getUsername());
        initLogoutButton();
    }

    private void initLogoutButton(){
        RowButton logoutButton = RowButton.inflate(getLayoutInflater(), R.drawable.logout, "Log out");

        logoutButton.setOnClickListener(view -> {
            userController.logout();
        });

        buttonPanel.addView(logoutButton);
        
    }
    
}
