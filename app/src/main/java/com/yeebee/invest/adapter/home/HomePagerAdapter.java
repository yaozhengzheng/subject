package com.yeebee.invest.adapter.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by DRL on 2016/8/22.
 */
public class HomePagerAdapter  extends FragmentPagerAdapter {

    List<Fragment> list;
    //写构造方法，方便赋值调用
    public HomePagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list=list;
    }

    //根据Item的位置返回对应位置的Fragment，绑定item和Fragment
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    //设置Item的数量
    @Override
    public int getCount() {
        return list.size();
    }
}
