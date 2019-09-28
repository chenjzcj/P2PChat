package com.rdc.p2p.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.felix.gdtlib.helper.TencentAdHelper;
import com.rdc.p2p.bean.UserBean;
import com.rdc.p2p.gdt.TencentAdIDsImpl;
import com.rdc.p2p.util.UserUtil;

import org.litepal.LitePal;

/**
 * Created by Lin Yaotian on 2018/5/1.
 */

public class App extends Application {
    
    @SuppressLint("StaticFieldLeak")
    private static Context sContxet;
    private static UserBean sUserBean;
    private static String sMyIP;

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        sContxet = getApplicationContext();
        sUserBean = getUserBean();
        sMyIP = getMyIP();
        TencentAdHelper.getInstance().setTencentAdIDs(new TencentAdIDsImpl());
    }

    public static String getMyIP() {
        if (sMyIP == null){
            sMyIP = UserUtil.getMyIp();
        }
        return sMyIP;
    }

    public static void setMyIP(String sMyIP) {
        UserUtil.saveMyIp(sMyIP);
        App.sMyIP = sMyIP;
    }

    public static UserBean getUserBean() {
        if (sUserBean == null){
            sUserBean = UserUtil.getUser();
        }
        return sUserBean;
    }

    public static void setUserBean(UserBean sUserBean) {
        App.sUserBean = sUserBean;
    }

    public static Context getContxet() {
        return sContxet;
    }

    public static void setContxet(Context sContxet) {
        App.sContxet = sContxet;
    }
}
