package acs.utils.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;

import acs.utils.Acs;
import acs.utils.AcsBox;
import acs.utils.AcsButton;
import acs.utils.AcsToast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AcsBox mBox;
    private AcsButton mBtnCircle;
    private AcsButton mBtnLogin;
    private AcsButton mBtnLight;
    private AcsButton mBtnDisabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBox = (AcsBox) findViewById(R.id.box);
        mBtnCircle = (AcsButton) findViewById(R.id.btn_circle);
        mBtnLogin = (AcsButton) findViewById(R.id.btn_login);
        mBtnLight = (AcsButton) findViewById(R.id.btn_ligth);
        mBtnDisabled = (AcsButton) findViewById(R.id.btn_disabled);

        mBox.onRetryng(new AcsBox.OnRetryng(){
            @Override public void onRetryng(){
                mBox.setLoading();
            }
        });

        findViewById(R.id.load).setOnClickListener(this);
        findViewById(R.id.toast).setOnClickListener(this);

        mBox.setOnClickListener(this);
        mBtnCircle.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
        mBtnLight.setOnClickListener(this);
        mBtnDisabled.setEnabled(false);

    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.box:
                mBtnCircle.hideLoading();
                mBtnLogin.hideLoading();
                mBtnLight.hideLoading();
                break;
            case R.id.btn_circle:
            case R.id.btn_login:
            case R.id.btn_ligth:
                mBtnCircle.setLoading();
                mBtnLogin.setLoading();
                mBtnLight.setLoading();
                break;
            case R.id.load:
                mBox.setError();
                break;
            case R.id.toast:
                showToast();
                break;
        }
    }

    private void showToast(){
        AcsToast.Settings stg = new AcsToast.Settings();
        stg.fast = false;
        stg.gravity = Gravity.BOTTOM;
        stg.radius          = 5;
        stg.margin = 20;
        stg.padding = 10;
        stg.icon            = R.drawable.ic_facebook;
        stg.iconMarginRight = 10;
        stg.borderWidth = 0;
        stg.borderColor = Color.BLACK;
        stg.elevation = 4;
                /*stg.gravity         = Gravity.CENTER;
                stg.width           = -2;
                //stg.height          = Acs.spToPx(this, 100);
                stg.bgColor         = Color.parseColor("#555555");
                stg.radius          = 10;
                stg.borderWidth     = 2;
                stg.borderColor     = Color.BLACK;
                stg.textColor       = Color.GREEN;
                stg.textSize        = 20;
                stg.padding         = 20;
                stg.icon            = R.drawable.ic_facebook;
                stg.iconSize        = 50;
                stg.iconColor       = Color.WHITE;
                stg.iconMarginRight = 15;*/
        AcsToast.show(this, "Hello World!!", stg);
    }

    @Override
    public void onBackPressed(){
        mBox.hide();
    }
}
