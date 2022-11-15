package com.example.jy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class loginSuccess extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);
    }
    public void onClick(View view) {
        if (view.getId() == R.id.gotoLogin) {
            Intent intent = new Intent(this, login.class);
            startActivity(intent);
        }
        if (view.getId() == R.id.returnHome) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}