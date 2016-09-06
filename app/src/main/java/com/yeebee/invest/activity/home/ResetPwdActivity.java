package com.yeebee.invest.activity.home;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yeebee.invest.MainActivity;
import com.yeebee.invest.R;
import com.yeebee.invest.eventbus.EventBusString;
import com.yeebee.invest.utils.ConnectUtils;
import com.yeebee.invest.utils.DialogUtils;
import com.yeebee.invest.utils.PhoneUtils;
import com.yeebee.invest.utils.SharedPreferenceUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResetPwdActivity extends AppCompatActivity {

    @BindView(R.id.iv_iv_login)
    ImageView ivIvLogin;
    @BindView(R.id.tv_yeebee_invest)
    TextView tvYeebeeInvest;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.rl_rigister_name)
    RelativeLayout rlRigisterName;
    @BindView(R.id.tv_code)
    Button tvCode;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.rl_register_code)
    RelativeLayout rlRegisterCode;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.rl_rigister_pwd)
    RelativeLayout rlRigisterPwd;
    @BindView(R.id.et_make_sure_pwd)
    EditText etMakeSurePwd;
    @BindView(R.id.rl_rigister_make_sure_pwd)
    RelativeLayout rlRigisterMakeSurePwd;
    @BindView(R.id.tv_login)
    TextView tvLogin;

    SharedPreferenceUtil mSharedPreferenceUtil;
    String mPhone; // 用户输入的电话号码
    private int mReSendTime = 60;

    String firpwd;
    String secpwd;
    String phoneNum;
    String validate;


    protected List<AsyncTask<Void, Void, String>> mAsyncTasks = new ArrayList<AsyncTask<Void, Void, String>>();
    protected void putAsyncTask(AsyncTask<Void, Void, String> asyncTask) {
        mAsyncTasks.add(asyncTask.execute());
    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                if (mReSendTime > 1) {
                    mReSendTime--;
                    tvCode.setEnabled(false);
                    tvCode.setText("重发(" + mReSendTime + ")");
                    handler.sendEmptyMessageDelayed(0, 1000);
                } else {
                    mReSendTime = 60;
                    tvCode.setEnabled(true);
                    tvCode.setText("重    发");
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        mSharedPreferenceUtil = new SharedPreferenceUtil(this,"userInfo");
    }
    @OnClick({R.id.tv_code, R.id.tv_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_code:
                getverifycode();
                break;
            case R.id.tv_login:
                setMsgToServer();
                break;
        }
    }

    private void getverifycode() {   //获取验证码
        if (validatePhone()) {

            putAsyncTask(new AsyncTask<Void, Void, String>() {

                @Override
                protected String doInBackground(Void... params) {

                    handler.sendEmptyMessage(0);
                    JSONObject json = new JSONObject();
                    try {
                        json.put("USERTEL", mPhone);
                        json.put("USERPWD", "");
                        json.put("USERIMIE", SharedPreferenceUtil.getIMEI(ResetPwdActivity.this));
                        json.put("USERYANZHENG", "");
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    String data_params = json.toString();
                    String code_params = "U001";//  发送注册验证码接口编号：001
                    String myData = ConnectUtils.Post_Myparams(data_params, code_params);

                    return myData;
                }

                @Override
                protected void onPostExecute(String result) {
                    super.onPostExecute(result);
                    if (result != null) {
                        JSONObject myDataObj = null;
                        try {
                            myDataObj = new JSONObject(result);
                            int reco = myDataObj.getInt("Recode");
//							String msg = myDataObj.getString("Remsg");
                            if(reco == 0) {
                                Toast.makeText(ResetPwdActivity.this,
                                        "手机号格式不正确！", Toast.LENGTH_SHORT).show();
                            } else if (reco == 1) {
                                etCode.requestFocus();
                                //	handler.sendEmptyMessage(0);
                            } else if (reco == 2) {
                                Toast.makeText(ResetPwdActivity.this,
                                        "发送失败！", Toast.LENGTH_SHORT).show();
                            } else if (reco == 3) {
                                Toast.makeText(ResetPwdActivity.this,
                                        "短信发送错误！", Toast.LENGTH_SHORT).show();
                            } else if (reco == 4) {
                                Toast.makeText(ResetPwdActivity.this,
                                        "该手机已注册，请直接登录!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ResetPwdActivity.this, "绑定失败!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                    }
                }

            });
        }
    }

    private boolean validatePhone() {
        mPhone = null;
        if (isNull(etName)) {
            Toast.makeText(ResetPwdActivity.this, "请输入手机号码",
                    Toast.LENGTH_SHORT).show();
            etName.requestFocus();
            return false;
        }
        String phone = etName.getText().toString().trim();
        if (PhoneUtils.isNumeric(phone)){
            mPhone = phone;
            return true;
        }else{
            Toast.makeText(ResetPwdActivity.this, "手机号码格式不正确",
                    Toast.LENGTH_SHORT).show();
            etName.requestFocus();
            return false;
        }
    }

    private boolean isNull(EditText editText) {
        String text = editText.getText().toString().trim();
        if (text != null && text.length() > 0) {
            return false;
        }
        return true;
    }

    private void setMsgToServer() {    //注册

        if (isNull(etCode)) {
            Toast.makeText(ResetPwdActivity.this, "验证码不能为空！",
                    Toast.LENGTH_SHORT).show();
            etCode.requestFocus();
            return;
        }

        if(valipwd()){

            putAsyncTask(new AsyncTask<Void, Void, String>() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    validate = etCode.getText().toString().trim();
                    phoneNum = etName.getText().toString()
                            .trim();
                }

                @Override
                protected String doInBackground(Void... params) {
                    JSONObject json = new JSONObject();
                    try {
                        json.put("USERTEL", phoneNum);
                        json.put("USERPWD", firpwd);
                        json.put("USERIMIE", SharedPreferenceUtil.getIMEI(ResetPwdActivity.this));
                        json.put("USERYANZHENG", validate);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String data_params = json.toString();
                    String code_params = "U002"; // 发送绑定验证码接口编号
                    String myData = ConnectUtils.Post_Myparams(data_params,
                            code_params);

                    // call method in SDK  v3.x
//					try {
//						EMClient.getInstance().createAccount(phoneNum, firpwd);
//					} catch (HyphenateException e) {
//						e.printStackTrace();
//					}

                    //v2.x

                    try {
                        // 调用sdk注册方法
//                        EMChatManager.getInstance().createAccountOnServer(phoneNum,
//                                phoneNum);
                    } catch (final Exception e) {

                    }
                    return myData;
                }

                @Override
                protected void onPostExecute(String result) {
                    super.onPostExecute(result);
                    if (result != null) {
                        JSONObject myDataObj = null;
                        try {
                            myDataObj = new JSONObject(result);
                            int reco = myDataObj.getInt("Recode");
                            if (reco == 0) {
                                Toast.makeText(ResetPwdActivity.this, "请先获取验证码！",
                                        Toast.LENGTH_SHORT).show();
                            }else if (reco == 1) {
                                mSharedPreferenceUtil.setNickName(phoneNum);
                                mSharedPreferenceUtil.setPasswd(etPwd.getText().toString().trim());
                                //	Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                //	startActivity(intent);

                                Toast.makeText(ResetPwdActivity.this, "注册成功！",
                                        Toast.LENGTH_SHORT).show();
                                EventBus.getDefault().post(
                                        new EventBusString("OK"));
                                Intent intent = new Intent();
                                intent.setClass(ResetPwdActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else if (reco == 2) {
                                Toast.makeText(ResetPwdActivity.this, "注册失败！",
                                        Toast.LENGTH_SHORT).show();
                            } else if (reco == 3) {
                                Toast.makeText(ResetPwdActivity.this, "验证码输入错误！",
                                        Toast.LENGTH_SHORT).show();
                            }else if (reco == 4) {
                                DialogUtils.showInfoDialog(ResetPwdActivity.this,
                                        "验证码已超时，请重新获取验证码！");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            });
        }
    }

    private boolean valipwd() {
        firpwd = etPwd.getText().toString().trim();
        secpwd = etMakeSurePwd.getText().toString().trim();

        if (isNull(etPwd)) {
            Toast.makeText(ResetPwdActivity.this, "密码不能为空！",
                    Toast.LENGTH_SHORT).show();
            etPwd.requestFocus();
            return false;
        }else if(firpwd.length() < 6){
            Toast.makeText(ResetPwdActivity.this, "密码长度不能小于6位！",
                    Toast.LENGTH_SHORT).show();
            etPwd.requestFocus();
            return false;
        }
        if (isNull(etMakeSurePwd)) {
            Toast.makeText(ResetPwdActivity.this, "密码不能为空！",
                    Toast.LENGTH_SHORT).show();
            etMakeSurePwd.requestFocus();
            return false;
        }

        if(!(firpwd.equals(secpwd))){
            Toast.makeText(ResetPwdActivity.this, "两次输入的密码不相同！",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
