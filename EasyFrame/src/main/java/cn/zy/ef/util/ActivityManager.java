package cn.zy.ef.util;

import android.app.Activity;
import android.os.Build;

import java.util.LinkedList;
import java.util.List;

/**
 * @author zy
 * @version 1.0
 * @des
 * @created date 16-11-1
 */

public class ActivityManager {
    private List<Activity> activities = new LinkedList<>();
    private static ActivityManager instance;

    private ActivityManager() {
    }

    ;

    public static ActivityManager getInstance() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public void exit() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            activities.forEach(Activity::finish);
        } else {
            for (Activity activity : activities) {
                activity.finish();
            }
        }
        //System.exit(0);
    }

    public List<Activity> getActivities() {
        return activities;
    }
}
