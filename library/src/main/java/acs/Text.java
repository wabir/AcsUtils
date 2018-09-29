package acs;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import acs.utils.Acs;
import acs.utils.R;

public class Text extends TextView {

    private static final int COLOR_NONE = -1100;

    private int mBgColor;
    private int mBgColorFocus;
    private int mBgColorDisabled;

    private int mBorderColor;
    private int mBorderColorFocus;
    private int mBorderColorDisabled;
    private int mBorderWidth;
    private int mBorderWidthFocus;
    private int mRadius;

    public Text(Context context) {
        super(context);
        this.init();
    }

    public Text(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initAttrs(attrs);
        this.init();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.Text);

        mBgColor = a.getColor(R.styleable.Text__bgColor, COLOR_NONE);
        mBgColorFocus = a.getColor(R.styleable.Text__bgColorFocus, COLOR_NONE);
        mBgColorDisabled = a.getColor(R.styleable.Text__bgColorDisabled, COLOR_NONE);

        mRadius = (int) a.getDimension(R.styleable.Text__radius, 0);
        mBorderWidth = (int) a.getDimension(R.styleable.Text__borderWidth, 0);
        mBorderWidthFocus = (int) a.getDimension(R.styleable.Text__borderWidthFocus, 0);
        mBorderColor = a.getColor(R.styleable.Text__borderColor, COLOR_NONE);
        mBorderColorFocus = a.getColor(R.styleable.Text__borderColorFocus, COLOR_NONE);
        mBorderColorDisabled = a.getColor(R.styleable.Text__borderColorDisabled, COLOR_NONE);

        Acs.setFont(getContext(), this, a.getString(R.styleable.Text__font), getTypeface().getStyle());

        a.recycle();
    }

    private void init() {
        setupBackground();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        setupBackground();
    }

    @Override public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
        setupBackground();
    }

    private void setupBackground() {
        if (mBgColor == COLOR_NONE) return;

        if (mBgColorFocus == COLOR_NONE) {
            mBgColorFocus = mBgColor;
        }

        if (mBorderColor != COLOR_NONE && mBorderColorFocus == COLOR_NONE) {
            mBorderColorFocus = mBorderColor;
        }

        // Default Drawable
        GradientDrawable _default = new GradientDrawable();
        _default.setCornerRadius(mRadius);
        _default.setColor(mBgColor);
        if (mBorderWidth > 0) {
            _default.setStroke(mBorderWidth, mBorderColor);
        }

        //Focus Drawable
        GradientDrawable _focus = new GradientDrawable();
        _focus.setCornerRadius(mRadius);
        _focus.setColor(mBgColorFocus);
        _focus.setStroke(mBorderWidthFocus > 0 ? mBorderWidthFocus : mBorderWidth, mBorderColorFocus);

        // Disabled Drawable
        GradientDrawable _disabled = new GradientDrawable();
        _disabled.setCornerRadius(mRadius);
        _disabled.setColor(mBgColorDisabled == COLOR_NONE ? mBgColor : mBgColorDisabled);
        _disabled.setStroke(mBorderWidth, mBorderColorDisabled == COLOR_NONE ? mBorderColor : mBorderColorDisabled);

        this.setBackground(getBG(_default, _focus, _disabled));

        if (isClickable()) {
            this.setTextColor(new ColorStateList(
                    new int[][]{
                            new int[]{android.R.attr.state_pressed},
                            new int[]{}
                    },
                    new int[]{
                            Acs.alphaColor(getCurrentTextColor(), 0.5f),
                            getCurrentTextColor()
                    }
            ));
        }
    }

    private Drawable getBG(Drawable _default, Drawable _focus, Drawable _disabled) {
        if (!isEnabled()) {
            return _disabled;
        } else {

            StateListDrawable states = new StateListDrawable();

            if (mBgColorFocus != COLOR_NONE) {
                states.addState(new int[]{android.R.attr.state_pressed}, _focus);
            }

            states.addState(new int[]{}, _default);

            return states;
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

    //Obtener valores
    public int getBgColor() {
        return mBgColor;
    }

    public int getBgColorDisabled() {
        return mBgColorDisabled;
    }

    public int getBorderColor() {
        return mBorderColor;
    }

}