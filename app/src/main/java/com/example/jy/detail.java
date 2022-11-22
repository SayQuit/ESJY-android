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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import util.Account;
import util.ParamsNetUtil;

public class detail extends AppCompatActivity {

    String id=new String();
    TextView txt;
    TextView price;
    ImageView img;

    ImageButton good;
    ImageButton collect;
    ImageButton comment;
    ImageButton car;

    boolean goodBool;
    boolean collectBool;
    boolean carBool;
    boolean commentBool;


    TextView goodnum;
    TextView collectnum;
    TextView commentnum;
    TextView carnum;


    TextView []commentList=new TextView[5];
    TextView []user=new TextView[5];

    String account;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        Account application;
        application=(Account)getApplicationContext();
        this.account=application.getAccount();


        this.init();





    }
    private static Bitmap base642Bitmap(String base64) {
        byte[] decode = Base64.decode(base64.split(",")[1],Base64.DEFAULT);
        Bitmap mBitmap = BitmapFactory.decodeByteArray(decode,0,decode.length);
        return mBitmap;
    }
    private void init(){
        Intent i = this.getIntent();


        this.commentBool=false;
        findViewById(R.id.commentBlock).setVisibility(View.INVISIBLE);

        this.txt=findViewById(R.id.detailtxt);
        this.price=findViewById(R.id.detailprice);
        this.img=findViewById(R.id.detailimg);

        this.id=i.getStringExtra("id");
        this.txt.setText(i.getStringExtra("cont"));
        this.price.setText(i.getStringExtra("price"));
        this.img.setImageBitmap(base642Bitmap(i.getStringExtra("img")));

        this.good=findViewById(R.id.detailgood);
        this.collect=findViewById(R.id.detailcollect);
        this.comment=findViewById(R.id.detailcomment);
        this.car=findViewById(R.id.detailcar);

        this.goodnum=findViewById(R.id.detailgoodnum);
        this.collectnum=findViewById(R.id.detailcollectnum);
        this.commentnum=findViewById(R.id.detailcommentnum);
        this.carnum=findViewById(R.id.detailcarnum);

        this.user[0]=findViewById(R.id.detailuser1);
        this.user[1]=findViewById(R.id.detailuser2);
        this.user[2]=findViewById(R.id.detailuser3);
        this.user[3]=findViewById(R.id.detailuser4);
        this.user[4]=findViewById(R.id.detailuser5);

        this.commentList[0]=findViewById(R.id.detailcomment1);
        this.commentList[1]=findViewById(R.id.detailcomment2);
        this.commentList[2]=findViewById(R.id.detailcomment3);
        this.commentList[3]=findViewById(R.id.detailcomment4);
        this.commentList[4]=findViewById(R.id.detailcomment5);


        this.getReq();

    }

    private void updateComment(int index,String userName,String txt){
        System.out.println(index);
        System.out.println(userName);
        System.out.println(txt);

        this.user[index].setText(userName);
        this.commentList[index].setText(txt);

        this.user[index].setVisibility(View.VISIBLE);
        this.commentList[index].setVisibility(View.VISIBLE);
    }

    private void hideComment(int index){
        this.user[index].setVisibility(View.INVISIBLE);
        this.commentList[index].setVisibility(View.INVISIBLE);
    }

    private void updateOp(boolean goodbool,boolean collectbool,boolean carbool,String goodNum,String collectNum,String commentNum,String carNum){
        this.goodBool=goodbool;
        this.collectBool=collectbool;
        this.carBool=carbool;
        if(goodbool){
            this.good.setImageResource(R.drawable.good1);
        }
        else{
            this.good.setImageResource(R.drawable.good);
        }
        if(collectbool){
            this.collect.setImageResource(R.drawable.collect1);
        }
        else{
            this.collect.setImageResource(R.drawable.collect);
        }
        if(carbool){
            this.car.setImageResource(R.drawable.car1);
        }
        else{
            this.car.setImageResource(R.drawable.car);
        }
        this.goodnum.setText(goodNum);
        this.collectnum.setText(collectNum);
        this.commentnum.setText(commentNum);
        this.carnum.setText(carNum);
    }

    private Handler mHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if (msg.what == 0) {
                String strData = (String) msg.obj;
//                System.out.println(strData);

                try {

                    JSONObject json = new JSONObject(strData);
                    String detail=json.optString("detail");
                    JSONObject d = new JSONObject(detail);


                    boolean goodbool=d.optBoolean("IsGood");
                    boolean carbool=d.optBoolean("IsInCar");
                    boolean collectbool=d.optBoolean("IsCollect");
                    String CollectNum=d.optString("CollectNum");
                    String GoodNum=d.optString("GoodNum");
                    String CommentNum=d.optString("CommentNum");
                    String CarNum=d.optString("CarNum");

//                    System.out.println(goodbool);
                    updateOp(goodbool,collectbool,carbool,GoodNum,CollectNum,CommentNum,CarNum);


                    String comment=d.optString("comment");
                    JSONArray array = new JSONArray(comment);
                    System.out.println(array);
//                    System.out.println(array);

                    for(int i=0;i<5;i++){
                        if(i<array.length()){
                            JSONObject item = array.getJSONObject(i);
                            String userName=item.optString("user");
                            String txt=item.optString("cont");

                            updateComment(i,userName+":",txt);
                        }
                        else{
                            hideComment(i);
                        }




                    }

                } catch(JSONException e){
                    e.printStackTrace();
                }






            }

        }
    };
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
        return ParamsNetUtil.getReq("/gooddetail/getDetail","?good="+this.id+"&user="+this.account,"GET");
    }

    private Handler carHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if (msg.what == 0) {
                init();
            }

        }
    };
    public void carClick(View v){
        if(this.account!="") {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String stringFromNet = car();
                    Message message = new Message();
                    message.what = 0;
                    message.obj = stringFromNet;
                    collectHandler.sendMessage(message);

                }
            }).start();
        }
    }
    private String car(){
        if(!this.carBool)return ParamsNetUtil.getReq("/car/add","?good="+this.id+"&user="+this.account,"POST");
        else return ParamsNetUtil.getReq("/car/cancel","?good="+this.id+"&user="+this.account,"POST");

    }

    private Handler collectHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if (msg.what == 0) {
                init();
            }

        }
    };
    public void collectClick(View v){
        if(this.account!="") {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String stringFromNet = collect();
                    Message message = new Message();
                    message.what = 0;
                    message.obj = stringFromNet;
                    collectHandler.sendMessage(message);

                }
            }).start();
        }
    }
    private String collect(){
        if(!this.collectBool)return ParamsNetUtil.getReq("/collect/add","?good="+this.id+"&user="+this.account,"POST");
        else return ParamsNetUtil.getReq("/collect/cancel","?good="+this.id+"&user="+this.account,"POST");

    }

    private Handler goodHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if (msg.what == 0) {
                init();
            }

        }
    };
    public void goodClick(View v){
        if(this.account!=""){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String stringFromNet = good();
                    Message message = new Message();
                    message.what = 0;
                    message.obj = stringFromNet;
                    goodHandler.sendMessage(message);

                }
            }).start();
        }



