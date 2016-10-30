package acs.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AcsButton extends LinearLayout{

    private Context ctx;

    private int mBgColor                    = 0;
    private int mBgColorFocus               = 0;
    private int mTextSize                   = spToPx(15);
    private int mTextColor                  = Color.WHITE;
    private boolean mTextBold               = false;
    private boolean mTextAllCaps            = false;
    private String mText                    = "";

    private int mBorderColor 	            = Color.BLACK;
    private int mBorderWidth 	            = 0;
    private int mRadius 		            = 0;

    private int mLoadingSize                = spToPx(35);

    private Drawable mIcon                  = null;
    private int mIconSize                   = spToPx(20);
    private int mIconMarginLeft            = 0;
    private int mIconMarginRight           = 0;
    private int mIconMarginTop             = 0;
    private int mIconMarginBottom          = 0;

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

        mBgColor        = a.getColor(R.styleable.AcsButton_ab_bgColor, Color.parseColor("#37474F"));
        mBgColorFocus   = a.getColor(R.styleable.AcsButton_ab_bgColorFocus, Color.parseColor("#314047"));

        mText           = a.getString(R.styleable.AcsButton_ab_text);
        mTextSize       = (int) a.getDimension(R.styleable.AcsButton_ab_textSize, mTextSize);
        mTextBold       = a.getBoolean(R.styleable.AcsButton_ab_textBold, mTextBold);
        mTextAllCaps    = a.getBoolean(R.styleable.AcsButton_ab_textAllCaps, mTextAllCaps);
        mRadius         = (int) a.getDimension(R.styleable.AcsButton_ab_radius, mRadius);
        mBorderWidth    = (int) a.getDimension(R.styleable.AcsButton_ab_borderWidth, mBorderWidth);
        mBorderColor    = a.getColor(R.styleable.AcsButton_ab_borderColor, Color.BLACK);

        try{
            mIcon 	    = a.getDrawable(R.styleable.AcsButton_ab_icon);
        } catch(Exception ignored){}

        mLoadingSize    = (int)a.getDimension(R.styleable.AcsButton_ab_loadingSize, mLoadingSize);

        mIconSize           = (int)a.getDimension(R.styleable.AcsButton_ab_iconSize, mIconSize);
        mIconMarginLeft    = (int)a.getDimension(R.styleable.AcsButton_ab_iconMarginLeft, mIconMarginLeft);
        mIconMarginRight   = (int)a.getDimension(R.styleable.AcsButton_ab_iconMarginRight, mIconMarginRight);
        mIconMarginTop     = (int)a.getDimension(R.styleable.AcsButton_ab_iconMarginTop, mIconMarginTop);
        mIconMarginBottom  = (int)a.getDimension(R.styleable.AcsButton_ab_iconMarginBottom, mIconMarginBottom);

        if(mTextAllCaps){
            mText           = mText.toUpperCase();
        }

        a.recycle();
    }

    private void initAcsButton(){

        this.setGravity(Gravity.CENTER);
        this.setClickable(true);
        this.setFocusable(true);


        mLoadingView    = setupLoadingView();
        mIconView       = setupIconView();
        mTextView       = setupTextView();

        //setEnabled(false);

        setupBackground();


        this.addView(mLoadingView);

        if(mIconView != null){
            this.addView(mIconView);
        }

        this.addView(mTextView);
    }

    @Override
    public void setEnabled(boolean enabled){
        super.setEnabled(enabled);

        if(isEnabled()){
            this.setAlpha(1.0f);
            mTextView.setAlpha(1.0f);
        } else {
            this.setAlpha(0.8f);
            mTextView.setAlpha(0.8f);
        }
    }

    private void setupBackground(){

        // Default Drawable
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

        }

    }

    private ProgressBar setupLoadingView(){
        ProgressBar pb = new ProgressBar(ctx);
        pb.setVisibility(View.GONE);
        pb.getIndeterminateDrawable().setColorFilter(mTextColor, PorterDuff.Mode.SRC_ATOP);


        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.width = mLoadingSize;
        layoutParams.height = mLoadingSize;

        pb.setLayoutParams(layoutParams);
        return pb;
    }

    private ImageView setupIconView(){
        if (mIcon != null){
            ImageView iconView = new ImageView(ctx);
            iconView.setImageDrawable(mIcon);

            LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER;
            layoutParams.width = mIconSize;
            layoutParams.height = mIconSize;
            layoutParams.leftMargin = mIconMarginLeft;
            layoutParams.rightMargin = mIconMarginRight;

            iconView.setLayoutParams(layoutParams);

            return iconView;
        }
        return null;
    }

    private TextView setupTextView(){
        TextView textView = new TextView(ctx);
        textView.setText(mText);
        textView.setTextColor(mTextColor);
        textView.setTextSize(pxToSp(mTextSize));
        textView.setTypeface(null, mTextBold ? Typeface.BOLD : Typeface.NORMAL);
        textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        return textView;
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private Drawable getRippleDrawable(Drawable defaultDrawable, Drawable focusDrawable){
        return new RippleDrawable(ColorStateList.valueOf(mBgColorFocus), defaultDrawable, focusDrawable);
    }

    /**
     * Acciones
     */
    public void setLoading(){
        setEnabled(false);
        mTextView.setVisibility(GONE);
        if(mIconView != null){
            mIconView.setVisibility(GONE);
        }
        mLoadingView.setVisibility(VISIBLE);
    }
    public void hideLoading(){
        setEnabled(true);
        mTextView.setVisibility(VISIBLE);
        if(mIconView != null){
            mIconView.setVisibility(VISIBLE);
        }
        mLoadingView.setVisibility(GONE);
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