package com.lacaba.tododroid.view.activity;

import com.lacaba.tododroid.R;

import com.lacaba.tododroid.controller.auth.UserController;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    private UserController userController;

    private EditText emailField;
    private EditText passwordField;
    private EditText confirmPasswordField;

    private Button signupButton;
    private TextView toLoginButton;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        userController = new UserController(this);

        initComponents();
        setEventListeners();
    }

    private void initComponents(){
        emailField = findViewById(R.id.signup_email_field);
        passwordField = findViewById(R.id.signup_password_field);
        confirmPasswordField = findViewById(R.id.signup_password_confirm_field);

        signupButton = findViewById(R.id.signup_signup_button);
        toLoginButton = findViewById(R.id.signup_login_link);
    }

    private void signup(){
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();
        String passwordConfirm = confirmPasswordField.getText().toString();

        if(!password.equals(passwordConfirm)){
            confirmPasswordField.setError("Password did not match", getResources().getDrawable(R.drawable.error_circle, null));
            return;
        }

        userController.signup(email, password);
    }

    private void setEventListeners(){
        signupButton.setOnClickListener(view -> {
            signup();
        });
        toLoginButton.setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
        userController.setOnAuthSuccessListener(user -> {
            finish();
        });
        userController.setOnAuthFailedListener(task -> {
            new AlertDialog.Builder(this)
                .setTitle("Signup Failed")
                .setMessage(task.getMessage())
                .setPositiveButton("OK", (dialog,which) -> dialog.dismiss())
                .show();
        });
    }
    
}
