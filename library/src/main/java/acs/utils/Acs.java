package acs.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Acs {

    public static String DEFAULT_FONT = null;

    /**
     * Convertir String a Int, retorna 0 en caso de error
     */
    public static int getInt(String string, int default_value) {
        try {
            return Integer.parseInt(string);
        } catch (Exception e) {
            return default_value;
        }
    }

    public static int getInt(String string) {
        return getInt(string, 0);
    }

    /**
     * Convertir String a Float, retorna 0 en caso de error
     */
    public static float getFloat(String string, float default_value) {
        try {
            return Float.parseFloat(string);
        } catch (Exception e) {
            return default_value;
        }
    }

    public static float getFloat(String string) {
        return getFloat(string, 0);
    }

    // Convert PX to DP
    public static int dp(final Context ctx, final float px) {
        return Math.round(px / ctx.getResources().getDisplayMetrics().scaledDensity);
    }

    // Convert DP to PX
    public static int px(final Context ctx, final float dp) {
        return Math.round(dp * ctx.getResources().getDisplayMetrics().scaledDensity);
    }

    // Asignar tipo de fuente a un TextView
    public static void setFont(Context ctx, TextView tv, String font, int style) {
        if (font == null && DEFAULT_FONT != null) {
            font = DEFAULT_FONT;
        }

        Typeface type = null;
        if (font != null && !font.isEmpty()) {
            try {
                type = Typeface.createFromAsset(ctx.getAssets(), font);
            } catch (Exception e) {
                Log.e("ACS", "Error al cargar fuente", e);
            }
        }
        tv.setTypeface(type, style);
    }

    public static void setFont(Context ctx, TextView tv, String font) {
        setFont(ctx, tv, font, Typeface.NORMAL);
    }

    // fuente por defecto definida
    public static void setFont(Context ctx, TextView tv) {
        setFont(ctx, tv, DEFAULT_FONT);
    }

    /**
     * Copiar al portapapeles
     */
    public static void copyClipboard(Context ctx, String text) {
        ClipboardManager clipboard = (ClipboardManager) ctx.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", text);
        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
        }
    }

    /**
     * Mostrar teclado
     */
    public static void showKayboard(EditText et) {
        try {
            et.requestFocus();
            InputMethodManager imm = (InputMethodManager) et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Ocultar teclado
     */
    public static void hideKayboard(Activity ctx) {
        View view = ctx.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    /**
     * Verificar si es un email
     */
    public static boolean isEmail(String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    /**
     * Saber si una cadena esta vacia
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Saber si una cadena esta vacia
     */
    public static boolean equals(String str1, String str2) {
        return str1 != null && str1.equals(str2);
    }

    // tiempo en segundos
    public static long time() {
        return System.currentTimeMillis() / 1000;
    }


    /**
     * Obtener codigo ISO de pais
     */
    public static String getISO(Context ctx, String defaultValue) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        String iso = tm != null ? tm.getNetworkCountryIso() : "";
        return iso == null || iso.isEmpty() ? defaultValue : iso.toUpperCase();
    }

    public static String getISO(Context ctx) {
        return getISO(ctx, "US");
    }


    /**
     * Abrir PlayStore con un paquete
     */
    public static void openPlayStore(Context ctx, String app) {
        try {
            ctx.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + app)));
        } catch (android.content.ActivityNotFoundException anfe) {
            ctx.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + app)));
        }
    }

    public static void openPlayStore(Context ctx) {
        openPlayStore(ctx, ctx.getPackageName());
    }

    @ColorInt
    public static int alphaColor(@ColorInt int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }
}