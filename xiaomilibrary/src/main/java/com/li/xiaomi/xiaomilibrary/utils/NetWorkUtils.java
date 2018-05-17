package com.li.xiaomi.xiaomilibrary.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 类描述：检测网络状态
 * 作  者：李清林
 * 时  间：2017/5/8
 * 修改备注：
 */
public class NetWorkUtils {

    public static final int WIFI = 0x1;//wifi
    public static final int G2 = 0x2;//32G
    public static final int G3 = 0x3;//3G
    public static final int G4 = 0x4;//4G
    public static final int NOConnect = 0x5;//没有网络链接

    /**
     * 判断是否有网络连接
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            // 获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            // 获取NetworkInfo对象
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            //判断NetworkInfo对象是否为空
            if (networkInfo != null)
                return networkInfo.isAvailable();
        }
        return false;
    }




    /**
     * 获取当前的网络状态 ：没有网络-0：WIFI网络1：4G网络-4：3G网络-3：2G网络-2
     * 自定义
     *
     * @param context
     * @return
     */
    public static int getAPNType(Context context) {
        //结果返回值
        int netType = NOConnect;
        //获取手机所有连接管理对象
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取NetworkInfo对象
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        //NetworkInfo对象为空 则代表没有网络
        if (networkInfo == null) {
            return netType;
        }
        //否则 NetworkInfo对象不为空 则获取该networkInfo的类型
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_WIFI) {
            //WIFI
            netType = WIFI;
        } else if (nType == ConnectivityManager.TYPE_MOBILE) {
            int nSubType = networkInfo.getSubtype();
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            //3G   联通的3G为UMTS或HSDPA 电信的3G为EVDO
            if (nSubType == TelephonyManager.NETWORK_TYPE_LTE
                    && !telephonyManager.isNetworkRoaming()) {
                netType = G4;
            } else if (nSubType == TelephonyManager.NETWORK_TYPE_UMTS
                    || nSubType == TelephonyManager.NETWORK_TYPE_HSDPA
                    || nSubType == TelephonyManager.NETWORK_TYPE_EVDO_0
                    && !telephonyManager.isNetworkRoaming()) {
                netType = G3;
                //2G 移动和联通的2G为GPRS或EGDE，电信的2G为CDMA
            } else if (nSubType == TelephonyManager.NETWORK_TYPE_GPRS
                    || nSubType == TelephonyManager.NETWORK_TYPE_EDGE
                    || nSubType == TelephonyManager.NETWORK_TYPE_CDMA
                    && !telephonyManager.isNetworkRoaming()) {
                netType = G2;
            } else {
                netType = G2;
            }
        }
        return netType;
    }

    static TimerTask timerTask;
    static Timer timer;
    static boolean asd;

    public static void getConnectListen(final Context mContext, final NetWorkConnectListen mNetWorkConnectListen) {
         asd = false;
        timerTask = new TimerTask() {
            @Override
            public void run() {
                boolean networkConnected = isNetworkConnected(mContext);
                if (!asd) {
                    if (networkConnected) {
                        asd = true;
                        timer.cancel();
                        timerTask.cancel();
                        mNetWorkConnectListen.connectListen();
                    }
                }
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 0, 1000);
    }


    public interface NetWorkConnectListen {
        void connectListen();
    }
}
