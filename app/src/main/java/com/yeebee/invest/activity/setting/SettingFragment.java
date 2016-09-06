package com.yeebee.invest.activity.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yeebee.invest.R;
import com.yeebee.invest.views.TopBar;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 这是设置界面
 * Created by DRL on 2016/8/22.
 */
public class SettingFragment extends Fragment {

    private View mView;

    @BindView(R.id.topBar)
    TopBar mTopBar;

    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.setting_fragment, container, false);
        ButterKnife.bind(this,mView);
        initTopBar();
        return mView;
    }

    //设置标题栏属性
    public void initTopBar() {
        mTopBar.setTitleText("设置");
    }

    public void getPhoto(){



    }
}
