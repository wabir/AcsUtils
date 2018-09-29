package acs.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;

@SuppressLint("CommitPrefEdits")
abstract class AcsPref {

    public SharedPreferences sp;
    public Editor editor;

    public AcsPref(Context context, String pref_name) {
        this.sp = context.getSharedPreferences(pref_name, Context.MODE_PRIVATE);
        this.editor = sp.edit();
    }

    public String getString(String key) {
        return sp.getString(key, "");
    }

    public String getString(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    public int getInt(String key) {
        return sp.getInt(key, 0);
    }

    public int getInt(String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    public long getLong(String key) {
        return sp.getLong(key, 0);
    }

    public boolean getBoolean(String key) {
        return sp.getBoolean(key, false);
    }

    public void setString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public void setInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public void setLong(String key, long value) {
        editor.putLong(key, value);
        editor.commit();
    }

    public void setBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void remove(String key) {
        editor.remove(key);
        editor.commit();
    }

    private <T> void setModel(String key, T val) {
        if (val == null) {
            remove(key);
        } else {
            setString(key, new Gson().toJson(val));
        }
    }

    private <T> T getModel(String key, Class<T> classOfT, boolean get_null) {
        String str = getString(key);
        if (str.isEmpty()) {
            return get_null ? null : new Gson().fromJson("{}", classOfT);
        } else {
            return new Gson().fromJson(str, classOfT);
        }
    }

    private <T> T getModel(String key, Class<T> classOfT) {
        return getModel(key, classOfT, false);
    }

    public void setDouble(String key, double value) {
        setLong(key, Double.doubleToRawLongBits(value));
    }

    public double getDouble(String key) {
        return Double.longBitsToDouble(getLong(key));
    }

}