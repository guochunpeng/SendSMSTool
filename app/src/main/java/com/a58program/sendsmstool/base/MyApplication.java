package com.a58program.sendsmstool.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import com.a58program.sendsmstool.constant.GlobalParams;
import com.a58program.sendsmstool.utils.TelephoneUtils;
//import com.umeng.analytics.MobclickAgent;
//import com.umeng.commonsdk.UMConfigure;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;


public class MyApplication extends Application {

    private volatile ArrayList<Activity> mTempActivity = new ArrayList<Activity>();

    public static Typeface typeFace;
    private Context mResumeContext;

    public Context getResumeContext() {
        return mResumeContext;
    }

    public void setResumeContext(Context mResumeContext) {
        this.mResumeContext = mResumeContext;
    }

    private static Context sGlobalContext;
    @Override
    public void onCreate() {
        super.onCreate();
//        inityoumeng();
        sGlobalContext = getApplicationContext();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        clearTempActivityInBackStack();
    }

    public synchronized void addTempActivityInBackStack(Activity activity) {
        mTempActivity.add(activity);
    }

    public synchronized void clearTempActivityInBackStack() {
        Iterator<Activity> iterator = mTempActivity.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            activity.finish();
        }
        mTempActivity.clear();
    }

    public static Context getGlobalContextContext() {
        return sGlobalContext;
    }

    public synchronized void clearTempActivityInBackStack(Class<?> className) {
        if (className != null) {
            Iterator<Activity> iterator = mTempActivity.iterator();
            while (iterator.hasNext()) {
                Activity activity = iterator.next();
                String class_name=className.getName();
                if(null==class_name){
                    return;
                }
                if (!activity.getClass().getName().equals(class_name)) {
                    activity.finish();
                }
            }
            Activity saveActivity = null;
            for (Activity activity : mTempActivity) {
                if (activity.getClass().getName().equals(className.getName())) {
                    saveActivity = activity;
                }
            }
            mTempActivity.clear();
            addTempActivityInBackStack(saveActivity);
        }
    }

    public synchronized void clearTempActivityInBackStack2() {
        for (int x = 0; x < mTempActivity.size() - 1; x++) {
            Activity activity = mTempActivity.get(x);
            activity.finish();
        }
    }

    public MyApplication getApplication() {
        // TODO Auto-generated method stub
        return this;
    }

    public ArrayList<Activity> getAllActivities() {
        return mTempActivity;
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public synchronized Iterator<Activity> getActivities(){
        return mTempActivity.iterator();
    }



   /* private void inityoumeng(){
        UMConfigure.init(this, GlobalParams.UM_KEY,GlobalParams.UM_CHANNEL, 0,"");
        MobclickAgent.setScenarioType(mResumeContext, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }*/

}
