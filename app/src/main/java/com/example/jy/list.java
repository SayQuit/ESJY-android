package com.example.jy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class list extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
    }

    public void enterDetail(View v){
        Intent intent=new Intent();
        intent.setClass(this,detail.class);
        startActivity(intent);
    }
}