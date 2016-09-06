package com.yeebee.invest.activity.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yeebee.invest.BaseApplication;
import com.yeebee.invest.MainActivity;
import com.yeebee.invest.R;
import com.yeebee.invest.eventbus.EventBusString;
import com.yeebee.invest.utils.ConnectUtils;
import com.yeebee.invest.utils.ExampleUtil;
import com.yeebee.invest.utils.NetWorkUtils;
import com.yeebee.invest.utils.PhoneUtils;
import com.yeebee.invest.utils.SharedPreferenceUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//import cn.jpush.android.api.JPushInterface;
//import cn.jpush.android.api.TagAliasCallback;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.iv_iv_login)
    ImageView ivIvLogin;
    @BindView(R.id.tv_yeebee_invest)
    TextView tvYeebeeInvest;
    @BindView(R.id.iv_login_name_icon)
    ImageView ivLoginNameIcon;
    @BindView(R.id.rl_login_name)
    RelativeLayout rlLoginName;
    @BindView(R.id.iv_login_pwd_icon)
    ImageView ivLoginPwdIcon;
    @BindView(R.id.rl_login_pwd)
    RelativeLayout rlLoginPwd;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_attesttation)
    TextView tvAttesttation;
    @BindView(R.id.tv_forgot)
    TextView tvForgot;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_pwd)
    EditText etPwd;

    String mPhone; // 用户输入的电话号码
    private String string_username;
    private String string_password;
    private Context mContext = LoginActivity.this;
    private SharedPreferenceUtil mSharedPreferenceUtil;

    private static final int MSG_SET_NUM = 2001;

    private static final int MSG_SET_ALIAS = 1001;
    private static final int MSG_SET_TAGS = 1002;

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            if (msg.what == 0x120) {
                Toast.makeText(LoginActivity.this,
                        "登录成功！", Toast.LENGTH_SHORT).show();

                Message msg2 = Message.obtain();
                msg2.what = MSG_SET_NUM;
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                MainActivity.mNumHandler.sendMessage(msg2);
//                judgesetTag();
//                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
//                        .hideSoftInputFromWindow(LoginActivity.this.getCurrentFocus().getWindowToken(),
//                                InputMethodManager.HIDE_NOT_ALWAYS);
                finish();
            }
            if (msg.what == 0x121) {
                Toast.makeText(LoginActivity.this,
                        "密码错误！", Toast.LENGTH_SHORT).show();
            }
            if (msg.what == 0x123) {
                Toast.makeText(LoginActivity.this,
                        "账号不存在！", Toast.LENGTH_SHORT).show();
            }
            if (msg.what == 0x124) {
                Toast.makeText(LoginActivity.this,
                        "登录失败，请稍后再试！", Toast.LENGTH_SHORT).show();
            }
        }

    };

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Log.d("ALIAS", "Set alias in handler.");
//                    JPushInterface.setAliasAndTags(getApplicationContext(), (String) msg.obj, null, mAliasCallback);
                    break;

                case MSG_SET_TAGS:
                    Log.d("TAGS", "Set tags in handler.");
