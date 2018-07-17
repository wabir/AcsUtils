package acs;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.widget.EditText;

import acs.utils.Acs;
import acs.utils.R;

public class Field extends EditText {

    private Context ctx;

    private static final int COLOR_NONE = -1;

    private int mBgColor;
    private int mBgColorFocus;
    private int mBgColorDisabled;

    private int mBorderColor;
    private int mBorderColorFocus;
    private int mBorderColorDisabled;
    private int mBorderWidth;
    private int mBorderWidthFocus;
    private int mRadius;

    public Field(Context context) {
        super(context);
        this.ctx = context;
        this.init();
    }

    public Field(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.ctx = context;
        this.initAttrs(attrs);
        this.init();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.Field);

        mBgColor = a.getColor(R.styleable.Field__bgColor, COLOR_NONE);
        mBgColorFocus = a.getColor(R.styleable.Field__bgColorFocus, COLOR_NONE);
        mBgColorDisabled = a.getColor(R.styleable.Field__bgColorDisabled, COLOR_NONE);

        mRadius = (int) a.getDimension(R.styleable.Field__radius, 0);
        mBorderWidth = (int) a.getDimension(R.styleable.Field__borderWidth, 0);
        mBorderWidthFocus = (int) a.getDimension(R.styleable.Field__borderWidthFocus, 0);
        mBorderColor = a.getColor(R.styleable.Field__borderColor, COLOR_NONE);
        mBorderColorFocus = a.getColor(R.styleable.Field__borderColorFocus, COLOR_NONE);
        mBorderColorDisabled = a.getColor(R.styleable.Field__borderColorDisabled, COLOR_NONE);

        Acs.setFont(ctx, this, a.getString(R.styleable.Field__font), Typeface.NORMAL);

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
                states.addState(new int[]{android.R.attr.state_focused}, _focus);
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

    /**
     * Utilities
     */
    public int getInt(int default_value) {
        return Acs.getInt(getText().toString(), default_value);
    }

    public int getInt() {
        return getInt(0);
    }


    public float getFloat(float default_value) {
        return Acs.getFloat(getText().toString(), default_value);
    }

    public float getFloat() {
        return getFloat(0);
    }

    public String getString() {
        return getText().toString().trim();
    }

}