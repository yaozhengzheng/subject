package com.yeebee.invest.activity.manageproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yeebee.invest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DRL on 2016/8/23.
 */
public class LookProjectActivity extends Activity {
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.rl_head_img)
    RelativeLayout rlHeadImg;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.nickName)
    TextView nickName;
    @BindView(R.id.brief)
    TextView brief;
    @BindView(R.id.tv_mate)
    TextView tvMate;
    @BindView(R.id.tv_refresh)
    TextView tvRefresh;
    @BindView(R.id.tv_refresh_count)
    TextView tvRefreshCount;
    @BindView(R.id.tv_more)
    TextView tvMore;
    @BindView(R.id.view_5)
    View view5;
    @BindView(R.id.lv_list)
    ListView lvList;
    @BindView(R.id.tv_bind)
    TextView tvBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.look_project_activity);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.rl_head_img, R.id.tv_refresh, R.id.tv_more, R.id.tv_bind})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_head_img:
                break;
            case R.id.tv_refresh:
                break;
            case R.id.tv_more:
                break;
            case R.id.tv_bind:
                break;
        }
    }
}
