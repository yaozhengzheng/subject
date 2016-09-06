package com.yeebee.invest.broadcast;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yeebee.invest.MainActivity;
import com.yeebee.invest.R;
import com.yeebee.invest.db.PushDataDB;

import cn.jpush.android.api.JPushInterface;


public class BroadcastActivity extends Activity {
	
	private PushDataDB pushdatadb = null;
	private LinearLayout backlayout;
	private WebView pushwebView;
	
	JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_broadcastchat);
        
        pushdatadb = new PushDataDB(this);
   //     pushdatadb.CreateTable_pushdb();
    //    TextView tv = new TextView(this);
    //    jsonObject = new JSONObject();
        pushwebView = (WebView)findViewById(R.id.pushwebView);
        
        pushwebView.setWebViewClient(new MyWebViewClient());
//        pushwebView.loadUrl("http://www.baidu.com");
       
        backlayout = (LinearLayout)findViewById(R.id.pushbroadcast_back_btn);
        backlayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(BroadcastActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
        Intent intent = getIntent();
		if (null != intent) {
			Bundle bundle = intent.getExtras();
			String content = bundle.getString(JPushInterface.EXTRA_ALERT);
			String keyurl = bundle.getString(JPushInterface.EXTRA_EXTRA);
	        String url = null;
	        try {
				jsonObject = new JSONObject(keyurl);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        try {
				url = jsonObject.getString("URL");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(null != url){
				pushwebView.loadUrl(url);
			}else{
				pushwebView.loadData(content, "text/html; charset=UTF-8", null);
			}
		          
	//        String url = bundle.getString(JPushInterface.ex)
	     //   tv.setText("Title : " + title + "  " + "Content : " + content);

        }
    //   addContentView(tv, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
   
    
    }
    
    private String getCurrentTime()     
    {  	  
	     SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
	     return format.format(new Date());            
    }
    
    private class MyWebViewClient extends WebViewClient {
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return false;
		}

//		public void onPageFinished(WebView view, String url) {
//			if (pb.isShowing()) {
//				pb.dismiss();
//			}
//		}

		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			Toast.makeText(BroadcastActivity.this, "网页加载出错啦！！", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
	//		MyApplication.getInstance().exitApp();
			Intent intent = new Intent();
			intent.setClass(BroadcastActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
    

}
