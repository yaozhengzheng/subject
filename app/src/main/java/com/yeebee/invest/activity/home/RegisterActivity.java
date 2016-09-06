package com.yeebee.invest.activity.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yeebee.invest.R;
import com.yeebee.invest.SelectPopupWindow;
import com.yeebee.invest.utils.SharedPreferenceUtil;
import com.yeebee.invest.views.TopBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.registe_img)
    ImageView registe_img;
    @BindView(R.id.jiantou_jg)
    ImageView jiantou_jg;
    @BindView(R.id.regist_name)
    EditText regist_name;
    @BindView(R.id.regist_num)
    EditText regist_num;
    @BindView(R.id.regist_jd)
    EditText regist_jd;
    @BindView(R.id.alun)
    TextView alun;
    @BindView(R.id.blun)
    TextView blun;
    @BindView(R.id.regist_jg)
    TextView regist_jg;
    @BindView(R.id.regist_ly)
    EditText regist_ly;
    @BindView(R.id.regist_city)
    EditText regist_city;
    @BindView(R.id.regist_phone)
    EditText regist_phone;
    @BindView(R.id.regist_yanzhengma)
    EditText regist_yanzhengma;
    @BindView(R.id.post_yzm)
    Button post_yzm;
    @BindView(R.id.regist_cancel)
    Button regist_cancel;
    @BindView(R.id.regist)
    Button regist;

    //自定义弹出框类
    SelectPopupWindow mPopupWindow;
    //自定义TopBar
    @BindView(R.id.topBar)
    TopBar mTopBar;
    SharedPreferenceUtil mSharedPreferenceUtil;
    String mPhone; // 用户输入的电话号码
    private int mReSendTime = 60;

    String firpwd;
    String secpwd;
    String phoneNum;
    String validate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mTopBar.setTitleText("注册");
    }

    @OnClick(R.id.jiantou_jg)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.jiantou_jg:
                mPopupWindow = new SelectPopupWindow(RegisterActivity.this, itemsOnClick);
                //显示窗口
                mPopupWindow.showAtLocation(RegisterActivity.this.findViewById(R.id.ll),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        }
    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {
            mPopupWindow.dismiss();
            switch (v.getId()) {
                case R.id.tianshilun:
                    regist_jg.setVisibility(View.VISIBLE);
                    alun.setVisibility(View.GONE);
                    blun.setVisibility(View.GONE);
                    break;
                case R.id.alun:
                    alun.setVisibility(View.VISIBLE);
                    blun.setVisibility(View.GONE);
                    regist_jg.setVisibility(View.GONE);
                    break;
                case R.id.blun:
                    alun.setVisibility(View.GONE);
                    blun.setVisibility(View.VISIBLE);
                    regist_jg.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }

    };
}
