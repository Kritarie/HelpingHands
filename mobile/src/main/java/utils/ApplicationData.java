package utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by allen on 2/28/15.
 */
public class ApplicationData {

    private static SharedPreferences sp;
    public static String HELP_WITH = "HELP_WITH_";
    public static String GET_HELP_WITH = "GET_HELP_WITH_";

    private static final String SP_KEY = "AwsickAppsSP";


    public static void setSharedPreferences(Context context){
        sp = context.getSharedPreferences(SP_KEY, Context.MODE_PRIVATE);
    }

    public static boolean update(String key, boolean value){
        return sp.edit().putBoolean(key, value).commit();
    }

    public static boolean isActive(String key){
        return sp.getBoolean(key, false);
    }

    public static int getInt(String key) {
        return sp.getInt(key, 0);
    }

    public static boolean update(String key, int value) {
        return sp.edit().putInt(key, value).commit();
    }
}
