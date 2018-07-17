package acs.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AcsToast{

    /**
     * Settings
     */
    public static class Settings{
        public boolean fast         = false;
        public int gravity          = Gravity.TOP;
        public int width            = -1; // MATCH_PARENT
        public int height           = -2; // WRAP_CONTENT
        public int bgColor          = Color.parseColor("#8BC34A");
        public int padding          = 20; // DP
        public int margin           = 0; // DP
        public int radius           = 0;
        public int elevation        = 0;
        public int borderWidth      = 0;
        public int borderColor      = 0;
        public boolean animated     = true;
        public int textColor        = Color.WHITE;
        public int textSize         = 16; // SP
        public boolean textBold     = true; // SP
        public int icon             = 0;
        public int iconSize         = 25;
        public int iconColor        = 0;
        public int iconMarginLeft   = 0;
        public int iconMarginRight  = 0;
        public String font          = "";
    }

    /**
     * Toast
     */
    public static void show(final Context ctx, String message, Settings stg){
        int padding = Acs.px(ctx, stg.padding);

        LinearLayout v = new LinearLayout(ctx);
        v.setGravity(Gravity.CENTER);

        LinearLayout root = new LinearLayout(ctx);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(stg.width, stg.height);
        root.setLayoutParams(lp);
        root.setPadding(padding,padding,padding,padding);
        root.setGravity(Gravity.CENTER);
        root.setClickable(true);

        if(stg.elevation > 0 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            root.setElevation(Acs.px(ctx, stg.elevation));
        }

        if(stg.margin > 0){
            int margin = Acs.px(ctx, stg.margin);
            lp.setMargins(margin,margin,margin,margin);
        }

        GradientDrawable bg = new GradientDrawable();
        bg.setColor(stg.bgColor);

        // Handle Radius
        if(stg.radius != 0)
            bg.setCornerRadius(Acs.px(ctx, stg.radius));

        // Handle Border
        if (stg.borderWidth != 0 && stg.borderColor != 0)
            bg.setStroke(Acs.px(ctx, stg.borderWidth), stg.borderColor);

        root.setBackgroundDrawable(bg);

        // Create icon if necessary
        if (stg.icon != 0){
            ImageView iconView = new ImageView(ctx);
            iconView.setImageResource(stg.icon);

            if(stg.iconColor != 0)
                iconView.setColorFilter(stg.iconColor);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Acs.px(ctx, stg.iconSize), Acs.px(ctx, stg.iconSize));
            layoutParams.gravity = Gravity.CENTER;
            layoutParams.leftMargin = Acs.px(ctx, stg.iconMarginLeft);
            layoutParams.rightMargin = Acs.px(ctx, stg.iconMarginRight);

            iconView.setLayoutParams(layoutParams);

            root.addView(iconView);
        }

        // Create TextView
        if(message != null){
            TextView titleView = new TextView(ctx);
            titleView.setText(message);
            titleView.setTextColor(stg.textColor);
            //titleView.setTypeface(null, stg.textBold ? Typeface.BOLD : Typeface.NORMAL);
            titleView.setTextSize(stg.textSize);

            Acs.setFont(ctx, titleView, stg.font, stg.textBold ? Typeface.BOLD : Typeface.NORMAL);

            root.addView(titleView);
        }

        if(stg.animated){
            if(stg.gravity == Gravity.CENTER){
                animFadeIn(root);
            } else if(stg.gravity == Gravity.BOTTOM){
                animShowDown(root);
            } else {
                animShowTop(root);
            }
        }

        v.addView(root);

        Toast toast = new Toast(ctx);

        toast.setGravity(Gravity.FILL_HORIZONTAL|stg.gravity, 0, 0);

        toast.setDuration(stg.fast ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG);
        toast.setView(v);
        toast.show();
    }

    public static void show(Context ctx, String message, int color){
        Settings stg = new Settings();
        stg.bgColor = color;
        show(ctx, message, stg);
    }

    public static void show(Context ctx, String message){
        show(ctx, message, new Settings());
    }

    private static void animShowTop(final View view){
        Animation slide = new TranslateAnimation(
                Animation.ABSOLUTE, 0,
                Animation.ABSOLUTE, 0,
                Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0f);
        slide.setDuration(300);
        slide.setFillAfter(true);
        slide.setFillEnabled(true);
        view.startAnimation(slide);
    }

    private static void animShowDown(final View view){
        Animation slide = new TranslateAnimation(
                Animation.ABSOLUTE, 0,
                Animation.ABSOLUTE, 0,
                Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_PARENT, 0f);
        slide.setDuration(300);
        slide.setFillAfter(true);
        slide.setFillEnabled(true);
        view.startAnimation(slide);
    }

    private static void animFadeIn(View view){
        Animation anim = new AlphaAnimation(0, 1);
        anim.setInterpolator(new DecelerateInterpolator()); //add this
        anim.setDuration(300);
        view.startAnimation(anim);
    }

}