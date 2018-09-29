package acs;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import acs.utils.Acs;
import acs.utils.Attr;
import acs.utils.R;

public class Box extends RelativeLayout {

    private static final int VALUE_NONE = -1100;

    private Context ctx;

    private OnClickListener onClickError;
    private OnClickListener onClickEmpty;

    // attrs
    private int _cont_color;

    private int _loading_size;
    private int _loading_color;

    private int _icon_size;
    private int _icon_color;

    private int _title_size;
    private int _title_color;
    private int _subtitle_size;
    private int _subtitle_color;

    private int _empty_icon;
    private String _empty_title;
    private String _empty_subtitle;
    private String _empty_button;

    private int _error_icon;
    private String _error_title;
    private String _error_subtitle;
    private String _error_button;

    private String _font;

    // vistas
    private LinearLayout mBox;
    private ProgressBar mLoading;
    private ImageView mIcon;
    private TextView mTitle;
    private TextView mSubtitle;
    private TextView mButton;

    // otros
    private boolean empty;

    public Box(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        ctx = context;

        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.Box);

        _cont_color = a.getColor(R.styleable.Box_bgColor, ctx.getResources().getColor(R.color.acs_box_cont));

        _loading_size = Attr.size(ctx, a, R.styleable.Box_loadingSize, 70);
        _loading_color = a.getColor(R.styleable.Box_loadingColor, VALUE_NONE);

        _icon_size = (int) a.getDimension(R.styleable.Box_iconSize, Acs.px(ctx, 70));
        _icon_color = a.getColor(R.styleable.Box_iconColor, VALUE_NONE);

        _title_size = (int) a.getDimension(R.styleable.Box_titleSize, Acs.px(ctx, 16));
        _title_color = a.getColor(R.styleable.Box_titleColor, ctx.getResources().getColor(R.color.acs_box_title));

        _subtitle_size = (int) a.getDimension(R.styleable.Box_subtitleSize, Acs.px(ctx, 12));
        _subtitle_color = Attr.colorR(ctx, a, R.styleable.Box_subtitleColor, R.color.acs_box_subtitle);

        _error_icon = a.getResourceId(R.styleable.Box_errorIcon, R.drawable.acs_box_error_icon);
        _error_title = defString(a.getString(R.styleable.Box_errorTitle), R.string.acs_box_error_title);
        _error_subtitle = defString(a.getString(R.styleable.Box_errorSubtitle), R.string.acs_box_error_subtitle);
        _error_button = defString(a.getString(R.styleable.Box_errorButton), R.string.acs_box_error_button);

        _empty_icon = a.getResourceId(R.styleable.Box_emptyIcon, R.drawable.acs_box_empty_icon);
        _empty_title = defString(a.getString(R.styleable.Box_emptyTitle), R.string.acs_box_empty_title);
        _empty_subtitle = defString(a.getString(R.styleable.Box_emptySubtitle), R.string.acs_box_empty_subtitle);
        _empty_button = defString(a.getString(R.styleable.Box_emptyButton), R.string.acs_box_empty_button);

        _font = a.getString(R.styleable.Box__font);

