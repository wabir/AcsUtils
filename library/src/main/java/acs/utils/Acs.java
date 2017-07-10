package acs.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.TextView;

public class Acs{

    // Convert String to Int, return 0 if error
    public static int getInt(String str){
        try{
            return Integer.parseInt(str);
        } catch (Exception e){
            return 0;
        }
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
    public static void setFont(Context ctx, TextView tv, String font, int style){
        if(font != null && !font.isEmpty()){
            Typeface type = null;
            try{
                type = Typeface.createFromAsset(ctx.getAssets(), font);
            } catch (Exception e){
                Log.e("ACS", "Error al asignar fuente", e);
            }
            tv.setTypeface(type, style);
        }
    }
    public static void setFont(Context ctx, TextView tv, String font){
        setFont(ctx, tv, font, Typeface.NORMAL);
    }

}