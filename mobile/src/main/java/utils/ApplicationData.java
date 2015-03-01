package utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.awsickapps.helpinghands.R;

/**
 * Created by allen on 2/28/15.
 */
public class ApplicationData {

    private static SharedPreferences sp;
    public static String HELP_WITH = "HELP_WITH_";
    public static String GET_HELP_WITH = "GET_HELP_WITH_";
    public static String[] helpingHands;

    private static final String SP_KEY = "AwsickAppsSP";


    public static void setSharedPreferences(Context context){
        sp = context.getSharedPreferences(SP_KEY, Context.MODE_PRIVATE);
        helpingHands = context.getResources().getStringArray(R.array.helping_hands);
    }

    public static boolean update(String key, boolean value){
        return sp.edit().putBoolean(key, value).commit();
    }

    public static boolean isActive(String key){
        return sp.getBoolean(key, false);
    }

    public static int getImageAsset(String key) {

        /*
            0.<item>Heart Attack</item>
            1.<item>Stroke</item>
            2.<item>Allergic Reaction</item>
            3.<item>Glaucoma</item>
            5.<item>Azsthma Attack</item>
         */
        if (key.equals(helpingHands[0]))
            return R.drawable.heart_outline;
        else if (key.equals(helpingHands[1]))
            return R.drawable.account_remove;
        else if (key.equals(helpingHands[2]))
            return R.drawable.carrot;
        else if (key.equals(helpingHands[3]))
            return R.drawable.eyedropper;
        else
            return R.drawable.satellite_variant;
    }

    public static int getInt(String key) {
        return sp.getInt(key, 0);
    }

    public static boolean update(String key, int value) {
        return sp.edit().putInt(key, value).commit();
    }
}
