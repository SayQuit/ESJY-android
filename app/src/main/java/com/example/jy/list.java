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

import java.util.ArrayList;
import java.util.List;

import util.NetUtil;

public class list extends AppCompatActivity {

    public ImageButton img1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        this.init();

    }

    private static Bitmap base642Bitmap(String base64) {
        byte[] decode = Base64.decode(base64.split(",")[1],Base64.DEFAULT);
        Bitmap mBitmap = BitmapFactory.decodeByteArray(decode,0,decode.length);
        return mBitmap;
    }
    public void init(){
        img1=findViewById(R.id.list_img_1);
        System.out.println(img1);
        String base64="data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAB4AHgDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD3+iiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKxdc8V6N4dQHUbxUkPKxL8zn8BSlJRV2zSlSqVZKFOLbfRG1RXnEnxk0VZCEsbx1HRsKM/rTf8Ahcuj/wDQOvPzX/GsPrVH+Y9JZFmL/wCXT/D/ADPSaK82/wCFy6P/ANA68/Nf8aP+Fy6P/wBA68/Nf8aPrVH+Yf8AYOY/8+n+H+Z6TRXm3/C5dH/6B15+a/40f8Ll0f8A6B15+a/40fWqP8wf2DmP/Pp/h/mek0V5t/wuXR/+gdefmv8AjR/wuXR/+gdefmv+NH1qj/MH9g5j/wA+n+H+Z6TRXm3/AAuXR/8AoHXn5r/jR/wuXR/+gdefmv8AjR9ao/zB/YOY/wDPp/h/mek0V5t/wuXR/wDoHXn5r/jR/wALl0f/AKB15+a/40fWqP8AMH9g5j/z6f4f5npNFcx4U8bWfiya5jtbWeE26qzGXHOc+n0oraE4zV47HnYjD1cPUdOqrSXQPHXigeF9BaaIbrucmOAejY+8fYV88XNzNeXD3FzK8s0hyzuckmvR/jNcu2vadak/u47YyD6sxB/9BFeZ14+NqOVRx6I/R+GcFCjg41be9PVv8kFFFFcZ9IFFFFABRRRQAUUUUAFFFFABRRRQB6n8Fv8Aj+1b/rnH/M0UfBb/AI/tW/65x/zNFe5g/wCCj8s4l/5GVT5fkij8ZP8Aka7P/rxX/wBDevOq9F+Mn/I12f8A14r/AOhvXnVeViv40j73Iv8AkXUvQ7PwL4Lt/Fsd6095Lb/ZyoGxQc5z6/SuwHwZ08/8xe5/79D/ABrzTQ/FGq+HVmXTZxEJiC+UDZx06/WvSfhv4u1rxBrs9vqN0JYkg3qoQDnI9K6MO6EkoSjqeRnMc2oyqYmjVSprp16eXfzHH4MaeOur3P8A37X/ABrF8U/DSz8P+HrnUotSnmeLbhGQAHJxXb/ErXNQ0DQre606YRStcBGJUHI2k9/pXkt9421/XLU6de3ivbzMqsPLA71pXWHp3hy6/wBeZy5TLN8Wo4h1lyX1Ttey36fqX/Bvw9uvEsX225lNrp+cBwuWk/3R6e9ehJ8LvCkEYWVZ2Y8BpJ8E106JDovh4CGP91aW2Qo77VzXzjqetahq9/JeXdzK8jtnG44X2A7CicaWGik43bIwtbH51XqSp1fZwjtb8O3z1PT9e+EVq1s0uh3EiTqMiGc5V/YHsa8y03SnuvENtpV1vgeScQyZHzIc4PFdj4X+J8uh6T9hv7aa+KN+6kMmCF9DnrWems2+v/E3T9St7U2yzXEe5GbJLDgmsansJ8rho+qPSwTzPDqrTxPvRSbjLTp+P3nQa98KbLR9BvdRTU7iR7eIuEaMAH9a8sr6U8b/APIlav8A9e7V810sbTjTklFWL4ZxtfF0Jzry5mnb8EFFFFcZ9Kep/Bb/AI/tW/65x/zNFHwW/wCP7Vv+ucf8zRXuYP8Ago/LOJf+RlU+X5Io/GT/AJGuz/68V/8AQ3rzqvRfjJ/yNdn/ANeK/wDob151XlYr+NI+9yL/AJF1L0CvRvg7/wAjPd/9ep/9CFec1q6F4i1Dw5dyXWnOiSumxi6buM5qaE1CopPob5nhp4nCTo095Lqes/GP/kVrT/r7H/oLV4mjFHVlOGU5Fb+u+NdZ8R2SWmoyxPEj+YAkYU5xj+tc9V4mrGpU5onNkmBq4LCexq2vd7eZ9KeHdYtPE3huG4Qhg8XlTx55VsYINeW6r8Jtahv5BphhuLQtmNnk2so9CDXH6Pr2paDdfaNOunhY/eA5VvqOhrs4PjDrSRhZbKylb+9hl/rXS69GtFKrujxI5RmOXVpzwDThLo/66ep2Phf4daZpWklNZtra8u3Yu7suVQY6A+lefyT6bN8VLP8AsiCOKyjuo40EYwrEHlvzqrrvxE1/XLd7aSZLe3fho4Bt3D0J64rm7K8m0++gvLcgTQuHQkZAIrKrXp+7GC0R24DK8ava1sXO8pppK+iv+H3H0zr2nNrGiXenLKIjcRlN5Gdv4V5p/wAKXk/6DKf9+f8A69c9/wALV8U/8/Nv/wB+BR/wtXxT/wA/Nv8A9+BW9TEYao7yT/r5nmYPJ86wUXChOKT1/q8TO8Y+FD4Tvba2a7FyZozJuCbcc4rm61te8R6j4kuIp9SkR5Ik2KUQLxnNZNefUcXNuGx9fgo140IrEO8+tv6R6n8Fv+P7Vv8ArnH/ADNFHwW/4/tW/wCucf8AM0V7OD/go/NuJf8AkZVPl+SKPxk/5Guz/wCvFf8A0N686r0X4yf8jXZ/9eK/+hvXnVeViv40j73Iv+RdS9AooorA9YKKKKACiiigAooooAKKKKACiiigD1P4Lf8AH9q3/XOP+Zoo+C3/AB/at/1zj/maK9zB/wAFH5ZxL/yMqny/JHp9/oOk6pOs9/p9vcyquwPKgYhck4/U1V/4Q/w5/wBASy/78ituiuhwi9WjyI4qvFcsZtL1Zif8If4c/wCgJZf9+RR/wh/hz/oCWX/fkVt0UvZw7Ir65iP+fkvvZif8If4c/wCgJZf9+RR/wh/hz/oCWX/fkVt0Uezh2QfXMR/z8l97MT/hD/Dn/QEsv+/Io/4Q/wAOf9ASy/78ituij2cOyD65iP8An5L72Yn/AAh/hz/oCWX/AH5FH/CH+HP+gJZf9+RW3RR7OHZB9cxH/PyX3sxP+EP8Of8AQEsv+/Io/wCEP8Of9ASy/wC/Irboo9nDsg+uYj/n5L72Yn/CH+HP+gJZf9+RR/wh3hz/AKAll/35FbdFHs4dkH1zEf8APyX3soafommaS7tp9jBbNIAHMSBdwHrRV+iqSS0RhOcpvmm7vzCiiimSFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFAH//2Q==";
        img1.setImageBitmap(base642Bitmap(base64));
    }

    public void enterDetail(View v){
        Intent intent=new Intent();
        intent.setClass(this,detail.class);
        String s="shanniruo";
        intent.putExtra("param",s);
        startActivity(intent);
    }






}