//                    JPushInterface.setAliasAndTags(getApplicationContext(), null, (Set<String>) msg.obj, mTagsCallback);
                    break;

                default:
                    Log.i("", "Unhandled msg - " + msg.what);
            }
        }
    };

    private void judgesetTag() {
        mPhone = mSharedPreferenceUtil.getPhone();
//		userpwd = mSharedPreferenceUtil.getPasswd();
        if (mSharedPreferenceUtil.getIsLogin()) {

//            setTag(tags);
            setAlias(mPhone);

        } else {
//            setTag("0");
            setAlias("0");

        }
    }

    private void setAlias(String stringalias) {
//		EditText aliasEdit = (EditText) findViewById(R.id.et_alias);
//		String alias = aliasEdit.getText().toString().trim();
        String alias = stringalias;
        if (TextUtils.isEmpty(alias)) {
//			Toast.makeText(PushSetActivity.this,R.string.error_alias_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!ExampleUtil.isValidTagAndAlias(alias)) {
//			Toast.makeText(PushSetActivity.this,R.string.error_tag_gs_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        //调用JPush API设置Alias
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
//        getSupportActionBar().hide();
//        EventBus.getDefault().register(this);
        mSharedPreferenceUtil = new SharedPreferenceUtil(this, "userInfo");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }


    @OnClick({R.id.tv_login, R.id.tv_attesttation, R.id.tv_forgot})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                if (!validatePhone()) {
                    Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                string_username = etName.getText().toString().trim();
                if (isNull(etPwd)) {
                    Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                string_password = etPwd.getText().toString().trim();

                if (NetWorkUtils.isNetWorkAvaliable(mContext)) {
                    login();

//                    regHx();
//				EMClient.getInstance().login(string_username, string_password, new EMCallBack() {
//
//					@Override
//					public void onSuccess() {
//
//						// ** manually load all local groups and conversation
//					    EMClient.getInstance().groupManager().loadAllGroups();
//					    EMClient.getInstance().chatManager().loadAllConversations();
//					    TLog.log("onSuccess", "onSuccess----->");
//					}
//
//					@Override
//					public void onProgress(int arg0, String arg1) {
//
//					}
//
//					@Override
//					public void onError(int arg0, String arg1) {
//						TLog.log("onError", "onError----->" + arg1);
//					}
//				});
                } else {
                    setNetwork();
                }
                break;
            //注册界面
            case R.id.tv_attesttation:
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            //忘记密码
            case R.id.tv_forgot:
                startActivity(new Intent(LoginActivity.this, ResetPwdActivity.class));
                break;
        }
    }

    private boolean validatePhone() {
        mPhone = null;
        if (isNull(etName)) {
            Toast.makeText(LoginActivity.this, "请输入账号",
                    Toast.LENGTH_SHORT).show();
            etName.requestFocus();
            return false;
        }
        String phone = etName.getText().toString().trim();
        if (PhoneUtils.isNumeric(phone)) {
            mPhone = phone;
            return true;
        } else {
            Toast.makeText(LoginActivity.this, "账号格式不正确",
                    Toast.LENGTH_SHORT).show();
            etName.requestFocus();
            return false;
        }
//		if (matchPhone(phone)) {
//			if (phone.length() < 3) {
//				Toast.makeText(LoginActivity.this, "账号格式不正确",
//						Toast.LENGTH_SHORT).show();
//				username.requestFocus();
//				return false;
//			}
//			if (Pattern.compile("(\\d{3,})|(\\+\\d{3,})").matcher(phone)
//					.matches()) {
//				mPhone = phone;
//				return true;
//			}
//		}
//		Toast.makeText(LoginActivity.this, "账号格式不正确", Toast.LENGTH_SHORT)
//				.show();
//		username.requestFocus();
    }

    public void setNetwork() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("当前无网络连接，是否进行设置？");
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
//				Intent mintent = new Intent();
//				ComponentName comp = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
//				mintent.setComponent(comp);
//				mintent.setAction("android.intent.action.VIEW");
//				startActivity(mintent);
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));


            }
        });
        builder.setNegativeButton("下次再说", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.cancel();
            }
        });
        builder.create();
        builder.show();
    }

    private boolean isNull(EditText editText) {
        String text = editText.getText().toString().trim();
        if (text != null && text.length() > 0) {
            return false;
        }
        return true;
    }

    /**
     * 登录 用户登录接口编号：U007_2
     */
    private void login() {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                // TODO Auto-generated method stub
//                getLocation();
//				double LASTLOGINLONGITUDE = 0.0;
//				double LASTLOGINLATITUDE = 0.0;
                double LASTLOGINLONGITUDE = BaseApplication.lontitude;
                double LASTLOGINLATITUDE = BaseApplication.latitude;
                JSONObject json = new JSONObject();
                try {
                    json.put("UserName", string_username);
                    json.put("UserPwd", string_password);
                    json.put("USERIMIE", SharedPreferenceUtil.getIMEI(mContext));
                    json.put("jingdu", LASTLOGINLONGITUDE + "");
                    json.put("weidu", LASTLOGINLATITUDE + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String data_params = json.toString();
                String code_params = "U007_2";// 登录接口编号

                String myData = ConnectUtils.Post_Myparams(data_params, code_params);
                if (myData != null) {
                    JSONObject myDataObj = null;
                    try {
                        myDataObj = new JSONObject(myData);
                        int reco = myDataObj.getInt("Recode");
                        if (reco == 1) {

                            String userId = myDataObj.getString("USERID");
//                            tags = myDataObj.getString("STAGS");
                            String USERNICK = myDataObj.optString("USERNICK");
//							Message msg = handler.obtainMessage();
                            mSharedPreferenceUtil.setUserId(userId);
                            mSharedPreferenceUtil.setPhone(mPhone);
//							mSharedPreferenceUtil.setPasswd(userpwd);
                            mSharedPreferenceUtil.setNickName(USERNICK);
//                            mSharedPreferenceUtil.setUserTags(tags);
                            mSharedPreferenceUtil.setIsLogin(true);
//							if (mSharedPreferenceUtil.getIsLogin()) {
//								EMChatManager.getInstance().login(usercode, userpwd,
//										new EMCallBack() {
//											@Override
//											public void onSuccess() {
//												// TODO Auto-generated method stub
//												mSharedPreferenceUtil.setIsLogin(true);
//											}
                            //
//											@Override
//											public void onProgress(int progress,
//													String status) {
//												// TODO Auto-generated method stub
//											}
                            //
//											@Override
//											public void onError(int code, String message) {
//												// TODO Auto-generated method stub
//											}
//										});
//							}
                            Message msg = handler.obtainMessage();
                            msg.what = 0x120;
                            handler.sendMessage(msg);
                        }
                        if (reco == 2) {
                            Message msg = handler.obtainMessage();
                            msg.what = 0x121;
                            handler.sendMessage(msg);
                        }
                        if (reco == 3) {
//							Message msg = handler.obtainMessage();
//							msg.what = 0x122;
//							handler.sendMessage(msg);
                        }
                        if (reco == 0) {
                            Message msg = handler.obtainMessage();
                            msg.what = 0x123;
                            handler.sendMessage(msg);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Message msg = handler.obtainMessage();
                    msg.what = 0x124;
                    handler.sendMessage(msg);
                }

                return null;
            }

        }.execute();
    }

//    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
//
//        @Override
//        public void gotResult(int code, String alias, Set<String> tags) {
//            String logs ;
//            switch (code) {
//                case 0:
//                    logs = "Set tag and alias success";
//                    Log.i("", logs);
//                    break;
//
//                case 6002:
//                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
//                    Log.i("", logs);
//                    if (ExampleUtil.isConnected(getApplicationContext())) {
//                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
//                    } else {
//                        Log.i("", "No network");
//                    }
//                    break;
//
//                default:
//                    logs = "Failed with errorCode = " + code;
//                    Log.e("", logs);
//            }
//
////            ExampleUtil.showToast(logs, getApplicationContext());
//        }
//    };
//
//    private final TagAliasCallback mTagsCallback = new TagAliasCallback() {
//
//        @Override
//        public void gotResult(int code, String alias, Set<String> tags) {
//            String logs ;
//            switch (code) {
//                case 0:
//                    logs = "Set tag and alias success";
//                    Log.i("", logs);
//                    break;
//
//                case 6002:
//                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
//                    Log.i("", logs);
//                    if (ExampleUtil.isConnected(getApplicationContext())) {
//                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_TAGS, tags), 1000 * 60);
//                    } else {
//                        Log.i("", "No network");
//                    }
//                    break;
//
//                default:
//                    logs = "Failed with errorCode = " + code;
//                    Log.e("", logs);
//            }
//
//            //        ExampleUtil.showToast(logs, getApplicationContext());
//        }
//
//    };

    public void onEventMainThread(EventBusString event) {
        if ("OK".equals(event.getMsg())) {
            finish();
        }
    }
}