        a.recycle();
    }

    private void initViews() {
        if (mBox == null) {
            setupContainerView();
            setupLoadingView();
            setupIconView();
            setupTitleView();
            setupSubtitleView();
            setupButton();
            addView(mBox);
        }
        mBox.setVisibility(View.VISIBLE);
        mLoading.setVisibility(View.GONE);
        mIcon.setVisibility(View.GONE);
        mTitle.setVisibility(View.GONE);
        mSubtitle.setVisibility(View.GONE);
        mButton.setVisibility(View.GONE);
    }

    /**
     * Setup
     */
    private void setupContainerView() {
        mBox = (LinearLayout) LayoutInflater.from(ctx).inflate(R.layout._box_cont, null);
        mBox.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        mBox.setBackgroundColor(_cont_color);
    }

    private void setupLoadingView() {
        mLoading = (ProgressBar) mBox.findViewById(R.id.loading);
        mLoading.setLayoutParams(new LinearLayout.LayoutParams(_loading_size, _loading_size));
        if (_loading_color != VALUE_NONE)
            mLoading.getIndeterminateDrawable().setColorFilter(_loading_color, PorterDuff.Mode.SRC_ATOP);
    }

    private void setupIconView() {
        mIcon = (ImageView) mBox.findViewById(R.id.icon);
        mIcon.setLayoutParams(new LinearLayout.LayoutParams(_icon_size, _icon_size));
        if (_icon_color != VALUE_NONE) mIcon.setColorFilter(_icon_color);
    }

    private void setupTitleView() {
        mTitle = (TextView) mBox.findViewById(R.id.title);
        mTitle.setTextColor(_title_color);
        mTitle.setTextSize(Acs.dp(ctx, _title_size));
        Acs.setFont(ctx, mTitle, _font, Typeface.BOLD);
    }

    private void setupSubtitleView() {
        mSubtitle = (TextView) mBox.findViewById(R.id.subtitle);
        mSubtitle.setTextColor(_subtitle_color);
        mSubtitle.setTextSize(Acs.dp(ctx, _subtitle_size));
        Acs.setFont(ctx, mSubtitle, _font);
    }

    private void setupButton() {
        mButton = (TextView) mBox.findViewById(R.id.button);
        mButton.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                if (empty) {
                    if (onClickEmpty != null) onClickEmpty.onClick(view);
                } else {
                    if (onClickError != null) onClickError.onClick(view);
                }
            }
        });
        Acs.setFont(ctx, mButton, _font, mButton.getTypeface().getStyle());
    }

    public void setEmptyIcon(int emptyIcon) {
        this._empty_icon = emptyIcon;
    }

    public void setEmptyTitle(String emptyTitle) {
        this._empty_title = emptyTitle;
    }

    public void setEmptyTitle(int res) {
        this._empty_title = ctx.getString(res);
    }

    public void setEmptySubtitle(String emptySubtitle) {
        this._empty_subtitle = emptySubtitle;
    }

    public void setEmptySubtitle(int res) {
        this._empty_subtitle = ctx.getString(res);
    }

    public void setEmptyButton(String text) {
        this._empty_button = text;
    }

    public void setEmptyButton(int res) {
        this._empty_button = ctx.getString(res);
    }

    public void setErrorIcon(int errorIcon) {
        this._error_icon = errorIcon;
    }

    public void setErrorTitle(String errorTitle) {
        this._error_title = errorTitle;
    }

    public void setErrorTitle(int res) {
        this._error_title = ctx.getString(res);
    }

    public void setErrorSubtitle(String errorSubtitle) {
        this._error_subtitle = errorSubtitle;
    }

    public void setErrorSubtitle(int res) {
        this._error_subtitle = ctx.getString(res);
    }

    public void setErrorButton(String text) {
        this._error_button = text;
    }

    public void setErrorButton(int res) {
        this._error_button = ctx.getString(res);
    }

    public void setLoading(String title) {
        initViews();
        mLoading.setVisibility(View.VISIBLE);
        if (title != null) {
            mTitle.setText(title);
            mTitle.setVisibility(View.VISIBLE);
        }
    }

    public void setLoading() {
        setLoading(null);
    }

    public void setValues(int icon, String title, String subtitle, String button) {
        initViews();
        mBox.setBackgroundColor(_cont_color);
        mIcon.setVisibility(View.VISIBLE);
        mTitle.setVisibility(View.VISIBLE);
        mSubtitle.setVisibility(View.VISIBLE);
        mIcon.setImageResource(icon);
        mTitle.setText(title);
        mSubtitle.setText(subtitle);
        if (button == null || button.isEmpty()) {
            mButton.setVisibility(View.GONE);
        } else {
            mButton.setText(button);
            mButton.setVisibility(View.VISIBLE);
        }
    }

    public void setEmpty() {
        empty = true;
        setValues(_empty_icon, _empty_title, _empty_subtitle, _empty_button);
    }

    public void setEmpty(OnClickListener listener) {
        setEmpty();
        onClickEmpty(listener);
    }

    public void setError() {
        empty = false;
        setValues(_error_icon, _error_title, _error_subtitle, _error_button);
    }

    public void setError(OnClickListener listener) {
        setError();
        onClickError(listener);
    }

    public void hide() {
        if (mBox != null) {
            mBox.setVisibility(View.GONE);
        }
    }

    public void onClickEmpty(OnClickListener listener) {
        this.onClickEmpty = listener;
    }

    public void onClickError(OnClickListener listener) {
        this.onClickError = listener;
    }

    // Default string
    private String defString(final String value, final String defValue) {
        return value == null || value.isEmpty() ? defValue : value;
    }

    private String defString(final String value, final int refDefValue) {
        return value == null || value.isEmpty() ? ctx.getString(refDefValue) : value;
    }

}