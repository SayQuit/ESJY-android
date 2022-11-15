package com.example.jy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import util.Account;

public class registerSuccess extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_success);

        Bundle bundle = getIntent().getExtras();
        TextView textView = findViewById(R.id.account);
        textView.setText(bundle.getString("account"));

        //绑定按钮
        Button gotoLogin = findViewById(R.id.gotoLogin);
        gotoLogin.setOnClickListener(this);
        Button returnHome = findViewById(R.id.returnHome);
        returnHome.setOnClickListener(this);




    }

    @Override
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