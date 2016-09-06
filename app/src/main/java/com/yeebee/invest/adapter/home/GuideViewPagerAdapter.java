package com.yeebee.invest.adapter.home;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yeebee.invest.MainActivity;
import com.yeebee.invest.R;
import com.yeebee.invest.utils.SharedPreferenceUtil;

import java.util.List;

/**
 * Created by DRL on 2016/8/19.
 */
public class GuideViewPagerAdapter extends PagerAdapter {
    private List<View> views;
    private Activity activity;
    private static final String SHAREDPREFERENCES_NAME = "first_pref";

    public GuideViewPagerAdapter(List<View> views, Activity activity) {
        this.views = views;
        this.activity = activity;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        ((ViewPager)container).removeView(views.get(position));
    }

    @Override
    public int getCount() {
        if (views != null) {
            return views.size();
        }
        return 0;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
//        return super.instantiateItem(container, position);
        ((ViewPager)container).addView(views.get(position), 0);
        if(position == views.size() - 1){
            TextView iv_btn_start = (TextView) container
                    .findViewById(R.id.iv_btn_start);
            iv_btn_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setGuided();
                    goHome();
                }
            });
        }
        return views.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    private void goHome() {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    private void setGuided() {
        SharedPreferenceUtil sharedpreUtil = new SharedPreferenceUtil(activity, "first_pref");

        sharedpreUtil.setIsFirst(false);
    }
}
