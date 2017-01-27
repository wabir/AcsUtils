package acs.utils;

import android.content.Context;

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

}