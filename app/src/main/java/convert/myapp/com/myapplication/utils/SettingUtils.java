package convert.myapp.com.myapplication.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import convert.myapp.com.myapplication.application.MyApplication;

/**
 * Created by Mastra on 2017/12/2.
 */

public class SettingUtils {

    private SharedPreferences mSp = PreferenceManager.getDefaultSharedPreferences(MyApplication.appContext);
    private static final String NIGHT_MODE = "night_mode";

    private SettingUtils() {
    }

    public static SettingUtils getInstance() {
        return SettingUtilsInstance.instance;
    }

    private static final class SettingUtilsInstance {
        private static final SettingUtils instance = new SettingUtils();
    }

    public boolean isNightMode() {
        return mSp.getBoolean(NIGHT_MODE, false);
    }

    public void saveNightMode(boolean mode) {
        mSp.edit().putBoolean(NIGHT_MODE, mode).commit();
    }
}
