package acs.utils.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import acs.utils.AcsBox;
import acs.utils.AcsButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AcsBox mBox;
    private AcsButton mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBox = (AcsBox) findViewById(R.id.box);
        mLogin = (AcsButton) findViewById(R.id.login);

        mBox.onRetryng(new AcsBox.OnRetryng(){
            @Override public void onRetryng(){
                mBox.setLoading();
            }
        });

        findViewById(R.id.load).setOnClickListener(this);

        mBox.setOnClickListener(this);
        mLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.box:
                mLogin.hideLoading();
                break;
            case R.id.login:
                mLogin.setLoading();
                break;
            case R.id.load:
                mBox.setError();
                break;
        }
    }

    @Override
    public void onBackPressed(){
        mBox.hide();
    }
}
