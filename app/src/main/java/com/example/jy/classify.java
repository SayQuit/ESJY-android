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
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import util.Account;
import util.NetUtil;
import util.ParamsNetUtil;

public class classify extends AppCompatActivity {

    ImageButton []img=new ImageButton[9];
    TextView []txt=new TextView[9];
    String []id=new String[9];
    int page;
    boolean isLast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classify);
        this.page=1;
        this.init();
        this.getReq();
    }
    public void enterList(View v){
        int index = Integer.parseInt(v.getTag().toString());
        Intent intent=new Intent();
        String pid=this.id[index];
        intent.putExtra("pid",pid);
        intent.setClass(this,list.class);
        startActivity(intent);
    };
    public void enterAddClassify(View v){

        Intent intent=new Intent();
        intent.setClass(this,goodListAdd.class);
        startActivity(intent);
    }

    void init(){
        this.img[0]=findViewById(R.id.imageButton);
        this.img[1]=findViewById(R.id.imageButton2);
        this.img[2]=findViewById(R.id.imageButton3);
        this.img[3]=findViewById(R.id.imageButton4);
        this.img[4]=findViewById(R.id.imageButton5);
        this.img[5]=findViewById(R.id.imageButton6);
        this.img[6]=findViewById(R.id.imageButton7);
        this.img[7]=findViewById(R.id.imageButton8);
        this.img[8]=findViewById(R.id.imageButton9);


        this.txt[0]=findViewById(R.id.textView);
        this.txt[1]=findViewById(R.id.textView2);
        this.txt[2]=findViewById(R.id.textView3);
        this.txt[3]=findViewById(R.id.textView4);
        this.txt[4]=findViewById(R.id.textView5);
        this.txt[5]=findViewById(R.id.textView6);
        this.txt[6]=findViewById(R.id.textView7);
        this.txt[7]=findViewById(R.id.textView8);
        this.txt[8]=findViewById(R.id.textView9);



    }

    private static Bitmap base642Bitmap(String base64) {
        byte[] decode = Base64.decode(base64.split(",")[1],Base64.DEFAULT);
        Bitmap mBitmap = BitmapFactory.decodeByteArray(decode,0,decode.length);
        return mBitmap;
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
    private void visibilityNone(int index){
        this.img[index].setVisibility(View.INVISIBLE);
        this.txt[index].setVisibility(View.INVISIBLE);

    }
    private Handler mHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if (msg.what == 0) {
                String strData = (String) msg.obj;
//                System.out.println(txt[0]);


                try {
                    JSONObject json = new JSONObject(strData);
                    String goodlist=json.optString("goodlist");
                    isLast=json.optBoolean("isLast");

                    JSONArray array = new JSONArray(goodlist);
//                    System.out.println(array);

                    for(int i=0;i<9;i++){
                        if(i<array.length()){
                            JSONObject jsonobject = array.getJSONObject(i);
                            String id = jsonobject.getString("id");
                            String name = jsonobject.getString("name");
                            String icon = jsonobject.getString("icon");

                            setImg(i,icon);
                            setTxt(i,name);
                            setId(i,id);
                        }
                        else{
                            visibilityNone(i);
                        }




                    }




                } catch(JSONException e){
                    e.printStackTrace();
                }

//                Toast.makeText(MainActivity.this,"主线程收到来自网络的消息啦！",Toast.LENGTH_SHORT).show();
            }

        }
    };
    public String get(){
        return ParamsNetUtil.getReq("/goodlist/get","?page="+String.valueOf(this.page),"GET");
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
    public void nextPage(View v){
        if(!this.isLast)this.page=this.page+1;
        this.getReq();

    }
    public void lastPage(View v){
        if(this.page>1)this.page=this.page-1;
        this.getReq();
    }
}