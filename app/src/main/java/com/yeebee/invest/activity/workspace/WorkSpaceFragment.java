package com.yeebee.invest.activity.workspace;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import com.yeebee.invest.R;
import com.yeebee.invest.activity.manageproject.LookProjectActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DRL on 2016/8/22.
 */
public class WorkSpaceFragment extends Fragment {
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
    @BindView(R.id.rl_home_head)
    RelativeLayout rlHomeHead;
    @BindView(R.id.tv_data_center)
    TextView tvDataCenter;
    @BindView(R.id.rl_data_center)
    RelativeLayout rlDataCenter;
    @BindView(R.id.view_1)
    View view1;
    @BindView(R.id.tv_tutor_count)
    TextView tvTutorCount;
    @BindView(R.id.rl_tutor_today)
    RelativeLayout rlTutorToday;
    @BindView(R.id.tv_project)
    TextView tvProject;
    @BindView(R.id.rl_projecr)
    RelativeLayout rlProjecr;
    @BindView(R.id.rl_1)
    RelativeLayout rl1;
    @BindView(R.id.view_2)
    View view2;
    @BindView(R.id.tv_attention_project)
    TextView tvAttentionProject;
    @BindView(R.id.rl_attention_prpject)
    RelativeLayout rlAttentionPrpject;
    @BindView(R.id.tv_receive_project)
    TextView tvReceiveProject;
    @BindView(R.id.rl_receive_project)
    RelativeLayout rlReceiveProject;
    @BindView(R.id.rl_2)
    RelativeLayout rl2;
    @BindView(R.id.view_3)
    View view3;
    @BindView(R.id.tv_invest_project)
    TextView tvInvestProject;
    @BindView(R.id.rl_invest_prpject)
    RelativeLayout rlInvestPrpject;
    @BindView(R.id.tv_take_project)
    TextView tvTakeProject;
    @BindView(R.id.rl_take_project)
    RelativeLayout rlTakeProject;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.tv_news)
    TextSwitcher tvNews;
    @BindView(R.id.ll_news)
    LinearLayout llNews;
    @BindView(R.id.tv_manage_center)
    TextView tvManageCenter;
    @BindView(R.id.rl_manage_center)
    RelativeLayout rlManageCenter;
    @BindView(R.id.view_4)
    View view4;
    @BindView(R.id.tv_look_project)
    TextView tvLookProject;
    @BindView(R.id.tv_review_project_img)
    TextView tvReviewProjectImg;
    @BindView(R.id.tv_manage_project_img)
    TextView tvManageProjectImg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.workspace_fragment, null);


        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.rl_head_img, R.id.tv_code, R.id.rl_tutor_today, R.id.rl_projecr, R.id.rl_attention_prpject, R.id.rl_receive_project, R.id.rl_invest_prpject, R.id.rl_take_project, R.id.tv_news, R.id.tv_look_project, R.id.tv_review_project_img, R.id.tv_manage_project_img})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.rl_head_img:
                Toast.makeText(getContext(),"头部点击事件，跳转到个人信息页面", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_code:
                Toast.makeText(getContext(),"二维码点击事件，跳转到二维码扫描页面", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_tutor_today:
                Toast.makeText(getContext(),"今日推荐点击事件，跳转到今日推荐页面", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_projecr:
                Toast.makeText(getContext(),"查看的项目点击事件，跳转到查看项目页面", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_attention_prpject:
                Toast.makeText(getContext(),"关注的项目点击事件，跳转到关注的项目页面", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_receive_project:
                Toast.makeText(getContext(),"收到的项目点击事件，跳转到收到的项目页面", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_invest_prpject:
                Toast.makeText(getContext(),"投资中的项目点击事件，跳转到投资中的项目页面", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_take_project:
                Toast.makeText(getContext(),"订阅的项目点击事件，跳转到订阅的项目页面", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_news:
                Toast.makeText(getContext(),"亿蜂快报点击事件，跳转到亿蜂快报页面", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_look_project:
                intent.setClass(getActivity(), LookProjectActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_review_project_img:
//                intent.setClass(getActivity(), HomeFragment.class);
//                startActivity(intent);
                break;
            case R.id.tv_manage_project_img:
                Toast.makeText(getContext(),"管项目点击事件，跳转到管项目页面", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
