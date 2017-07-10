package acs.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AcsBox extends RelativeLayout {

    private Context ctx;

    private OnRetryng callback;

    private int mBgColor            = Color.WHITE;

    // Valores (Loading)
    private int mLoadingSize        = Acs.px(getContext(), 70);
    private int mLoadingColor       = 0;

    private int mIconSize           = Acs.px(getContext(), 70);
    private int mIconColor          = 0;

    private int mTitleSize          = Acs.px(getContext(), 17);
    private int mTitleColor         = Color.BLACK;
    private int mSubtitleSize       = Acs.px(getContext(), 14);
    private int mSubtitleColor      = Color.GRAY;

    private int mEmptyIcon          = R.drawable.ic_cloud;
    private String mEmptyTitle      = "Oops!";
    private String mEmptySubtitle   = "No content";

    private int mErrorIcon          = R.drawable.ic_cloud;
    private String mErrorTitle      = "Oops!";
    private String mErrorSubtitle   = "There was an error";

    private String mButtonText      = "Retrying";

    private int mSpacing            = Acs.px(getContext(), 10);

    private String mFont            = "";

    // Vistas
    private LinearLayout mBox;
    private ProgressBar mLoading;
    private ImageView mIcon;
    private TextView mTitle;
    private TextView mSubtitle;
    private Button mButton;

    public AcsBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        ctx = context;

        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.AcsBox);

        mBgColor        = a.getColor(R.styleable.AcsBox_bgColor, mBgColor);

        mLoadingSize    = (int) a.getDimension(R.styleable.AcsBox_loadingSize, mLoadingSize);
        mLoadingColor   = a.getColor(R.styleable.AcsBox_loadingColor, mLoadingColor);

        mIconSize       = (int) a.getDimension(R.styleable.AcsBox_iconSize, mIconSize);
        mIconColor      = a.getColor(R.styleable.AcsBox_iconColor, mIconColor);

        mTitleSize      = (int) a.getDimension(R.styleable.AcsBox_titleSize, mTitleSize);
        mTitleColor     = a.getColor(R.styleable.AcsBox_titleColor, mTitleColor);

        mSubtitleSize   = (int) a.getDimension(R.styleable.AcsBox_subtitleSize, mSubtitleSize);
        mSubtitleColor  = a.getColor(R.styleable.AcsBox_subtitleColor, mSubtitleColor);

        mErrorIcon      = a.getResourceId(R.styleable.AcsBox_errorIcon, mErrorIcon);
        mErrorTitle     = defString(a.getString(R.styleable.AcsBox_errorTitle), mErrorTitle);
        mErrorSubtitle  = defString(a.getString(R.styleable.AcsBox_errorSubtitle), mErrorSubtitle);

        mEmptyIcon      = a.getResourceId(R.styleable.AcsBox_emptyIcon, mEmptyIcon);
        mEmptyTitle     = defString(a.getString(R.styleable.AcsBox_emptyTitle), mEmptyTitle);
        mEmptySubtitle  = defString(a.getString(R.styleable.AcsBox_emptySubtitle), mEmptySubtitle);

        mButtonText     = defString(a.getString(R.styleable.AcsBox_buttonText), mButtonText);

        mSpacing       = (int) a.getDimension(R.styleable.AcsBox_spacing, mSpacing);

        mFont           = a.getString(R.styleable.AcsBox_font);

        a.recycle();
    }

    private void initViews(){
        if(mBox == null){
            mBox = setupContainerView();

            mLoading    = setupLoadingView();
            mIcon       = setupIconView();
            mTitle      = setupTitleView();
            mSubtitle   = setupSubtitleView();
            mButton     = setupButton();

            mButton.setOnClickListener(clickButton);

            mBox.addView(mLoading);
            mBox.addView(mIcon);
            mBox.addView(mTitle);
            mBox.addView(mSubtitle);
            mBox.addView(mButton);

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
    private LinearLayout setupContainerView(){
        LinearLayout box = new LinearLayout(ctx);
        box.setBackgroundColor(mBgColor);
        box.setClickable(true);
        box.setOrientation(LinearLayout.VERTICAL);
        box.setGravity(Gravity.CENTER);

        LayoutParams lp = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        box.setLayoutParams(lp);
        return box;
    }

    private ProgressBar setupLoadingView(){
        ProgressBar pb = new ProgressBar(ctx);
        if(mLoadingColor != 0)
            pb.getIndeterminateDrawable().setColorFilter(mLoadingColor, PorterDuff.Mode.SRC_ATOP);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.width = mLoadingSize;
        layoutParams.height = mLoadingSize;

        pb.setLayoutParams(layoutParams);
        return pb;
    }

    private ImageView setupIconView(){
        ImageView iconView = new ImageView(ctx);

        if(mIconColor != 0) iconView.setColorFilter(mIconColor);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        lp.width = mIconSize;
        lp.height = mIconSize;
        lp.bottomMargin = mSpacing;

        iconView.setLayoutParams(lp);

        return iconView;
    }

    private TextView setupTitleView(){
        TextView textView = new TextView(ctx);
        textView.setTextColor(mTitleColor);
        textView.setTextSize(Acs.dp(ctx, mTitleSize));
        //textView.setTypeface(null, Typeface.BOLD);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        Acs.setFont(ctx, textView, mFont, Typeface.BOLD);
        return textView;
    }

    private TextView setupSubtitleView(){
        TextView textView = new TextView(ctx);
        textView.setTextColor(mSubtitleColor);
        textView.setTextSize(Acs.dp(ctx, mSubtitleSize));
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        Acs.setFont(ctx, textView, mFont);
        return textView;
    }

    private Button setupButton(){
        Button button = new Button(ctx);
        button.setText(mButtonText);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.topMargin = mSpacing;

        button.setLayoutParams(lp);
        Acs.setFont(ctx, button, mFont);
        return button;
    }

    /**
     * Click Button
     */
    private View.OnClickListener clickButton = new OnClickListener(){
        @Override public void onClick(View view){
            if(callback != null){
                callback.onRetryng();
            }
        }
    };


    public void setEmptyIcon(int emptyIcon) {
        this.mEmptyIcon = emptyIcon;
    }

    public void setEmptyTitle(String emptyTitle) {
        this.mEmptyTitle = emptyTitle;
    }

    public void setEmptySubtitle(String emptySubtitle) {
        this.mEmptySubtitle = emptySubtitle;
    }

    public void setErrorIcon(int errorIcon) {
        this.mErrorIcon = errorIcon;
    }

    public void setErrorTitle(String errorTitle) {
        this.mErrorTitle = errorTitle;
    }

    public void setErrorSubtitle(String errorSubtitle) {
        this.mErrorSubtitle = errorSubtitle;
    }

    public void setButtonText(String buttonText) {
        this.mButtonText = buttonText;
    }

    public void setLoading(String title){
        initViews();
        mLoading.setVisibility(View.VISIBLE);
        if(title != null){
            mTitle.setText(title);
            mTitle.setVisibility(View.VISIBLE);
        }
    }

    public void setLoading(){
        setLoading(null);
    }

    public void setValues(int icon, String title, String subtitle, boolean showButton){
        initViews();
        mBox.setBackgroundColor(mBgColor);
        mIcon.setVisibility(View.VISIBLE);
        mTitle.setVisibility(View.VISIBLE);
        mSubtitle.setVisibility(View.VISIBLE);
        mIcon.setImageResource(icon);
        mTitle.setText(title);
        mSubtitle.setText(subtitle);
        if(showButton){
            mButton.setText(mButtonText);
            mButton.setVisibility(View.VISIBLE);
        }
    }

    public void setEmpty(){
        setValues(mEmptyIcon, mEmptyTitle, mEmptySubtitle, false);
    }

    public void setError(){
        setValues(mErrorIcon, mErrorTitle, mErrorSubtitle, true);
    }

    public void setError(OnRetryng callback){
        setError();
        onRetryng(callback);
    }

    public void hide(){
        if(mBox != null){
            mBox.setVisibility(View.GONE);
        }
    }

    public void onRetryng(OnRetryng callback){
        this.callback = callback;
    }

    // Listener
    public interface OnRetryng{
        void onRetryng();
    }

    // Default string
    private String defString(final String value, final String defValue){
        return value == null || value.isEmpty() ? defValue : value;
    }

}