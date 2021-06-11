package com.yin.myapplication.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class UserDBManager {
    // 싱글턴
    private static SharedPreferences sharedPref = null;
    private static SharedPreferences.Editor editor = null;

    public UserDBManager(Context context) {
        if (sharedPref==null) {
            sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
            editor = sharedPref.edit();
        }
    }

    public static String getUser_id() {
        return sharedPref.getString("user_id", "test");
    }

    public static void setUser_id(String user_id) {
        editor.putString("user_id", user_id);
        editor.commit();
    }

    public static String getUser_pw() {
        return sharedPref.getString("user_pw", "");
    }

    public static void setUser_pw(String user_pw) {
        editor.putString("user_pw", user_pw);
        editor.commit();
    }

    public static String getPhone() {
        return sharedPref.getString("phone", "");
    }

    public static void setPhone(String phone) {
        editor.putString("phone", phone);
        editor.commit();
    }
}
