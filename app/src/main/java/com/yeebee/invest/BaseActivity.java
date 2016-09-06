package com.yeebee.invest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by DRL on 2016/8/18.
 */
public class BaseActivity extends Activity {
    protected List<AsyncTask<Void, Void, String>> mAsyncTasks = new ArrayList<AsyncTask<Void, Void, String>>();

//	protected DialogInterface.OnClickListener cancelDialog = new DialogInterface.OnClickListener() {
//		@Override
//		public void onClick(DialogInterface dialog, int which) {
//			IntentUtils.keepDialog(dialog, true);
//			dialog.dismiss();
//		}
//	};

    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }


    //�ж������Ƿ�����
    public boolean isNetWorkAvaliable(){
        Context context = getApplication();
        ConnectivityManager connect = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connect == null){
            return false;
        }else{
            NetworkInfo[] info = connect.getAllNetworkInfo();
            if(info != null){
                for(int i=0;i<info.length;i++){
                    if(info[i].getState() == NetworkInfo.State.CONNECTED){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    //������������
    public void setNetwork(){
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

    protected void putAsyncTask(AsyncTask<Void, Void, String> asyncTask) {
        mAsyncTasks.add(asyncTask.execute());
    }

    protected void clearAsyncTask() {
        Iterator<AsyncTask<Void, Void, String>> iterator = mAsyncTasks
                .iterator();
        while (iterator.hasNext()) {
            AsyncTask<Void, Void, String> asyncTask = iterator.next();
            if (asyncTask != null && !asyncTask.isCancelled()) {
                asyncTask.cancel(true);
            }
        }
        mAsyncTasks.clear();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(),"再按一次退出亿蜂",Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                MobclickAgent.onKillProcess(getApplicationContext());
//	            System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
