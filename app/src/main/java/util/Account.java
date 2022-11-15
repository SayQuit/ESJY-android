package util;

import android.app.Application;

public class Account extends Application {
    static String account;
    static String name;

    public void onCreate(){  //创建该类
        super.onCreate();
        this.account="";
        this.name="";

    }

    public void setAccount(String newAccount){
        this.account=newAccount;
    }
    public String getAccount(){
        return account;
    }
    public void setName(String newName){
        this.name=newName;
    }
    public String getName(){
        return name;
    }
}
