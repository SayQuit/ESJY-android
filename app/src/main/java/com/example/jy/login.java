package com.example.jy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void enterMain(View v){
        Intent intent=new Intent();
        intent.setClass(this,Main.class);
        startActivity(intent);
    }
    public void enterRegister(View v){
        Intent intent=new Intent();
        intent.setClass(this,register.class);
        startActivity(intent);
    }
    public void enterLoginSuccess(View v){
        Intent intent=new Intent();
        intent.setClass(this,loginSuccess.class);
        startActivity(intent);
    }
}