//        return ParamsNetUtil.getReq("/good/add","?good="+this.id+"&user=46510384588","POST");
    }
    private String good(){
        if(!this.goodBool)return ParamsNetUtil.getReq("/like/add","?good="+this.id+"&user="+this.account,"POST");
        else return ParamsNetUtil.getReq("/like/cancel","?good="+this.id+"&user="+this.account,"POST");

    }

    public void commentClick(View v){
        this.commentBool=!this.commentBool;
        if(commentBool) {
            findViewById(R.id.commentBlock).setVisibility(View.VISIBLE);
            ImageButton comt=findViewById(R.id.detailcomment);
            comt.setImageResource(R.drawable.comment1);
        }

        else {
            findViewById(R.id.commentBlock).setVisibility(View.INVISIBLE);
            ImageButton comt=findViewById(R.id.detailcomment);
            comt.setImageResource(R.drawable.comment);
        }

    }

    private Handler commentHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            System.out.println(msg);

            if (msg.what == 0) {

                updateComment();
            }

        }
    };
    public void addComment(View v){
        if(this.account!=""){
            EditText e= findViewById(R.id.commentCont);
            String cont=e.getText().toString();
            e.setText("");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String stringFromNet = comment(cont);
                    Message message = new Message();
                    message.what = 0;
                    message.obj = stringFromNet;
                    commentHandler.sendMessage(message);

                }
            }).start();
        }

//        提示
    }
    private String comment(String cont){
        return ParamsNetUtil.getReq("/comment/add","?good="+this.id+"&user="+this.account+"&cont="+cont,"POST");

    }
    private void updateComment(){

        this.updateCommentGetReq();
    }



    private Handler updateCommentHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if (msg.what == 0) {
                String strData = (String) msg.obj;
                try {

                    System.out.println(strData);
                    JSONObject json = new JSONObject(strData);
                    String detail=json.optString("detail");
                    JSONObject d = new JSONObject(detail);
                    String CommentNum=d.optString("CommentNum");
                    updateCommentNum(CommentNum);


                    String comment=d.optString("comment");
                    JSONArray array = new JSONArray(comment);
                    System.out.println(array);

                    for(int i=0;i<5;i++){
                        if(i<array.length()){
                            JSONObject item = array.getJSONObject(i);
                            String userName=item.optString("user");
                            String txt=item.optString("cont");
                            updateComment(i,userName+":",txt);
                        }
                        else{
                            hideComment(i);
                        }




                    }

                } catch(JSONException e){
                    e.printStackTrace();
                }






            }

        }
    };
    public void updateCommentGetReq(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                String stringFromNet = updateCommentGet();
                Message message = new Message();
                message.what = 0;
                message.obj = stringFromNet;
                updateCommentHandler.sendMessage(message);
//                System.out.println(message);

            }
        }).start();

    }
    public String updateCommentGet(){
        return ParamsNetUtil.getReq("/gooddetail/getDetail","?good="+this.id+"&user="+this.account,"GET");
    }
    private void updateCommentNum(String CommentNum){
        this.commentnum.setText(CommentNum);
    }










//    下面需要测试


    public void buy(View v){

        if(this.account=="")return;
        new Thread(new Runnable() {
            @Override
            public void run() {
                String stringFromNet = buyPost();
                Message message = new Message();
                message.what = 0;
                message.obj = stringFromNet;
                buyHandler.sendMessage(message);
//                System.out.println(message);

            }
        }).start();
    }

    public String buyPost(){
        return ParamsNetUtil.getReq("/order/buy","?good="+this.id+"&user="+this.account,"POST");
    }

    private Handler buyHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if (msg.what == 0) {
                String strData = (String) msg.obj;

                System.out.println(strData);






            }

        }
    };





}