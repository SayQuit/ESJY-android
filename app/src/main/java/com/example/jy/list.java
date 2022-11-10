package com.example.jy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import util.NetUtil;
import util.ParamsNetUtil;

public class list extends AppCompatActivity {

//    public ImageButton img1;
    String pid;
    ImageButton []img=new ImageButton[5];
    TextView []txt=new TextView[5];
    TextView []price=new TextView[5];
    String []id=new String[5];
    String []base64=new String[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Intent i = this.getIntent();
        this.pid=i.getStringExtra("pid");
        this.init();

    }

    private static Bitmap base642Bitmap(String base64) {
        byte[] decode = Base64.decode(base64.split(",")[1],Base64.DEFAULT);
        Bitmap mBitmap = BitmapFactory.decodeByteArray(decode,0,decode.length);
        return mBitmap;
    }
    public void init(){



        this.img[0]=findViewById(R.id.listimg1);
        this.img[1]=findViewById(R.id.listimg2);
        this.img[2]=findViewById(R.id.listimg3);
        this.img[3]=findViewById(R.id.listimg4);
        this.img[4]=findViewById(R.id.listimg5);


        this.txt[0]=findViewById(R.id.listcont1);
        this.txt[1]=findViewById(R.id.listcont2);
        this.txt[2]=findViewById(R.id.listcont3);
        this.txt[3]=findViewById(R.id.listcont4);
        this.txt[4]=findViewById(R.id.listcont5);

        this.price[0]=findViewById(R.id.listprice1);
        this.price[1]=findViewById(R.id.listprice2);
        this.price[2]=findViewById(R.id.listprice3);
        this.price[3]=findViewById(R.id.listprice4);
        this.price[4]=findViewById(R.id.listprice5);

//        for(int i=0;i<5;i++){
//            visibilityNone(i);
//        }

        this.getReq();


    }
    public void enterDetail(View v){
        Intent intent=new Intent();
        int index = Integer.parseInt(v.getTag().toString());

        String id=this.id[index];
        intent.putExtra("id",id);
        String cont=this.txt[index].getText().toString();
        intent.putExtra("cont",cont);
        String img=this.base64[index];
        intent.putExtra("img",img);
        String price=this.price[index].getText().toString();
        intent.putExtra("price",price);



        intent.setClass(this,detail.class);
        startActivity(intent);
    }


    private void setImg(int index,String img){
        this.img[index].setImageBitmap(base642Bitmap(img));

    }
    private void setTxt(int index,String text){
        this.txt[index].setText(text);

    }
    private void setId(int index,String id){
        this.id[index]=id;

    }
    private void setPrice(int index,String text){
        this.price[index].setText(text);

    }
    private void setBase64(int index,String base){
        this.base64[index]=base;
    }
    private void visibilityNone(int index){
        this.img[index].setVisibility(View.INVISIBLE);
        this.txt[index].setVisibility(View.INVISIBLE);
        this.price[index].setVisibility(View.INVISIBLE);

    }

    private Handler mHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if (msg.what == 0) {
                String strData = (String) msg.obj;
                try {
                    JSONObject json = new JSONObject(strData);

                    String message=json.optString("message");
//                    System.out.println(message);
                    if(message.equals("fail")){
                        for(int i=0;i<5;i++){
                            visibilityNone(i);
                        }
                        return;
                    }

                    String goodDetailList=json.optString("goodDetailList");

                    JSONArray array = new JSONArray(goodDetailList);
//                    System.out.println(array);

                    for(int i=0;i<5;i++){
                        if(i<array.length()){
                            JSONObject jsonobject = array.getJSONObject(i);
                            String id = jsonobject.getString("id");
                            String cont = jsonobject.getString("name");
                            String logo = jsonobject.getString("logo");
                            String price = jsonobject.getString("price");




                            setBase64(i,logo);
                            setImg(i,logo);
                            setTxt(i,cont);
                            setId(i,id);
                            setPrice(i,"￥"+price);
                        }
                        else{

                            visibilityNone(i);
                        }




                    }

                } catch(JSONException e){
                    e.printStackTrace();
                }
            }

        }
    };
    public String get(){
        return ParamsNetUtil.getReq("/gooddetail/get","?pid="+this.pid,"GET");
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



}