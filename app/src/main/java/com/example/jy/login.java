package com.example.jy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import org.json.JSONException;
import org.json.JSONObject;

import util.Account;
import util.ParamsNetUtil;

public class login extends AppCompatActivity implements View.OnClickListener {

    //承载登录成功后来自服务器信息的类
//    private LoginResp loginResp = new LoginResp();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //绑定单击事件
        Button login = findViewById(R.id.login);
        login.setOnClickListener(this);
        Button returnHome = findViewById(R.id.returnHome);
        returnHome.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.login) {
            EditText editText1 = findViewById(R.id.username);
            String username = editText1.getText().toString();
            System.out.println("username = " + username);
            EditText editText2 = findViewById(R.id.password);
            String password = editText2.getText().toString();
            System.out.println("password = " + password);

            String params = "?account=" + username + "&psw=" + password;

            testReq(params);
        }
        if (view.getId() == R.id.returnHome) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }


    //网络部分
    private Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if (msg.what == 0) {
                String strData = (String) msg.obj;
                JSONObject json = null;
                System.out.println(strData);
                try {
                    json = new JSONObject(strData);
                    String message = json.getString("message");
                    String account = json.getString("account");
                    String name = json.getString("name");
                    if(message.equals("success")){
                        Account application;
                        application=(Account)getApplicationContext();
                        application.setAccount(account);
                        application.setName(name);


                        System.out.println("登陸成功登陸成功登陸成功登陸成功");
                        Intent intent=new Intent(login.this, loginSuccess.class);
                        startActivity(intent);
                    }
                    else{
                        System.out.println("登录失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                System.out.println("收到消息");
//                System.out.println("接收消息：" + strData);

//                //保存账号
//                loginResp = JSON.parseObject(strData, LoginResp.class);
//                System.out.println("转换的结果是" + loginResp);
//
//                //跳转到注册成功页面
//                Intent intent = new Intent(login.this, loginSuccess.class);
//
                //判断是否登录成功
//                if (loginResp.getMessage().equals("success")) {
//                    startActivity(intent);
//                } else System.out.println("登陆失败！");



//                Toast.makeText(MainActivity.this,"主线程收到来自网络的消息啦！",Toast.LENGTH_SHORT).show();
            }

        }
    };

    public String register(String params) {
        return ParamsNetUtil.getReq("/user/login", params, "POST");
    }

    public void testReq(String params) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String stringFromNet = register(params);

                System.out.println(stringFromNet);

                Message message = new Message();
                message.what = 0;
                message.obj = stringFromNet;
                mHandler.sendMessage(message);


            }
        }).start();

//        System.out.println("开启子线程请求网络");
    }

}