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

//import com.alibaba.fastjson.JSON;

import org.json.JSONException;
import org.json.JSONObject;

import util.Account;
import util.ParamsNetUtil;

public class register extends AppCompatActivity implements View.OnClickListener {

    //承载注册成功后来自服务器信息的类
//    private RegisterResp registerResp = new RegisterResp();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //绑定按钮
        Button register = findViewById(R.id.register);
        register.setOnClickListener(this);
        Button returnHome = findViewById(R.id.returnHome);
        returnHome.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.returnHome) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        if (view.getId() == R.id.register) {
            EditText editText1 = findViewById(R.id.nickname);
            String nickname = editText1.getText().toString();
            EditText editText2 = findViewById(R.id.password);
            String password = editText2.getText().toString();
            EditText editText3 = findViewById(R.id.ack_psw);
            String ack_psw = editText3.getText().toString();

            if (!password.equals(ack_psw)) {
                System.out.println("两次输入的密码不相同！");
            } else {

                //生成请求数据所需要的params部分
                String params = "?name=" + nickname + "&psw=" + password;
                handleRegister(params);

            }
        }
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
                    String account = json.getString("account");



                    Intent intent=new Intent(register.this, registerSuccess.class);
                    intent.putExtra("account",account);
                    startActivity(intent);



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //保存账号
//                registerResp = JSON.parseObject(strData, RegisterResp.class);
//                System.out.println("转换的结果是" + registerResp);
//
//                //跳转到注册成功页面
//                Intent intent = new Intent(register.this, registerSuccess.class);
//                Bundle bundle = new Bundle();
//
//                //判断是否注册成功
//                if (registerResp.getMessage().equals("注册成功"))
//                    bundle.putString("account", registerResp.getAccount());
//                else bundle.putString("account", registerResp.getMessage());
//                intent.putExtras(bundle);
//                startActivity(intent);

//                Toast.makeText(MainActivity.this,"主线程收到来自网络的消息啦！",Toast.LENGTH_SHORT).show();
            }

        }
    };

    public String register(String params) {
        return ParamsNetUtil.getReq("/user/register", params, "POST");
    }

    public void handleRegister(String params) {

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