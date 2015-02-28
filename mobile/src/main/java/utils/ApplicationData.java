package utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by allen on 2/28/15.
 */
public class ApplicationData {

    private SharedPreferences sp;

    private static final String SP_KEY = "AwsickAppsSP";
    private static final String HELP_KEY = "helpsWith";
    private static final String GET_HELP_KEY = "needsHelpWith";

    private HashSet<String> helpsWith;
    private HashSet<String> needsHelpWith;



    public void setSharedPreferences(Context context){
        sp = context.getSharedPreferences(SP_KEY, Context.MODE_PRIVATE);
        helpsWith       = (HashSet<String>) sp.getStringSet(HELP_KEY, null);
        needsHelpWith   = (HashSet<String>) sp.getStringSet(GET_HELP_KEY, null);

    }

    public void updateHelpsWith(HashSet<String> helpsWith){
        this.helpsWith = helpsWith;
    }

    public void updateNeedHelpWith(HashSet<String> needsHelpWith){
        this.needsHelpWith = needsHelpWith;
    }

    public HashSet<String> getHelpsWith(){
        if (helpsWith == null)
            helpsWith = (HashSet<String>) sp.getStringSet(HELP_KEY, null);

        return helpsWith;
    }

    public HashSet<String> getNeedsHelpWith(){
        if(needsHelpWith == null)
            needsHelpWith = (HashSet<String>) sp.getStringSet(GET_HELP_KEY, null);

        return needsHelpWith;
    }



}
