package com.example.jy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class shoppingCar extends AppCompatActivity {

    String interface1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_car);
        Intent i = this.getIntent();
        this.interface1=i.getStringExtra("interface");
        System.out.println(interface1);
    }

    public void enterDetail(View v){
        Intent intent=new Intent();
        intent.setClass(this,shoppingCar.class);
        startActivity(intent);

    }
}