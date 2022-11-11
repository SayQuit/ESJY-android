package util;

import android.app.Application;

public class Account extends Application {
    static String account;

    public void onCreate(){  //创建该类
        super.onCreate();
        this.account="";

    }

    public void setAccount(String newAccount){
        this.account=newAccount;
    }
    public String getAccount(){
        return account;
    }
}
