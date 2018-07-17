package acs;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import acs.utils.Acs;
import acs.utils.R;

public class Text extends TextView {

    private Context ctx;

    private int mBgColor = 0;
    private int mBgColorFocus = 0;
    private int mBgColorDisabled = 0;

    private int mBorderColor = 0;
    private int mBorderColorDisabled = 0;
    private int mBorderWidth = 0;
    private int mRadius = 0;

    public Text(Context context) {
        super(context);
        this.ctx = context;
        this.initAcsButton();
    }

    public Text(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.ctx = context;
        this.initAttrs(attrs);
        this.initAcsButton();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.Text);

        mBgColor = a.getColor(R.styleable.Text__bgColor, 0);
        mBgColorFocus = a.getColor(R.styleable.Text__bgColorFocus, 0);
        mBgColorDisabled = a.getColor(R.styleable.Text__bgColorDisabled, mBgColorDisabled);

        mRadius = (int) a.getDimension(R.styleable.Text__radius, mRadius);
        mBorderWidth = (int) a.getDimension(R.styleable.Text__borderWidth, mBorderWidth);
        mBorderColor = a.getColor(R.styleable.Text__borderColor, 0);
        mBorderColorDisabled = a.getColor(R.styleable.Text__borderColorDisabled, mBorderColorDisabled);

        Acs.setFont(ctx, this, a.getString(R.styleable.Text__font), Typeface.NORMAL);

        a.recycle();
    }

    private void initAcsButton() {
        setupBackground();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (isEnabled()) {
            if (mBgColorDisabled == 0) {
                setAlpha(1.0f);
            } else {
                setupBackground();
            }
        } else {
            if (mBgColorDisabled == 0) {
                setAlpha(0.5f);
            } else {
                setupBackground();
            }
        }
    }

    private void setupBackground() {

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
        disabledDrawable.setColor(mBgColorDisabled == 0 ? mBgColor : mBgColorDisabled);
        disabledDrawable.setStroke(mBorderWidth, mBorderColorDisabled == 0 ? mBorderColor : mBorderColorDisabled);

        // Handle Border
        if (mBorderColor != 0) {
            defaultDrawable.setStroke(mBorderWidth, mBorderColor);
        }

        this.setBackground(getRippleDrawable(defaultDrawable, focusDrawable, disabledDrawable));
    }

    //@TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private Drawable getRippleDrawable(Drawable defaultDrawable, Drawable focusDrawable, Drawable disabledDrawable) {
        if (!isEnabled()) {
            return disabledDrawable;
        } else {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                return new RippleDrawable(ColorStateList.valueOf(mBgColorFocus), defaultDrawable, focusDrawable);
            } else {
                StateListDrawable states = new StateListDrawable();

                if (mBgColorFocus != 0) {
                    states.addState(new int[]{android.R.attr.state_pressed}, focusDrawable);
                    states.addState(new int[]{android.R.attr.state_focused}, focusDrawable);
                }

                states.addState(new int[]{}, defaultDrawable);

                return states;
            }
        }
    }

    /**
     * Asignar valores
     */
    // Fondo Normal
    public void setBgColor(int color) {
        mBgColor = color;
        setupBackground();
    }

    public void setBgColorDisabled(int color) {
        mBgColorDisabled = color;
        setupBackground();
    }

    // Border
    public void setBorderColor(int color) {
        mBorderColor = color;
        setupBackground();
    }

    public void setBorderColorDisabled(int color) {
        mBorderColorDisabled = color;
        setupBackground();
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