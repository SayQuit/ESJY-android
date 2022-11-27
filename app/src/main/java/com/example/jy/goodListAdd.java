package com.example.jy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import util.ParamsNetUtil;

public class goodListAdd extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_list_add);

        //绑定按钮
        Button submit = findViewById(R.id.submit);
        submit.setOnClickListener(this);
        Button goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.goBack) {
            System.out.println("点击了返回按钮");
            //点击了返回按钮
        }
        if (view.getId() == R.id.submit) {
            System.out.println("点击了提交按钮");
            EditText editText1 = findViewById(R.id.description);
            String description = editText1.getText().toString();
            EditText editText2 = findViewById(R.id.picture);
            String picture = editText2.getText().toString();

            String params = "?name=" + description + "&icon=" + picture;

            testReq(params);
        }
    }
    public void enterMain(){
        Intent intent=new Intent();
        intent.setClass(this,Main.class);
        startActivity(intent);

    }
    //网络部分
    private Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if (msg.what == 0) {
                String strData = (String) msg.obj;
                try {
                    JSONObject json = new JSONObject(strData);
                    String message = json.getString("message");

                    if (!message.equals("success")) {
                        System.out.println("提交失败！");
                    } else enterMain();


                } catch (JSONException e) {
                    e.printStackTrace();
                }


//                Toast.makeText(MainActivity.this,"主线程收到来自网络的消息啦！",Toast.LENGTH_SHORT).show();
            }

        }
    };

    public String submit(String params) {
        return ParamsNetUtil.getReq("/goodlist/add", params, "POST");
    }

    public void testReq(String params) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String stringFromNet = submit(params);

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