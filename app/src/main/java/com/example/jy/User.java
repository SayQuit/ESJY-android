package com.example.jy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import util.Account;

public class User extends AppCompatActivity {

    String account;
    String name;
    TextView accounttxt;
    TextView nametxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Account application;
        application=(Account)getApplicationContext();
        this.account=application.getAccount();
        this.name=application.getName();

        accounttxt=findViewById(R.id.myaccount);
        nametxt=findViewById(R.id.myname);

        accounttxt.setText("账号:"+account);
        nametxt.setText("昵称:"+name);


    }

    public void click(View v){

        Intent intent=new Intent();
        intent.putExtra("interface",v.getTag().toString());
        intent.setClass(this,list.class);
        startActivity(intent);
    }

}