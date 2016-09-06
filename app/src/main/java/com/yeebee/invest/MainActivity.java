package com.yeebee.invest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.yeebee.invest.activity.information.InformationFragment;
import com.yeebee.invest.activity.search.HomeFragment;
import com.yeebee.invest.activity.setting.SettingFragment;
import com.yeebee.invest.activity.workspace.WorkSpaceFragment;
import com.yeebee.invest.adapter.home.HomePagerAdapter;
import com.yeebee.invest.utils.ConnectUtils;
import com.yeebee.invest.utils.DownloadInstall;
import com.yeebee.invest.utils.ExampleUtil;
import com.yeebee.invest.utils.SharedPreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity implements ViewPager.OnPageChangeListener, TabHost.OnTabChangeListener {
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(android.R.id.tabcontent)
    FrameLayout tabcontent;
    @BindView(android.R.id.tabhost)
    FragmentTabHost tabhost;

    /*推送*/
    public static boolean isForeground = false;
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    private LayoutInflater layoutInflater;
    private Class fragmentArray[] = {WorkSpaceFragment.class, InformationFragment.class, HomeFragment.class, SettingFragment.class};
//    private String textViewArray[] = {"工作台", "信息", "搜索", "设置"};
    private String textViewArray[] = {"首页", "工作台", "消息", "设置"};
    private int imageViewArray[] = {R.drawable.tab_home_information_btn, R.drawable.tab_view_workspace_btn, R.drawable.tab_home_search_btn, R.drawable.tab_view_setting_btn};
    private List<Fragment> list = new ArrayList<Fragment>();
    String version;// apk版本号
    String downLoadAddrsee; // apk下载地址

    Context mContext = MainActivity.this;
    SharedPreferenceUtil mSharedPreferenceUtil;

    String mPhone;
    String tags = "0";

    private static final int MSG_SET_NUM = 2001;


    public final static Handler mNumHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_NUM:
//                    new GetDataTask(true).execute();
                    break;

                default:
                    Log.i("", "Unhandled msg - " + msg.what);
            }
        }
    };

    protected List<AsyncTask<Void, Void, String>> mAsyncTasks = new ArrayList<AsyncTask<Void, Void, String>>();

    protected void putAsyncTask(AsyncTask<Void, Void, String> asyncTask) {
        mAsyncTasks.add(asyncTask.execute());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();//初始化控件
        initPage();//初始化页面

//        getVersionFromServer();

    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    // 控件初始化控件
    private void initView() {
        pager.addOnPageChangeListener(this);//设置页面切换时的监听器
        layoutInflater = LayoutInflater.from(this);//加载布局管理器
        tabhost.setup(this, getSupportFragmentManager(), R.id.pager);//绑定viewpager

        /*实现setOnTabChangedListener接口,目的是为监听界面切换），然后实现TabHost里面图片文字的选中状态切换*/
        /*简单来说,是为了当点击下面菜单时,上面的ViewPager能滑动到对应的Fragment*/
        tabhost.setOnTabChangedListener(this);

        int count = textViewArray.length;
        /*新建Tabspec选项卡并设置Tab菜单栏的内容和绑定对应的Fragment*/
        for (int i = 0; i < count; i++) {
            // 给每个Tab按钮设置标签、图标和文字
            TabHost.TabSpec tabSpec = tabhost.newTabSpec(textViewArray[i])
                    .setIndicator(getTabItemView(i));
            // 将Tab按钮添加进Tab选项卡中，并绑定Fragment
            tabhost.addTab(tabSpec, fragmentArray[i], null);
            tabhost.setTag(i);
            tabhost.getTabWidget().getChildAt(i)
                    .setBackgroundResource(R.drawable.selector_tab_background);//设置Tab被选中的时候颜色改变
        }
    }

    /*初始化Fragment*/
    private void initPage() {
        HomeFragment fragment1 = new HomeFragment();
        WorkSpaceFragment fragment2 = new WorkSpaceFragment();
        InformationFragment fragment3 = new InformationFragment();
        SettingFragment fragment4=new SettingFragment();

        list.add(fragment1);
        list.add(fragment2);
        list.add(fragment3);
        list.add(fragment4);

        //绑定Fragment适配器
        pager.setAdapter(new HomePagerAdapter(getSupportFragmentManager(), list));
        tabhost.getTabWidget().setDividerDrawable(null);
    }

    private View getTabItemView(int i) {
        //将xml布局转换为view对象
        View view = layoutInflater.inflate(R.layout.tab_content, null);
        //利用view对象，找到布局中的组件,并设置内容，然后返回视图
        ImageView mImageView = (ImageView) view
                .findViewById(R.id.tab_imageview);
        TextView mTextView = (TextView) view.findViewById(R.id.tab_textview);
        mImageView.setBackgroundResource(imageViewArray[i]);
        mTextView.setText(textViewArray[i]);
        return view;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    //position是表示你当前选中的页面位置Postion，这事件是在你页面跳转完毕的时候调用的。
    @Override
    public void onPageSelected(int position) {
        TabWidget widget = tabhost.getTabWidget();
        int oldFocusability = widget.getDescendantFocusability();
        widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);//设置View覆盖子类控件而直接获得焦点
        tabhost.setCurrentTab(position);//根据位置Postion设置当前的Tab
        widget.setDescendantFocusability(oldFocusability);//设置取消分割线
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //Tab改变的时候调用
    @Override
    public void onTabChanged(String tabId) {
        int position = tabhost.getCurrentTab();
        pager.setCurrentItem(position);//把选中的Tab的位置赋给适配器，让它控制页面切换
    }

    private void getVersionFromServer(){
        putAsyncTask(new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                JSONObject mJsonObject = new JSONObject();
                try {
                    mJsonObject.put("USERIMEI", SharedPreferenceUtil.getIMEI(getApplicationContext()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String data_params = mJsonObject.toString();
                String code_params = "Z019";// 检查更新接口号
                String myData = ConnectUtils.Post_Myparams(data_params,
                        code_params);
                return myData;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if (result != null) {
                    JSONObject json;
                    try {
                        json = new JSONObject(result);
                        version = json.getString("VersionCode");
                        downLoadAddrsee = json.getString("VersionFile");
                        String versionLoc;
                        try {
                            versionLoc = getVersionName().trim();
                            String Tile = "检查提示";
                            String Meage = "";
                            String zhi = version.split("\\.")[0]
                                    + version.split("\\.")[1]
                                    + version.split("\\.")[2];
                            String zhi2 = versionLoc.split("\\.")[0]
                                    + versionLoc.split("\\.")[1]
                                    + versionLoc.split("\\.")[2];
                            if (Integer.parseInt(zhi)<=Integer.parseInt(zhi2)) {
                            } else {// 显示版本信息
                                Meage = "有最新的版本" + version + ",是否更新？";
                                showAPKTip(Tile, Meage);
                            }
                            // if (version.equals(versionLoc)) {
                            // } else {// 显示版本信息
                            // Meage = "有最新的版本" + version + ",是否更新？";
                            // showAPKTip(Tile, Meage);
                            // }
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        });


//        Call<IPModel> call = IPUtils.ipService .getIpMsg("21.22.11.33");
//        call.enqueue(new Callback<IPModel>() {
//            @Override
//            public void onResponse(Call<IPModel> call, Response<IPModel> response) {
//                //这里的response就可以提取数据了
////                Log.i(TAG, response.body().getData().getCountry());
//                Toast.makeText(getApplicationContext(), response.body().getData().getCountry(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<IPModel> call, Throwable t) {
//
//            }
//        });

    }

    private String getVersionName() throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),
                0);
        String version = packInfo.versionName;
        return version;
    }

    /**
     * 有新的版本
     */
    private void showAPKTip(String Tile, String M_Message) {
        final AlertDialog alertDialog = new AlertDialog.Builder(mContext)
                .setTitle(Tile)
                .setMessage(M_Message)
                .setPositiveButton("马上升级",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                String Url = downLoadAddrsee;
                                DownloadInstall di = new DownloadInstall(
                                        mContext);
                                di.execute(Url);
                                return;
                            }
                        })
                .setNegativeButton("下次再说",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                return;
                            }
                        }).create();
        alertDialog.show();
    }

    //推送
    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!ExampleUtil.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
//                setCostomMsg(showMsg.toString());
            }
        }
    }

}
