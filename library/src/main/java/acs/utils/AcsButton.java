package acs.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AcsButton extends LinearLayout{

    private Context ctx;

    private boolean mCentered               = true;

    private int mBgColor                    = 0;
    private int mBgColorFocus               = 0;
    private int mBgColorDisabled            = 0;
    private int mTextSize                   = spToPx(15);
    private int mTextColor                  = Color.WHITE;
    private boolean mTextBold               = false;
    private boolean mTextAllCaps            = false;
    private boolean mTextSingleLine         = false;
    private String mText                    = "";

    private int mBorderColor 	            = 0;
    private int mBorderColorDisabled        = 0;
    private int mBorderWidth 	            = 0;
    private int mRadius 		            = 0;

    private int mLoadingSize                = spToPx(35);
    private int mLoadingColor 	            = mTextColor;

    private int mIcon                       = 0;
    private int mIconSize                   = spToPx(20);
    private int mIconColor                  = 0;
    private int mIconMarginLeft             = 0;
    private int mIconMarginRight            = 0;
    private int mIconMarginTop              = 0;
    private int mIconMarginBottom           = 0;
    private boolean mIconTop                = false;

    private ProgressBar mLoadingView;
    private ImageView mIconView;
    private TextView mTextView;

    private boolean useRippleEffect = true;

    public AcsButton(Context context){
        super(context);
        this.ctx = context;
        this.initAcsButton();
    }

    public AcsButton(Context context, AttributeSet attrs){
        super(context, attrs);
        this.ctx = context;
        this.initAttrs(attrs);
        this.initAcsButton();
    }

    private void initAttrs(AttributeSet attrs){
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.AcsButton);

        mCentered           = a.getBoolean(R.styleable.AcsButton_ab_centered, mCentered);

        mBgColor            = a.getColor(R.styleable.AcsButton_ab_bgColor, Color.parseColor("#37474F"));
        mBgColorFocus       = a.getColor(R.styleable.AcsButton_ab_bgColorFocus, Color.parseColor("#314047"));
        mBgColorDisabled    = a.getColor(R.styleable.AcsButton_ab_bgColorDisabled, mBgColorDisabled);

        mText               = a.getString(R.styleable.AcsButton_ab_text);
        mTextSize           = (int) a.getDimension(R.styleable.AcsButton_ab_textSize, mTextSize);
        mTextBold           = a.getBoolean(R.styleable.AcsButton_ab_textBold, mTextBold);
        mTextAllCaps        = a.getBoolean(R.styleable.AcsButton_ab_textAllCaps, mTextAllCaps);
        mTextColor          = a.getColor(R.styleable.AcsButton_ab_textColor, mTextColor);
        mTextSingleLine     = a.getBoolean(R.styleable.AcsButton_ab_textSingleLine, mTextSingleLine);

        mRadius             = (int) a.getDimension(R.styleable.AcsButton_ab_radius, mRadius);
        mBorderWidth        = (int) a.getDimension(R.styleable.AcsButton_ab_borderWidth, mBorderWidth);
        mBorderColor        = a.getColor(R.styleable.AcsButton_ab_borderColor, Color.BLACK);
        mBorderColorDisabled= a.getColor(R.styleable.AcsButton_ab_borderColorDisabled, mBorderColorDisabled);

        try{
            mIcon 	        = a.getResourceId(R.styleable.AcsButton_ab_icon, mIcon);
        } catch(Exception ignored){}

        mLoadingSize        = (int)a.getDimension(R.styleable.AcsButton_ab_loadingSize, mLoadingSize);
        mLoadingColor       = a.getColor(R.styleable.AcsButton_ab_loadingColor, mTextColor);

        mIconSize           = (int)a.getDimension(R.styleable.AcsButton_ab_iconSize, mIconSize);
        mIconColor          = a.getColor(R.styleable.AcsButton_ab_iconColor, mIconColor);
        mIconMarginLeft     = (int)a.getDimension(R.styleable.AcsButton_ab_iconMarginLeft, mIconMarginLeft);
        mIconMarginRight    = (int)a.getDimension(R.styleable.AcsButton_ab_iconMarginRight, mIconMarginRight);
        mIconMarginTop      = (int)a.getDimension(R.styleable.AcsButton_ab_iconMarginTop, mIconMarginTop);
        mIconMarginBottom   = (int)a.getDimension(R.styleable.AcsButton_ab_iconMarginBottom, mIconMarginBottom);
        mIconTop            = a.getBoolean(R.styleable.AcsButton_ab_iconTop, mIconTop);

        if(mTextAllCaps){
            mText           = mText.toUpperCase();
        }

        a.recycle();
    }

    private void initAcsButton(){

        if(mIconTop){
            this.setOrientation(VERTICAL);
        }

        if(mCentered){
            this.setGravity(Gravity.CENTER);
        } else {
            this.setGravity(Gravity.CENTER_VERTICAL);
        }

        //this.setClickable(true);
        //this.setFocusable(true);


        mLoadingView    = setupLoadingView();
        mIconView       = setupIconView();
        mTextView       = setupTextView();

        //setEnabled(false);

        setupBackground();


        this.addView(mLoadingView);

        if(mIconView != null){
            this.addView(mIconView);
        }

        if(mTextView != null){
            this.addView(mTextView);
        }
    }

    @Override
    public void setEnabled(boolean enabled){
        super.setEnabled(enabled);
        if(isEnabled()){
            if(mBgColorDisabled == 0){
                this.getBackground().setAlpha(255);
            } else {
                setupBackground();
            }
            if(mIconView != null){
                mIconView.setAlpha(1.0f);
            }
            if(mTextView != null){
                mTextView.setAlpha(1.0f);
            }
        } else {
            if(mBgColorDisabled == 0){
                this.getBackground().setAlpha(160);
            } else {
                setupBackground();
                //this.setBackgroundColor(mBgColorDisabled);
            }

            if(mIconView != null){
                mIconView.setAlpha(0.7f);
            }
            if(mTextView != null){
                mTextView.setAlpha(0.7f);
            }
        }
    }

    private void setupBackground(){
        /*// Default Drawable
        GradientDrawable defaultDrawable = new GradientDrawable();
        defaultDrawable.setCornerRadius(mRadius);
        defaultDrawable.setColor(mBgColor);

        //Focus Drawable
        GradientDrawable focusDrawable = new GradientDrawable();
        focusDrawable.setCornerRadius(mRadius);
        focusDrawable.setColor(mBgColorFocus);

        // Handle Border
        if (mBorderColor != 0) {
            defaultDrawable.setStroke(mBorderWidth, mBorderColor);
        }

        if (useRippleEffect && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.setBackground(getRippleDrawable(defaultDrawable, focusDrawable));
        } else {
            StateListDrawable states = new StateListDrawable();

            // Focus/Pressed Drawable
            GradientDrawable drawable2 = new GradientDrawable();
            drawable2.setCornerRadius(mRadius);
            drawable2.setColor(mBgColorFocus);

            // Handle Button Border
            if (mBorderColor != 0) {
                drawable2.setStroke(mBorderWidth, mBorderColor);
            }

            if(mBgColorFocus != 0){
                states.addState(new int[] { android.R.attr.state_pressed}, drawable2);
                states.addState(new int[] { android.R.attr.state_focused}, drawable2);
            }

            states.addState(new int[]{}, defaultDrawable);


            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                this.setBackgroundDrawable(states);
            } else {
                this.setBackground(states);
            }
        }*/


        // Default Drawable
        GradientDrawable defaultDrawable = new GradientDrawable();
        defaultDrawable.setCornerRadius(mRadius);
        defaultDrawable.setColor(mBgColor);

        //Focus Drawable
        GradientDrawable focusDrawable = new GradientDrawable();
        focusDrawable.setCornerRadius(mRadius);
        focusDrawable.setColor(mBgColorFocus);

        // Disabled Drawable
        GradientDrawable disabledDrawable = new GradientDrawable();
        disabledDrawable.setCornerRadius(mRadius);
        disabledDrawable.setColor(mBgColorDisabled);
        disabledDrawable.setStroke(mBorderWidth, mBorderColorDisabled);

        // Handle Border
        if (mBorderColor != 0) {
            defaultDrawable.setStroke(mBorderWidth, mBorderColor);
        }

        // Handle disabled border color
        if (!isEnabled()){
            defaultDrawable.setStroke(mBorderWidth, mBorderColorDisabled);
        }


        if (useRippleEffect && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            this.setBackground(getRippleDrawable(defaultDrawable, focusDrawable, disabledDrawable));

        } else {

            StateListDrawable states = new StateListDrawable();

            // Focus/Pressed Drawable
            GradientDrawable drawable2 = new GradientDrawable();
            drawable2.setCornerRadius(mRadius);
            drawable2.setColor(mBgColorFocus);

            // Handle Button Border
            if(mBorderColor != 0){
                drawable2.setStroke(mBorderWidth, mBorderColor);
            }

            if(!isEnabled()){
                drawable2.setStroke(mBorderWidth, mBorderColorDisabled);
            }

            if(mBgColorFocus != 0){
                states.addState(new int[] { android.R.attr.state_pressed}, drawable2);
                states.addState(new int[] { android.R.attr.state_focused}, drawable2);
                states.addState(new int[]{ -android.R.attr.state_enabled }, disabledDrawable);
            }

            states.addState(new int[]{}, defaultDrawable);


            if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                this.setBackgroundDrawable(states);
            } else {
                this.setBackground(states);
            }

        }
    }

    private ProgressBar setupLoadingView(){
        ProgressBar pb = new ProgressBar(ctx);
        pb.setVisibility(View.GONE);
        pb.getIndeterminateDrawable().setColorFilter(mLoadingColor, PorterDuff.Mode.SRC_ATOP);


        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        //layoutParams.gravity = Gravity.CENTER;
        layoutParams.width = mLoadingSize;
        layoutParams.height = mLoadingSize;

        pb.setLayoutParams(layoutParams);
        return pb;
    }

    private ImageView setupIconView(){
        if (mIcon != 0){
            ImageView iconView = new ImageView(ctx);
            iconView.setImageResource(mIcon);

            if(mIconColor != 0)
                iconView.setColorFilter(mIconColor);

            LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
            //layoutParams.gravity = Gravity.CENTER;
            layoutParams.width = mIconSize;
            layoutParams.height = mIconSize;
            layoutParams.leftMargin = mIconMarginLeft;
            layoutParams.rightMargin = mIconMarginRight;
            layoutParams.topMargin = mIconMarginTop;
            layoutParams.bottomMargin = mIconMarginBottom;

            iconView.setLayoutParams(layoutParams);

            return iconView;
        }
        return null;
    }

    private TextView setupTextView(){
        if(mText == null) return null;

        TextView textView = new TextView(ctx);
        textView.setText(mText);
        textView.setTextColor(mTextColor);
        textView.setTextSize(pxToSp(mTextSize));
        textView.setTypeface(null, mTextBold ? Typeface.BOLD : Typeface.NORMAL);
        textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        if(mTextSingleLine){
            textView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setSingleLine();
        }
        return textView;
    }


    /*@TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private Drawable getRippleDrawable(Drawable defaultDrawable, Drawable focusDrawable){
        return new RippleDrawable(ColorStateList.valueOf(mBgColorFocus), defaultDrawable, focusDrawable);
    }*/

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private Drawable getRippleDrawable(Drawable defaultDrawable, Drawable focusDrawable, Drawable disabledDrawable){
        if (!isEnabled()){
            return disabledDrawable;
        } else {
            return new RippleDrawable(ColorStateList.valueOf(mBgColorFocus), defaultDrawable, focusDrawable);
        }
    }


    /**
     * Acciones
     */
    public void setLoading(){
        setEnabled(false);
        if(mIconView != null){
            mIconView.setVisibility(GONE);
        }
        if(mTextView != null){
            mTextView.setVisibility(GONE);
        }
        mLoadingView.setVisibility(VISIBLE);
    }
    public void hideLoading(){
        setEnabled(true);
        if(mIconView != null){
            mIconView.setVisibility(VISIBLE);
        }
        if(mTextView != null){
            mTextView.setVisibility(VISIBLE);
        }
        mLoadingView.setVisibility(GONE);
    }

    /**
     * Asignar valores
     */
    // Fondo Normal
    public void setBgColor(int color){
        mBgColor = color;
        setupBackground();
    }
    public void setBgColorDisabled(int color){
        mBgColorDisabled = color;
        setupBackground();
    }
    // Border
    public void setBorderColor(int color){
        mBorderColor = color;
        setupBackground();
    }
    public void setBorderColorDisabled(int color){
        mBorderColorDisabled = color;
        setupBackground();
    }

    // Icon
    public void setIconSize(int size){
        mIconSize = spToPx(size);
        if(mIconView != null){
            mIconView.getLayoutParams().width  = mIconSize;
            mIconView.getLayoutParams().height = mIconSize;
        }
    }
    public void setIconVisible(boolean visible){
        if(mIconView != null){
            mIconView.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    // Texto
    public void setTextSize(int size){
        mTextSize = spToPx(size);
        if(mTextView != null){
            mTextView.setTextSize(mTextSize);
        }
    }
    public void setTextVisible(boolean visible){
        if(mTextView != null){
            mTextView.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    public void setText(String text){
        mText = mTextAllCaps ? text.toUpperCase() : text;
        if(mTextView != null){
            mTextView.setText(mText);
        }
    }
    public void setText(int res_text){
        setText(ctx.getString(res_text));
    }

    public void setTextColor(@ColorInt int color){
        mTextColor = color;
        if(mTextView != null){
            mTextView.setTextColor(mTextColor);
        }
    }
    public void setTextColorRes(@ColorRes int res_color){
        setTextColor(ctx.getResources().getColor(res_color));
    }

    // Icono
    public void setIcon(int res_icon){
        if(mIconView != null){
            mIconView.setImageResource(res_icon);
        }
    }
    public void setIconColor(@ColorInt int color){
        mIconColor = color;
        if(mIconView != null){
            mIconView.setColorFilter(mIconColor);
        }
    }
    public void setIconColorRes(@ColorRes int res_color){
        setIconColor(ctx.getResources().getColor(res_color));
    }

    // Foreground color: icon, text
    public void setFgColor(@ColorInt int color){
        setTextColor(color);
        setIconColor(color);
    }
    public void setFgColorRes(@ColorRes int res_color){
        setFgColor(ctx.getResources().getColor(res_color));
    }

    /**
     * Utils
     */
    public int pxToSp(final float px) {
        return Math.round(px / getContext().getResources().getDisplayMetrics().scaledDensity);
    }

    public int spToPx(final float sp) {
        return Math.round(sp * getContext().getResources().getDisplayMetrics().scaledDensity);
    }
}