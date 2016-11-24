package cn.zy.ef.util;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author zy
 * @version 1.0
 * @des
 * @created date 16-10-31
 */

public class Serialize<T> {

    public void serialize(T obj, Context mContext, String fileName) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(mContext.getCacheDir() + fileName));
            objectOutputStream.writeObject(obj);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static ObjectInputStream deserialize(Context mContext, String fileName) {
        ObjectInputStream stream = null;
        try {
            stream = new ObjectInputStream(new FileInputStream(mContext.getCacheDir() + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stream;
    }
}
