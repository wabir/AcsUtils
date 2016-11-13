package acs.utils.sample;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;

import java.util.Random;

import acs.utils.Acs;
import acs.utils.AcsBox;
import acs.utils.AcsButton;
import acs.utils.AcsLock;
import acs.utils.AcsToast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AcsBox mBox;
    private AcsButton mBtnCircle;
    private AcsButton mBtnLogin;
    private AcsButton mBtnCircleText;
    private AcsButton mBtnLight;
    private AcsButton mBtnDisabled;
    private AcsButton mBtnLeft;
    private AcsLock mLockStatus;
    private AcsLock mLockNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBox = (AcsBox) findViewById(R.id.box);
        mBtnCircle = (AcsButton) findViewById(R.id.btn_circle);
        mBtnLogin = (AcsButton) findViewById(R.id.btn_login);
        mBtnCircleText = (AcsButton) findViewById(R.id.btn_circle_text);
        mBtnLight = (AcsButton) findViewById(R.id.btn_ligth);
        mBtnDisabled = (AcsButton) findViewById(R.id.btn_disabled);
        mBtnLeft = (AcsButton) findViewById(R.id.btn_left);
        mLockStatus = (AcsLock) findViewById(R.id.lock_status);
        mLockNav = (AcsLock) findViewById(R.id.lock_nav);

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
        mBtnCircleText.setOnClickListener(this);
        mBtnLight.setOnClickListener(this);
        mBtnDisabled.setEnabled(false);
        mBtnLeft.setOnClickListener(this);

        mLockNav.onUnlock(new AcsLock.OnUnlock(){
            @Override public void onUnlock(View v, boolean state){
                AcsToast.show(MainActivity.this, "Locked: " + state);
                //mLockNav.setEnabled(false);
            }
        });
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.box:
                mBtnCircle.hideLoading();
                mBtnLogin.hideLoading();
                mBtnCircleText.hideLoading();
                mBtnLight.hideLoading();
                break;
            case R.id.btn_circle:
            case R.id.btn_login:
            case R.id.btn_circle_text:
            case R.id.btn_ligth:
                mBtnCircle.setLoading();
                mBtnLogin.setLoading();
                mBtnCircleText.setLoading();
                mBtnLight.setLoading();
                break;
            case R.id.btn_left:
                mBtnLeft.setBgColor(Color.RED);
                mBtnLeft.setIcon(R.drawable.ic_like);
                mBtnLeft.setText("Text dynamically changed "+randomString());
                mLockStatus.setState(!mLockStatus.isOn());
                mLockNav.setState(!mLockNav.isOn());
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
        super.onBackPressed();
    }

    public static String randomString(){
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(10);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }


}
