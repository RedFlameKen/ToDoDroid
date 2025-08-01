package com.lacaba.tododroid.view.fragment;

import com.lacaba.tododroid.R;
import com.lacaba.tododroid.model.ResourceRepository;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private TextView usernameLabel;

    private ResourceRepository resourceRepository;

    public ProfileFragment(){
        super(R.layout.fragment_profile);
        resourceRepository = ResourceRepository.getInstance();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        usernameLabel = view.findViewById(R.id.profile_username_label);
        usernameLabel.setText(resourceRepository.getCurUser().getUsername());
    }
    
}
