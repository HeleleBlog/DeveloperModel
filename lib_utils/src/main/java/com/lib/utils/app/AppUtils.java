package com.lib.utils.app;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.List;

/**
 * Created by orangeLe on 2016/8/5 0005.
 * App工具类
 */
public class AppUtils {

    /**
     * 判断服务是否开启
     *
     * @param context
     * @param className
     * @return
     */
    public static boolean isServiceRunning(Context context, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(Integer.MAX_VALUE);
        if (!(serviceList.size() > 0)) {
            context = null;
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        context = null;
        return isRunning;
    }

    /**
     * 获取App信息
     * @param Tag 0：返回versionName 1：返回versionCode
     * @return App信息
     */
    public static String getAppVersion(Context context, int Tag) {
        String version = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            if (Tag == 0) {
                version = pi.versionName;
            } else {
                version = pi.versionCode + "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return version;
    }

    /**
     * 判断App是否启动
     */
    public static boolean isTopActivity(Context context) {
        String packageName = "com.exmart.qiyishow";
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
        if (tasksInfo.size() > 0) {
            // 应用程序位于堆栈的顶层
            if (packageName.equals(tasksInfo.get(0).topActivity
                    .getPackageName())) {
                return true;
            }
        }
        return false;
    }
}
