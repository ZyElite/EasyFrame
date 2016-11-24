package cn.zy.ef.util;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * @author zy
 * @version 1.0
 * @des
 * @created date 16-11-6
 */

public class AppUtil {


    public static boolean isBackgound(Context context) {

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcesse : appProcesses) {
            if (appProcesse.processName.equals(context.getPackageName())) {
                if (appProcesse.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    return true;
                }
            }
        }
        return false;
    }

}
