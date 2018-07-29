package acs;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;

import acs.utils.R;

public class View extends android.view.View {

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

    public View(Context context) {
        super(context);
        this.init();
    }

    public View(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initAttrs(attrs);
        this.init();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.View);

        mBgColor = a.getColor(R.styleable.View__bgColor, COLOR_NONE);
        mBgColorFocus = a.getColor(R.styleable.View__bgColorFocus, COLOR_NONE);
        mBgColorDisabled = a.getColor(R.styleable.View__bgColorDisabled, COLOR_NONE);

        mRadius = (int) a.getDimension(R.styleable.View__radius, 0);
        mBorderWidth = (int) a.getDimension(R.styleable.View__borderWidth, 0);
        mBorderWidthFocus = (int) a.getDimension(R.styleable.View__borderWidthFocus, 0);
        mBorderColor = a.getColor(R.styleable.View__borderColor, COLOR_NONE);
        mBorderColorFocus = a.getColor(R.styleable.View__borderColorFocus, COLOR_NONE);
        mBorderColorDisabled = a.getColor(R.styleable.View__borderColorDisabled, COLOR_NONE);

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

    private void setupBackground() {
        if (mBgColor == COLOR_NONE) return;

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
        _focus.setColor(mBgColorFocus == COLOR_NONE ? mBgColor : mBgColorFocus);
        _focus.setStroke(mBorderWidthFocus > 0 ? mBorderWidthFocus : mBorderWidth,
                mBorderColorFocus == COLOR_NONE ? mBorderColor : mBorderColorFocus);

        // Disabled Drawable
        GradientDrawable _disabled = new GradientDrawable();
        _disabled.setCornerRadius(mRadius);
        _disabled.setColor(mBgColorDisabled == COLOR_NONE ? mBgColor : mBgColorDisabled);
        _disabled.setStroke(mBorderWidth, mBorderColorDisabled == COLOR_NONE ? mBorderColor : mBorderColorDisabled);

        this.setBackground(getBG(_default, _focus, _disabled));
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