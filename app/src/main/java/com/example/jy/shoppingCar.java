package com.example.jy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaCodec;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import util.Account;
import util.ParamsNetUtil;

public class shoppingCar extends AppCompatActivity {

    JSONArray array;
    String interface1;
    String account;
    boolean isLast;
    int page = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_car);
        Intent i = this.getIntent();
        this.interface1=i.getStringExtra("interface");
        System.out.println(interface1);
        Account application;
        application=(Account)getApplicationContext();
        this.account=application.getAccount();
        this.getReq();
    }

    public void getReq(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                String stringFromNet = get();
                Message message = new Message();
                message.what = 0;
                message.obj = stringFromNet;
                mHandler.sendMessage(message);

            }
        }).start();
    }

    public String get(){
        System.out.println(this.interface1);
        return ParamsNetUtil.getReq(this.interface1,"?user="+this.account+"&page="+this.page,"GET");
    }

    private Handler mHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if (msg.what == 0) {
                String strData = (String) msg.obj;
                System.out.println(strData);

                try{
                    JSONObject json = new JSONObject(strData);
                    String car=json.optString("goodlist");
                    isLast=json.optBoolean("isLast");
                    array = new JSONArray(car);
                    System.out.println(array);

                    LinearLayout commoditiesPage = findViewById(R.id.commoditiesPage);
                    int len = array.length();

                    for(int i=0;i<len;i++){
                        JSONObject Obj = array.getJSONObject(i);
                        addCommodityView(commoditiesPage,i,Obj);
                    }

                } catch(JSONException e) {
                    e.printStackTrace();
                }

            }

        }
    };

    private static Bitmap base642Bitmap(String base64) {
        byte[] decode = Base64.decode(base64.split(",")[1],Base64.DEFAULT);
        Bitmap mBitmap = BitmapFactory.decodeByteArray(decode,0,decode.length);
        return mBitmap;
    }

    private void addCommodityView(LinearLayout commoditiesPage,int index,JSONObject commodityObj){
        String name = commodityObj.optString("name");
        String price = commodityObj.optString("price");
        String logo = commodityObj.optString("logo");

        ImageButton image = new ImageButton(this);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(350,350);
        image.setLayoutParams(imageParams);
        image.setImageBitmap(base642Bitmap(logo));
        image.setBackgroundColor(Color.BLACK);

        LinearLayout textLayout = new LinearLayout(this);
        textLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams textLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.MATCH_PARENT);
        textLayout.setLayoutParams(textLayoutParams);
        textLayout.setPadding(20,20,20,20);

        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,100);
        TextView text1 = new TextView(this);
        text1.setText(name);
        text1.setTextSize(18);
        text1.setTextColor(Color.BLACK);
        text1.setLayoutParams(textParams);
        TextView text2 = new TextView(this);
        text2.setText("￥"+price);
        text2.setTextSize(18);
        text2.setTextColor(Color.parseColor("#EB4450"));
        text2.setLayoutParams(textParams);
        textLayout.addView(text1);
        textLayout.addView(text2);

        LinearLayout commodity = new LinearLayout(this);
        LinearLayout.LayoutParams commodityParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,400);
        commodity.setLayoutParams(commodityParams);
        commodity.setOrientation(LinearLayout.HORIZONTAL);
        commodity.addView(image);
        commodity.addView(textLayout);
        commodity.setOnClickListener(this::enterDetail);
        commodity.setTag(index);

        commoditiesPage.addView(commodity);
    };

    public void enterDetail(View v){
        Intent intent=new Intent();

        int index = Integer.parseInt(v.getTag().toString());
        try{
            JSONObject Obj = array.getJSONObject(index);
            String name = Obj.optString("name");
            String price = Obj.optString("price");
            String logo = Obj.optString("logo");
            String id = Obj.optString("id");
            intent.putExtra("id",id);
            intent.putExtra("cont",name);
            intent.putExtra("img",logo);
            intent.putExtra("price",price);
        }catch(JSONException e) {
            e.printStackTrace();
        }

        intent.setClass(this,detail.class);
        startActivity(intent);

    };
}