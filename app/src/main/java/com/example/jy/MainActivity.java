package com.example.jy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import util.Account;
import util.NetUtil;
import util.ParamsNetUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Account application;
//        application=(Account)getApplicationContext();
//
//        String account=application.getAccount();
//        System.out.println(account);





    }












    private Handler mHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if (msg.what == 0) {
                String strData = (String) msg.obj;
//                System.out.println("收到消息");
                System.out.println(strData);

//                Toast.makeText(MainActivity.this,"主线程收到来自网络的消息啦！",Toast.LENGTH_SHORT).show();
            }

        }
    };
    public String test(){
        return ParamsNetUtil.getReq("/user/posttest","?test=jkl&wh=123","POST");
    }
    public void testReq(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                String stringFromNet = test();

                System.out.println(stringFromNet);

                Message message = new Message();
                message.what = 0;
                message.obj = stringFromNet;
                mHandler.sendMessage(message);


            }
        }).start();

//        System.out.println("开启子线程请求网络");
    }




    public void enterLogin(View v)  {


        Intent intent=new Intent();
        intent.setClass(this,login.class);
        startActivity(intent);









    }
    public void enterRegister(View v){
        Intent intent=new Intent();
        intent.setClass(this,register.class);
        startActivity(intent);
    }
    public void enterMain(View v){
        Intent intent=new Intent();
        intent.setClass(this,Main.class);
        startActivity(intent);
    }

    public void enterCar(View v){
        Intent intent=new Intent();
        intent.setClass(this,shoppingCar.class);
        startActivity(intent);
    }
}