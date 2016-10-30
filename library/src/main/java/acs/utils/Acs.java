package acs.utils;

import android.content.Context;

public class Acs{

    public static int pxToSp(final Context ctx, final float px) {
        return Math.round(px / ctx.getResources().getDisplayMetrics().scaledDensity);
    }

    public static int spToPx(final Context ctx, final float sp) {
        return Math.round(sp * ctx.getResources().getDisplayMetrics().scaledDensity);
    }

}