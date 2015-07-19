package com.cettco.coreutils.utils;

import android.content.Context;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author cettco
 */
public class SharedPreferencesManager{
    private static String sDefaultName = "com.cettco.coreutils";
    private static Context sContext;
    private static SharedPreferencesManager instance;
    private SharedPreferencesManager(Context context){
        this(context,sDefaultName);
    }
    private SharedPreferencesManager(Context context,String defaultName){
        this.sContext = context;
        this.sDefaultName = defaultName;
    }
    public static void createInstance(Context context){
        if(instance==null){
            synchronized (SharedPreferencesManager.class){
                if(instance==null){
                    instance = new SharedPreferencesManager(context);
                }
            }
        }
    }
    public static void createInstance(Context context, String defaultName){
        if(instance==null){
            synchronized (SharedPreferencesManager.class){
                if(instance==null){
                    instance = new SharedPreferencesManager(context,defaultName);
                }
            }
        }
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return getBoolean(getDefaultSharedPreferencesName(), key, defValue);
    }

    public static String getString(String key, String defValue) {
        return getString(getDefaultSharedPreferencesName(), key, defValue);
    }

    public static int getInt(String key, int defValue) {
        return getInt(getDefaultSharedPreferencesName(), key, defValue);
    }

    public static boolean putInt(String key, int value) {
        return putInt(getDefaultSharedPreferencesName(), key, value);
    }

    public static long getLong(String key, long defValue) {
        return getLong(getDefaultSharedPreferencesName(), key, defValue);
    }

    public static boolean putLong(String key, long value) {
        return putLong(getDefaultSharedPreferencesName(), key, value);
    }

    public static boolean putBoolean(String key, boolean value) {
        return putBoolean(getDefaultSharedPreferencesName(), key, value);
    }

    public static boolean putString(String key, String value) {
        return putString(getDefaultSharedPreferencesName(), key, value);
    }

    public static boolean getBoolean(String name, String key, boolean defValue) {
        if (instance != null && sContext != null) {
            return sContext.getSharedPreferences(name, Context.MODE_PRIVATE)
                    .getBoolean(key, defValue);
        }
        return defValue;
    }

    public static String getString(String name, String key, String defValue) {
        if (instance != null && sContext != null) {
            return sContext.getSharedPreferences(name, Context.MODE_PRIVATE)
                    .getString(key, defValue);
        }
        return defValue;
    }

    public static int getInt(String name, String key, int defValue) {
        if (instance != null && sContext != null) {
            return sContext.getSharedPreferences(name, Context.MODE_PRIVATE)
                    .getInt(key, defValue);
        }
        return defValue;
    }

    public static boolean putInt(String name, String key, int value) {
        if (instance != null && sContext != null) {
            sContext.getSharedPreferences(name, Context.MODE_PRIVATE).edit()
                    .putInt(key, value).apply();
            return true;
        }
        return false;
    }

    public static long getLong(String name, String key, long defValue) {
        if (instance != null && sContext != null) {
            return sContext.getSharedPreferences(name, Context.MODE_PRIVATE)
                    .getLong(key, defValue);
        }
        return defValue;
    }

    public static boolean putLong(String name, String key, long value) {
        if (instance != null && sContext != null) {
            sContext.getSharedPreferences(name, Context.MODE_PRIVATE).edit()
                    .putLong(key, value).apply();
            return true;
        }
        return false;
    }

    public static boolean putBoolean(String name, String key, boolean value) {
        if (instance != null && sContext != null) {
            sContext.getSharedPreferences(name, Context.MODE_PRIVATE).edit()
                    .putBoolean(key, value).apply();
            return true;
        }
        return false;
    }

    public static boolean putString(String name, String key, String value) {
        if (instance != null && sContext != null) {
            sContext.getSharedPreferences(name, Context.MODE_PRIVATE).edit()
                    .putString(key, value).apply();
            return true;
        }
        return false;
    }


    private static String getDefaultSharedPreferencesName() {
        return sDefaultName;
    }

    /**
     * Read the object from Base64 string.
     */
    private static Object fromString(String s) throws IOException,
            ClassNotFoundException {
        byte[] data = Base64.decode(s, Base64.DEFAULT);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(
                data));
        Object o = ois.readObject();
        ois.close();
        return o;
    }

    /**
     * Write the object to a Base64 string.
     */
    private static String toString(Serializable o) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        oos.close();
        return new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
    }
    public static <T extends Serializable>boolean saveSerializable(String key, T value){
        try {
            String valueString = toString(value);
            SharedPreferencesManager.putString(key, valueString);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public static <T extends Serializable> T getSerializable(String key, T defaultValue){
        String valueString = SharedPreferencesManager.getString(getDefaultSharedPreferencesName(), key,
                defaultValue.toString());
        if (valueString == null || valueString.length() == 0) {
            saveSerializable(key, defaultValue);
            return defaultValue;
        }
        try {
            Object resultObject = fromString(valueString);
            return (T) resultObject;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

}