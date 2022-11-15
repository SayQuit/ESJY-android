package com.example.jy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class shoppingCar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_car);
    }

    public void enterDetail(View v){
        Intent intent=new Intent();
        intent.setClass(this,shoppingCar.class);
        startActivity(intent);
    }
}