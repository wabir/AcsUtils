package acs.utils;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AcsLock extends RelativeLayout implements View.OnTouchListener {

    private static final String TAG = "ACS";

    private Context ctx;

    private int mRadius                 = 0;
    private int mBgColorOff             = Color.BLUE;
    private int mBgColorOn              = 0;

    private int mBorderWidth            = 0;
    private int mBorderColorOff         = 0;
    private int mBorderColorOn          = 0;

    private int mThumbWidth             = LayoutParams.WRAP_CONTENT;
    private int mThumbRadius            = 0;
    private int mThumbBgColorOff        = Color.GREEN;
    private int mThumbBgColorOn         = Color.RED;
    private String mThumbTextOff        = null;
    private String mThumbTextOn         = null;
    private boolean mThumbTextAllCaps   = true;
    private int mThumbTextSize          = toPX(14);
    private int mThumbTextColorOff      = Color.WHITE;
    private int mThumbTextColorOn       = Color.WHITE;
    private int mThumbIconOff           = 0; // Icon
    private int mThumbIconOn            = 0;
    private int mThumbIconSize          = toPX(15);
    private int mThumbIconSizeOn        = 0;
    private int mThumbIconColorOff      = 0;
    private int mThumbIconColorOn       = 0;
    private int mThumbIconMrgLeftOff    = 0;
    private int mThumbIconMrgLeftOn     = 0;
    private int mThumbIconMrgRightOff   = 0;
    private int mThumbIconMrgRightOn    = 0;
    private int mThumbBorderWidth       = 0; // Border
    private int mThumbBorderColorOn     = 0;
    private int mThumbBorderColorOff    = 0;
    private int mThumbPaddingLeft       = 0; // Padding
    private int mThumbPaddingTop        = 0;
    private int mThumbPaddingRight      = 0;
    private int mThumbPaddingBottom     = 0;

    private int mTolerance              = 0; // Tolerancia Slide

    // Views
    private LinearLayout mThumbView;
    private TextView mThumbTextView;
    private ImageView mThumbIconView;

    GradientDrawable mGD;

    private boolean isOn            = false;
    boolean sliding = false;
    private int sliderPosition = 0;

    private int width = 0; // Ancho del marco
    private int thumbWidth = 0;     // Ancho del boton Deslizable


    private OnLock listener = null;

    public AcsLock(Context context){
        super(context);
        this.ctx = context;
        this.initAcsLock();
    }

    public AcsLock(Context context, AttributeSet attrs){
        super(context, attrs);
        this.ctx = context;
        this.initAttrs(attrs);
        this.initAcsLock();
    }

    private void initAttrs(AttributeSet attrs){
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.AcsLock);

        mRadius                 = (int) a.getDimension(R.styleable.AcsLock_al_radius, mRadius);

        mBgColorOff             = a.getColor(R.styleable.AcsLock_al_bgColorOff, mBgColorOff);
        mBgColorOn              = a.getColor(R.styleable.AcsLock_al_bgColorOn, mBgColorOff);

        mBorderWidth            = (int) a.getDimension(R.styleable.AcsLock_al_borderWidth, mBorderWidth);
        mBorderColorOff         = a.getColor(R.styleable.AcsLock_al_borderColorOff, mBorderColorOff);
        mBorderColorOn          = a.getColor(R.styleable.AcsLock_al_borderColorOn, mBorderColorOn);

        mThumbWidth             = (int) a.getDimension(R.styleable.AcsLock_al_thumbWidth, mThumbWidth);

        mThumbRadius            = (int) a.getDimension(R.styleable.AcsLock_al_thumbRadius, mThumbRadius);

        mThumbBgColorOff        = a.getColor(R.styleable.AcsLock_al_thumbBgColorOff, mThumbBgColorOff);
        mThumbBgColorOn         = a.getColor(R.styleable.AcsLock_al_thumbBgColorOn, mThumbBgColorOff);

        mThumbTextOff           = a.getString(R.styleable.AcsLock_al_thumbTextOff);
        mThumbTextOn            = a.getString(R.styleable.AcsLock_al_thumbTextOn);
        mThumbTextAllCaps       = a.getBoolean(R.styleable.AcsLock_al_thumbTextAllCaps, mThumbTextAllCaps);
        mThumbTextSize          = (int) a.getDimension(R.styleable.AcsLock_al_thumbTextSize, mThumbTextSize);

        mThumbTextColorOff      = a.getColor(R.styleable.AcsLock_al_thumbTextColorOff, mThumbTextColorOff);
        mThumbTextColorOn       = a.getColor(R.styleable.AcsLock_al_thumbTextColorOn, mThumbTextColorOff);


        mThumbIconOff           = a.getResourceId(R.styleable.AcsLock_al_thumbIconOff, mThumbIconOff);
        mThumbIconOn            = a.getResourceId(R.styleable.AcsLock_al_thumbIconOn, mThumbIconOff);
        mThumbIconSize          = (int) a.getDimension(R.styleable.AcsLock_al_thumbIconSize, mThumbIconSize);
        mThumbIconSizeOn        = (int) a.getDimension(R.styleable.AcsLock_al_thumbIconSizeOn, mThumbIconSize);
        mThumbIconColorOff      = a.getColor(R.styleable.AcsLock_al_thumbIconColorOff, mThumbIconColorOff);
        mThumbIconColorOn       = a.getColor(R.styleable.AcsLock_al_thumbIconColorOn, mThumbIconColorOn);
        mThumbIconMrgLeftOff    = (int) a.getDimension(R.styleable.AcsLock_al_thumbIconMrgLeftOff, mThumbIconMrgLeftOff);
        mThumbIconMrgLeftOn     = (int) a.getDimension(R.styleable.AcsLock_al_thumbIconMrgLeftOn, mThumbIconMrgLeftOn);
        mThumbIconMrgRightOff   = (int) a.getDimension(R.styleable.AcsLock_al_thumbIconMrgRightOff, mThumbIconMrgRightOff);
        mThumbIconMrgRightOn    = (int) a.getDimension(R.styleable.AcsLock_al_thumbIconMrgRightOn, mThumbIconMrgRightOn);

        mThumbBorderWidth       = (int) a.getDimension(R.styleable.AcsLock_al_thumbBorderWidth, mThumbBorderWidth);
        mThumbBorderColorOff    = a.getColor(R.styleable.AcsLock_al_thumbBorderColorOff, mThumbBorderColorOff);
        mThumbBorderColorOn     = a.getColor(R.styleable.AcsLock_al_thumbBorderColorOn, mThumbBorderColorOn);

        mThumbPaddingLeft       = (int) a.getDimension(R.styleable.AcsLock_al_thumbPaddingLeft, mThumbPaddingLeft);
        mThumbPaddingTop        = (int) a.getDimension(R.styleable.AcsLock_al_thumbPaddingTop, mThumbPaddingTop);
        mThumbPaddingRight      = (int) a.getDimension(R.styleable.AcsLock_al_thumbPaddingRight, mThumbPaddingRight);
        mThumbPaddingBottom     = (int) a.getDimension(R.styleable.AcsLock_al_thumbPaddingBottom, mThumbPaddingBottom);

        mTolerance              = (int) a.getDimension(R.styleable.AcsLock_al_tolerance, mTolerance);

        if(mThumbTextOff != null && mThumbTextOn == null)
            mThumbTextOn = mThumbTextOff;

        if(mThumbTextAllCaps){
            if(mThumbTextOff != null) mThumbTextOff = mThumbTextOff.toUpperCase();
            if(mThumbTextOn  != null) mThumbTextOn  = mThumbTextOn.toUpperCase();
        }

        a.recycle();
    }

    private void initAcsLock(){
        mThumbView = setupThumbView();

        addView(mThumbView);

        thumbWidth = mThumbView.getWidth();

        Log.i(TAG, "thumbWidth:"+thumbWidth);

        setupBackground();
        setupBackgroundThumb();

        getThumbWidth(null);
    }

    private void setupBackground(){
        // Default Drawable
        mGD = new GradientDrawable();
        mGD.setCornerRadius(mRadius);
        mGD.setColor(isOn ? mBgColorOn : mBgColorOff);

        // Handle Border
        if (isOn && mBorderColorOn != 0) {
            mGD.setStroke(mBorderWidth, mBorderColorOn);
        } else if(mBorderColorOff != 0){
            mGD.setStroke(mBorderWidth, mBorderColorOff);
        }

        this.setBackgroundDrawable(mGD);
    }

    /**
     * Animar color de fondo
     */
    private void animateBackgroundColor(int colorFrom, int colorTo){
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
            @Override public void onAnimationUpdate(ValueAnimator animator){
                mGD.setColor((int) animator.getAnimatedValue());
            }
        });
        colorAnimation.setDuration(300);
        colorAnimation.start();
    }

    private void setupBackgroundThumb(){
        // Default Drawable
        GradientDrawable defaultDrawable = new GradientDrawable();
        defaultDrawable.setCornerRadius(mThumbRadius > 0 ? mThumbRadius : mRadius);
        defaultDrawable.setColor(isOn ? mThumbBgColorOn : mThumbBgColorOff);

        // Handle Border
        if (isOn && mThumbBorderColorOn != 0) {
            defaultDrawable.setStroke(mThumbBorderWidth, mThumbBorderColorOn);
        } else if(mThumbBorderColorOff != 0){
            defaultDrawable.setStroke(mThumbBorderWidth, mThumbBorderColorOff);
        }

        mThumbView.setBackgroundDrawable(defaultDrawable);
    }

    private LinearLayout setupThumbView(){
        mThumbTextView = setupThumbTextView();
        mThumbIconView = setupThumbIconView();

        final LinearLayout thumb = new LinearLayout(ctx);
        thumb.setBackgroundColor(Color.GREEN);
        thumb.setPadding(mThumbPaddingLeft, mThumbPaddingTop, mThumbPaddingRight, mThumbPaddingBottom);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mThumbWidth, LayoutParams.MATCH_PARENT);

        thumb.setLayoutParams(layoutParams);
        thumb.setGravity(Gravity.CENTER);
        if(mThumbTextView != null) thumb.addView(mThumbTextView);
        if(mThumbIconView != null) thumb.addView(mThumbIconView);
        thumb.setOnTouchListener(this);
        return thumb;
    }

    private TextView setupThumbTextView(){
        if(mThumbTextOff == null) return null;
        TextView textView = new TextView(ctx);
        textView.setText(mThumbTextOff);
        textView.setTextColor(mThumbTextColorOff);
        textView.setLines(1);
        textView.setTextSize(toDP(mThumbTextSize));
        return textView;
    }

    private ImageView setupThumbIconView(){
        if(mThumbIconOff == 0) return null;

        ImageView iconView = new ImageView(ctx);
        iconView.setImageResource(mThumbIconOff);

        if(mThumbIconColorOff != 0)
            iconView.setColorFilter(mThumbIconColorOff);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //layoutParams.gravity = Gravity.CENTER;
        layoutParams.width = mThumbIconSize;
        layoutParams.height = mThumbIconSize;
        layoutParams.leftMargin = mThumbIconMrgLeftOff;
        layoutParams.rightMargin = mThumbIconMrgRightOff;
        layoutParams.topMargin = 0;
        layoutParams.bottomMargin = 0;

        iconView.setLayoutParams(layoutParams);

        return iconView;
    }

    private void getThumbWidth(final OnEnd callback){
        mThumbView.measure(0,0);
        thumbWidth = mThumbWidth <= 0 ? mThumbView.getMeasuredWidth() : mThumbWidth;
        Log.i(TAG, "thumbWidth:"+thumbWidth);
        /*mThumbView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){
            @Override public void onGlobalLayout(){
                thumbWidth = mThumbView.getWidth();
                Log.i(TAG, "newWidth: "+thumbWidth);
                if(callback != null){
                    callback.onEnd();
                }
                mThumbView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });*/
    }
    private interface OnEnd{
        void onEnd();
    }







    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld){
        super.onSizeChanged(xNew, yNew, xOld, yOld);
        //Log.i(TAG, String.format("onSizeChanged():: width = %s xNew = %s yNew = %s xOld = %s yOld = %s", getWidth(), xNew, yNew, xOld, yOld));
        width = (xNew - (getPaddingLeft() + getPaddingRight()));
        setState(isOn);
        //Log.i(TAG, "isEnabled() = " + isEnabled());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event){
        super.onTouchEvent(event);
        if(!isEnabled()) return false;
        final int X = (int) event.getRawX();
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                sliding = true;
                break;
            case MotionEvent.ACTION_UP:
                sliding = false;
                if(!isOn){
                    if((width-sliderPosition-thumbWidth) <= mTolerance){
                        setOn(true);
                    } else {
                        animateSlider(true);
                    }
                } else {
                    if(sliderPosition <= mTolerance){
                        setOff(true);
                    } else {
                        animateSlider(false);
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(!sliding) break;

                sliderPosition = (X - thumbWidth);
                if (sliderPosition <= 0) sliderPosition = 0;
                if (sliderPosition >= (width - thumbWidth)) {
                    sliderPosition = (width - thumbWidth);
                }

                setMarginLeft(sliderPosition);
                break;
        }

        return true;
    }

    /**
     * Asignar Positivo
     */
    private void setOn(boolean send_ballback){
        isOn = true;
        if(mThumbTextView != null){
            mThumbTextView.setText(mThumbTextOn);
            mThumbTextView.setTextColor(mThumbTextColorOn);
        }
        if(mThumbIconView != null){
            mThumbIconView.setImageResource(mThumbIconOn);
            if(mThumbIconColorOn != 0) mThumbIconView.setColorFilter(mThumbIconColorOn);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mThumbIconView.getLayoutParams();
            params.width = mThumbIconSizeOn;
            params.height = mThumbIconSizeOn;
            params.leftMargin = mThumbIconMrgLeftOn;
            params.rightMargin = mThumbIconMrgRightOn;
        }

        if(mThumbTextView != null && mThumbIconView != null){
            mThumbView.removeAllViews();
            mThumbView.addView(mThumbIconView);
            mThumbView.addView(mThumbTextView);
        }

        setupBackground();
        setupBackgroundThumb();
        animateSlider(false);

        animateBackgroundColor(mBgColorOff, mBgColorOn);
        if(send_ballback && listener != null) listener.onLock(this, true);
    }
    public void setOn(){
        setOn(false);
    }

    /**
     * Asignar Negativo
     */
    private void setOff(boolean send_ballback){
        isOn = false;
        if(mThumbTextView != null){
            mThumbTextView.setText(mThumbTextOff);
            mThumbTextView.setTextColor(mThumbTextColorOff);
        }
        if(mThumbIconView != null){
            mThumbIconView.setImageResource(mThumbIconOff);
            if(mThumbIconColorOff != 0) mThumbIconView.setColorFilter(mThumbIconColorOff);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mThumbIconView.getLayoutParams();
            params.width = mThumbIconSize;
            params.height = mThumbIconSize;
            params.leftMargin = mThumbIconMrgLeftOff;
            params.rightMargin = mThumbIconMrgRightOff;
        }

        if(mThumbTextView != null && mThumbIconView != null){
            mThumbView.removeAllViews();
            mThumbView.addView(mThumbTextView);
            mThumbView.addView(mThumbIconView);
        }

        setupBackground();
        setupBackgroundThumb();
        animateSlider(true);
        animateBackgroundColor(mBgColorOn, mBgColorOff);
        if(send_ballback && listener != null) listener.onLock(this, false);
    }
    public void setOff(){
        setOff(false);
    }

    /**
     * Asignar On -o- Off
     */
    public void setState(boolean state){
        if(state){
            setOn();
        } else {
            setOff();
        }
    }

    private void setMarginLeft(int margin){
        if(mThumbView == null) return;
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mThumbView.getLayoutParams();
        params.setMargins(margin, 0, 0, 0);
        mThumbView.setLayoutParams(params);
    }

    // Obtener
    public boolean isOn(){
        return isOn;
    }

    public int getBgColorOff(){
        return mBgColorOff;
    }



    /**
     * Animar THUMB position
     */
    public void animateSlider(boolean to_left){
        getThumbWidth(null);
        final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mThumbView.getLayoutParams();
        ValueAnimator animator = ValueAnimator.ofInt(params.leftMargin, to_left ? 0 : (width - thumbWidth));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
            @Override public void onAnimationUpdate(ValueAnimator valueAnimator){
                params.leftMargin = (Integer) valueAnimator.getAnimatedValue();
                mThumbView.requestLayout();
            }
        });
        animator.setDuration(200);
        animator.start();
    }

    /**
     * Asignar valores
     */
    // Listener
    public void onUnlock(OnLock listener){
        this.listener = listener;
    }
    // Fondo
    public void setBgColorOff(int color){
        mBgColorOff = color;
        setupBackground();
    }
    // Thumb Texto
    public void setThumbTextOff(String text){
        mThumbTextOff = text;
        if(mThumbTextAllCaps) mThumbTextOff = mThumbTextOff.toUpperCase();
        if(!isOn && mThumbTextView != null){
            mThumbTextView.setText(mThumbTextOff);
        }
    }
    public void setThumbTextOff(int res_text){
        setThumbTextOff(ctx.getString(res_text));
    }
    public void setThumbTextColorOff(int color){
        mThumbTextColorOff = color;
        if(!isOn && mThumbTextView != null){
            mThumbTextView.setTextColor(mThumbTextColorOff);
        }
    }
    // Thumb icon
    public void setThumbIconColorOff(int color){
        mThumbIconColorOff = color;
        if(!isOn && mThumbIconView != null){
            mThumbIconView.setColorFilter(mThumbIconColorOff);
        }
    }


    public interface OnLock{
        void onLock(AcsLock acsLock, boolean state);
    }


    /**
     * Utils
     */
    public int toDP(final float px) {
        return Math.round(px / getContext().getResources().getDisplayMetrics().scaledDensity);
    }

    public int toPX(final float sp) {
        return Math.round(sp * getContext().getResources().getDisplayMetrics().scaledDensity);
    }

}
