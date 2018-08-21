package com.a58program.sendsmstool.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.CallLog;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.a58program.sendsmstool.base.MyApplication;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import static android.content.Context.TELEPHONY_SERVICE;

public class TelephoneUtils {

    private Context mContext;


    public TelephoneUtils(Context context) {
        this.mContext = context;
    }

    public static int getWindowWidth(Context mContext){
        Resources resources = mContext.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int width = dm.widthPixels;
        return width;
    }
    public static void changeDark(Context mContext){
        WindowManager.LayoutParams lp = ((Activity)mContext).getWindow().getAttributes();
        lp.alpha = 0.4f;
        ((Activity)mContext).getWindow().setAttributes(lp);
    }
    public static void changeLight(Context mContext){
        WindowManager.LayoutParams lp = ((Activity)mContext).getWindow().getAttributes();
        lp.alpha = 1f;
        ((Activity)mContext).getWindow().setAttributes(lp);
    }


    /**
     * 获取当前应用的版本号
     * @return
     */
    public  static String getVersionName() {
        Context mContext= MyApplication.getGlobalContextContext();
        try {

            PackageManager manager = mContext.getPackageManager();
            PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "Fail";
        }
    }

    public static int getVersionCode(){
        Context mContext= MyApplication.getGlobalContextContext();
        try {
            PackageManager manager = mContext.getPackageManager();
            PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
            int versionCode =info.versionCode;
            return versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getPath(String filePath) {
        String path = Environment.getExternalStorageDirectory().getPath() + "/" + filePath + "/";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        return path;
    }

    public static String getDeiceIdMd5(Context context) {
        String CILIENT_ID="";
        if (null==CILIENT_ID||"".equals(CILIENT_ID)) {
            String uniImei = SharedPreferencesUtil.getInstance(context).getString("device_id");
            if (!(null==uniImei||"".equals(uniImei))) {
                CILIENT_ID = uniImei;
                return uniImei;
            }
            String realImei = getRealImei(context);
            String androidId = getAndroidId(context);
            String macAddress = getMacAddress(context);
            StringBuilder builder = new StringBuilder();
            builder.append(realImei);
            builder.append(androidId);
            builder.append(getMobileModel());
            builder.append(macAddress);
            if (null==(macAddress + androidId + realImei)||"".equals(macAddress + androidId + realImei)) {
                builder.append(System.currentTimeMillis());
            }
            String str = builder.toString();
            String clientId = Utils.md5(str);
            SharedPreferencesUtil.getInstance(context).putString("device_id",clientId);
            CILIENT_ID = clientId;
        }
        return CILIENT_ID;
    }

    private static String getMobileModel() {
        return Build.MODEL;
    }
    @SuppressLint("MissingPermission")
    private static String getRealImei(final Context context) {
        String sReallyImei = "";
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            sReallyImei = tm.getDeviceId();// imei
            //imei获取失败则不进行缓存，获取到“－”
            //imei获取成功则缓存内存与Disk，备后续使用
            if (TextUtils.isEmpty(sReallyImei) || sReallyImei.matches("0+")) {
                sReallyImei = "";
            } else if (sReallyImei.equals("zeros") || sReallyImei.equals("asterisks")) {
                //脏数据处理
                sReallyImei = "";
            }
            return sReallyImei;

        } catch (Exception e) {
            String message = e.getMessage();
            return sReallyImei;
        }
    }

    private static String getAndroidId(Context context) {
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        if ("9774d56d682e549c".equals(androidId)) {
            androidId = "";
        }
        return androidId;
    }

    /**
     * 获取wifi网络下的的MAC地址
     *
     * @return MAC地址
     */

    private static String getMacAddress(Context context) {
        String sMacAddress = "";
        try {
            String wifiInterfaceName = "wlan0";
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            if (interfaces != null) {
                while (interfaces.hasMoreElements()) {
                    NetworkInterface iF = interfaces.nextElement();
                    if (iF.getName().equalsIgnoreCase(wifiInterfaceName)) {
                        byte[] addr = iF.getHardwareAddress();
                        if (addr == null || addr.length == 0) {
                            break;
                        }

                        StringBuilder buf = new StringBuilder();
                        for (byte b : addr) {
                            buf.append(String.format("%02X:", b));
                        }
                        if (buf.length() > 0) {
                            buf.deleteCharAt(buf.length() - 1);
                        }
                        sMacAddress = buf.toString();
                        break;
                    }
                }
            }
        } catch (Exception se) {
            return "";
        }
        if (TextUtils.isEmpty(sMacAddress)) {
            try {
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo info = wifiManager.getConnectionInfo();
                sMacAddress = info.getMacAddress();
            } catch (Exception e) {
                return "";
            }
        }
        if (TextUtils.isEmpty(sMacAddress) || sMacAddress.equals("02:00:00:00:00:00")) {
            sMacAddress = "";
        }
        return sMacAddress;
    }

    public static synchronized String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
