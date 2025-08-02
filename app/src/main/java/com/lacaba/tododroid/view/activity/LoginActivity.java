package com.lacaba.tododroid.view.activity;

import com.lacaba.tododroid.R;
import com.lacaba.tododroid.controller.auth.UserController;
import com.lacaba.tododroid.model.ResourceRepository;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

public class LoginActivity extends AppCompatActivity {

    private EditText emailField;
    private EditText passwordField;

    private Button loginButton;
    private TextView toSignupButton;

    private UserController userController;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userController = new UserController(this);

        initComponents();
        setListeners();
    }

    private void initComponents(){
        emailField = findViewById(R.id.login_email_field);

        passwordField = findViewById(R.id.login_password_field);

        loginButton = findViewById(R.id.login_login_button);
        toSignupButton = findViewById(R.id.login_signup_link);
    }

    private void login(){
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        userController.login(email, password, () -> reload());
    }

    private void reload(){
        userController.getCurrentUserFromFirebaseUser(user -> {
            ResourceRepository.getInstance().setCurUser(user);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }

    private void setListeners(){
        loginButton.setOnClickListener(view -> {
            login();
        });
        toSignupButton.setOnClickListener(view -> {
            startActivity(new Intent(this, SignupActivity.class));
        });
        userController.setOnAuthFailedListener(e -> {
            new AlertDialog.Builder(this)
                .setTitle("Login Failed")
                .setMessage(e.getMessage())
                .setPositiveButton("OK", (dialog,which) -> dialog.dismiss())
                .show();
        });
    }
    
}
