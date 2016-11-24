package cn.zy.ef.util;

import android.widget.Toast;

import cn.zy.ef.base.BaseApplication;

/**
 * @author zy
 * @version 1.0
 * @des
 * @created date 16-10-5
 */

public class Log {

    private static boolean isOpen = true;

    public static void toast(String msg) {
        Toast.makeText(BaseApplication.getAppContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void setIsOpen(boolean isOpen) {
        Log.isOpen = isOpen;
    }

    public static void e(String body) {
        if (isOpen)
            android.util.Log.e("json", body);
    }

}
