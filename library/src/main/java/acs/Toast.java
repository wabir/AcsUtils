package acs;

import android.content.Context;
import android.support.annotation.StringRes;

import acs.utils.AcsToast;
import acs.utils.R;

public class Toast {

    public static void success(Context ctx, String text) {
        AcsToast.show(ctx, text, ctx.getResources().getColor(R.color._success));
    }

    public static void success(Context ctx, @StringRes int res_text, Object... args) {
        AcsToast.show(ctx, ctx.getString(res_text, args), ctx.getResources().getColor(R.color._success));
    }

    public static void danger(Context ctx, String text) {
        AcsToast.show(ctx, text, ctx.getResources().getColor(R.color._danger));
    }

    public static void danger(Context ctx, @StringRes int res_text, Object... args) {
        AcsToast.show(ctx, ctx.getString(res_text, args), ctx.getResources().getColor(R.color._danger));
    }

    public static void warning(Context ctx, String text) {
        AcsToast.show(ctx, text, ctx.getResources().getColor(R.color._warning));
    }

    public static void warning(Context ctx, @StringRes int res_text, Object... args) {
        AcsToast.show(ctx, ctx.getString(res_text, args), ctx.getResources().getColor(R.color._warning));
    }

    public static void info(Context ctx, String text) {
        AcsToast.show(ctx, text, ctx.getResources().getColor(R.color._info));
    }

    public static void info(Context ctx, @StringRes int res_text, Object... args) {
        AcsToast.show(ctx, ctx.getString(res_text, args), ctx.getResources().getColor(R.color._info));
    }

    public static void metal(Context ctx, String text) {
        AcsToast.show(ctx, text, ctx.getResources().getColor(R.color._metal));
    }

    public static void metal(Context ctx, @StringRes int res_text, Object... args) {
        AcsToast.show(ctx, ctx.getString(res_text, args), ctx.getResources().getColor(R.color._metal));
    }

}