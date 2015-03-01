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

    private static final String PROPERTY_REG_ID = "registration_id";


    public static void setSharedPreferences(Context context){
        sp = context.getSharedPreferences(SP_KEY, Context.MODE_PRIVATE);
    }

    public static boolean update(String key, boolean value){
        return sp.edit().putBoolean(key, value).commit();
    }

    public static boolean isActive(String key){
        return sp.getBoolean(key, false);
    }

    public static String getRegId() {
        return sp.getString(PROPERTY_REG_ID, "");
    }

    public static boolean setRegId(String id) {
        return sp.edit().putString(PROPERTY_REG_ID, id).commit();
    }
}
