package acs.utils;

import android.content.Context;
import android.content.res.TypedArray;

public class Attr {

    public static int size(Context ctx, TypedArray a, int attr, int default_value) {
        return (int) a.getDimension(attr, Acs.px(ctx, default_value));
    }

    public static int sizeR(Context ctx, TypedArray a, int attr, int res_default_value) {
        return (int) a.getDimension(attr, ctx.getResources().getDimension(res_default_value));
    }

    public static int colorR(Context ctx, TypedArray a, int attr, int res_default_value) {
        return a.getColor(attr, ctx.getResources().getColor(res_default_value));
    }

}