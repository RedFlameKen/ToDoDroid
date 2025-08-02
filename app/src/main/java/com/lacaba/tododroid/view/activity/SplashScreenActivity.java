package com.lacaba.tododroid.view.activity;

import com.lacaba.tododroid.R;
import com.lacaba.tododroid.controller.auth.UserController;
import com.lacaba.tododroid.model.ResourceRepository;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private UserController userController;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        userController = new UserController(this);

    }

    @Override
    public void onStart(){
        super.onStart();
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runOnUiThread(() -> {
                if(userController.isUserLoggedIn()){
                    reload();
                    return;
                }
                showLoginPage();
            });
        });
        thread.start();
    }

    private void showLoginPage(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    
    private void reload(){
        userController.getCurrentUserFromFirebaseUser(user -> {
            ResourceRepository.getInstance().setCurUser(user);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }

}
