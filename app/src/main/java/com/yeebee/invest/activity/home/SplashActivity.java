package com.yeebee.invest.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.umeng.analytics.MobclickAgent;
import com.yeebee.invest.MainActivity;
import com.yeebee.invest.R;
import com.yeebee.invest.BaseActivity;
import com.yeebee.invest.utils.SharedPreferenceUtil;

import butterknife.ButterKnife;

/**
 * Created by DRL on 2016/8/19.
 */
public class SplashActivity extends BaseActivity {

    private SharedPreferenceUtil sharedpreutil;
    private boolean isFirstIn = false;
    private Runnable runnable;
    private Handler handler = new Handler();
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        ButterKnife.bind(this);

        MobclickAgent.openActivityDurationTrack(false);
        sharedpreutil = new SharedPreferenceUtil(this, "is_first");
        isFirstIn = sharedpreutil.getIsFirst();

        runnable = new Runnable() {
            public void run() {
                intent = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
                /*if (isFirstIn) {
//                    intent = new Intent(SplashActivity.this,GuideActivity.class);
                    intent = new Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    intent = new Intent();
                    intent.setClass(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }*/
        };
        handler.postDelayed(runnable, 3000);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        JPushInterface.onPause(this);
        MobclickAgent.onPause(this);
        MobclickAgent.onPageStart("Splash");
        sharedpreutil.setIsFirst(false);
    }

}
