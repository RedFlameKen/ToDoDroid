package com.lacaba.tododroid.view.fragment;

import java.util.Calendar;
import java.util.Date;

import com.lacaba.tododroid.R;
import com.lacaba.tododroid.controller.auth.UserController;
import com.lacaba.tododroid.model.ResourceRepository;
import com.lacaba.tododroid.view.activity.ContactSupportActivity;
import com.lacaba.tododroid.view.component.RowButton;
import com.lacaba.tododroid.view.dialog.ConfirmDialog;
import com.lacaba.tododroid.view.dialog.PromptDialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";

    private TextView greetLabel;
    private TextView usernameLabel;
    private TextView emailLabel;

    private LinearLayout buttonPanel;

    private ResourceRepository resourceRepository;
    private UserController userController;

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
        userController = new UserController(requireActivity());

        greetLabel = view.findViewById(R.id.profile_greet_label);
        usernameLabel = view.findViewById(R.id.profile_username_label);
        emailLabel = view.findViewById(R.id.profile_email_label);

        buttonPanel = view.findViewById(R.id.profile_buttons_panel);

        greetLabel.setText(generateGreetLabelText());
        usernameLabel.setText(resourceRepository.getCurFirebaseUser().getDisplayName());
        emailLabel.setText(resourceRepository.getCurFirebaseUser().getEmail());

        initChangeUsernameButton();
        initSupportButton();
        initLogoutButton();
    }

    private String generateGreetLabelText(){
        Date time = new Date();
        if(time.after(new Date(getMorningTime())) && time.before(new Date(getAfternoonTime())))
            return "Good Morning,";
        else if(time.after(new Date(getAfternoonTime())) && time.before(new Date(getEveningTime())))
            return "Good Afternoon,";
        else if(time.after(new Date(getEveningTime())) && time.before(new Date(getNightTime())))
            return "Good Evening,";
        else
            return "Good Night,";
    }

    private long getMorningTime(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        return cal.getTimeInMillis();
    }

    private long getAfternoonTime(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE, 0);
        return cal.getTimeInMillis();
    }

    private long getEveningTime(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 17);
        cal.set(Calendar.MINUTE, 0);
        return cal.getTimeInMillis();
    }

    private long getNightTime(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 21);
        cal.set(Calendar.MINUTE, 0);
        return cal.getTimeInMillis();
    }

    private void initLogoutButton(){
        RowButton logoutButton = RowButton.inflate(getLayoutInflater(), R.drawable.logout, "Log out");

        logoutButton.setOnClickListener(view -> {
            userController.logout();
        });

        buttonPanel.addView(logoutButton);
    }

    private void initSupportButton(){
        RowButton supportButton = RowButton.inflate(getLayoutInflater(), R.drawable.support, "Contact Support");

        supportButton.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), ContactSupportActivity.class);
            startActivity(intent);
        });

        buttonPanel.addView(supportButton);
    }

    private void initChangeUsernameButton(){
        RowButton changeUsernameButton = RowButton.inflate(getLayoutInflater(), R.drawable.edit, "Change Username");

        changeUsernameButton.setOnClickListener(view -> {
            PromptDialog dialog = new PromptDialog.Builder()
                .setOkText("Change")
                .setHintText("New username")
                .setHeaderText("Change username")
                .setOnOkListener(text -> confirmUsernameChange(text))
                .build();
            dialog.show(getParentFragmentManager(), PromptDialog.TAG);
        });

        buttonPanel.addView(changeUsernameButton);
    }

    private void confirmUsernameChange(String username){
        ConfirmDialog dialog = new ConfirmDialog.Builder()
            .setMessageText(String.format("Are you sure you want to change your username to \"%s\"?", username))
            .setOnOkListener(() -> changeUsernameAction(username))
            .build();
        dialog.show(getParentFragmentManager(), ConfirmDialog.TAG);
    }

    private void changeUsernameAction(String username){
        userController.updateUsername(username, () -> {
            usernameLabel.setText(username);
        });
    }
    
}
