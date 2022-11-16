package com.example.jy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import util.Account;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }


    public void enterClassify(View v) {
        Intent intent = new Intent();
        intent.setClass(this, classify.class);
        startActivity(intent);
    }
    public void enterUser(View v){
        Account application;
        application=(Account)getApplicationContext();
        if(application.getAccount().equals("")){
            return;
        }
        Intent intent=new Intent();
        intent.setClass(this,User.class);
        startActivity(intent);
    }

}