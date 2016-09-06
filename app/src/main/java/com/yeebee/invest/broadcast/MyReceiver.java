package com.yeebee.invest.broadcast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import com.yeebee.invest.MainActivity;
import com.yeebee.invest.utils.ExampleUtil;
import com.yeebee.invest.utils.SharedPreferenceUtil;
import com.yeebee.invest.utils.StringUtils;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	
	private static final int MSG_SET_ALIAS = 1001;
	private static final int MSG_SET_TAGS = 1002;
	
	private static final int MSG_SET_NUM = 2001;
	
	SharedPreferenceUtil mSharedPreferenceUtil;
	private static final String TAG = "JPush";
	private static final String PUSHNUMBERACTION = "PUSHNUMBERACTION";
	public static final int RECCOUNT = 100;
	public static final int LEFTRECCOUNT = 101;
	public static Handler pushqipaoHandler = new Handler();
//	msg.what = 123;
//	handler.sendMessage(msg);
//	private SQLiteDatabase pushdatadb = SQLiteDatabase.openOrCreateDatabase("PushData.db", null);
//	Context context;
//	private PushDataDB pushdatadb = new PushDataDB(context);
//	private PushDataDB pushdatadb;
	JSONObject jsonObject;
	private int gotoType = -1;
//	public MyReceiver(Context context) {
//		super();
//		pushdatadb = new PushDataDB();
//		pushdatadb.CreateTable_pushdb();
//	}
	
	String url = null;
	Context mContext;

	@Override
	public void onReceive(Context context, Intent intent) {
		
		mSharedPreferenceUtil = new SharedPreferenceUtil(context, "userInfo");
		mContext = context;
        Bundle bundle = intent.getExtras();
 //       pushdatadb = new PushDataDB(context);
		Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
		
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...
                        
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
        	Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
        	processCustomMessage(context, bundle);
        
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
            
   //         sendPushNumber(context,"56");
            
            String time = getCurrentTime();
	        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
	        String content = bundle.getString(JPushInterface.EXTRA_ALERT);
	        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
	        
	        if(extras != null && extras.length() > 0){ 
	        	try {
	        		
	        		JSONObject jData = null;	        		
	        		if(extras.contains("tags")){
	        			jData = new JSONObject(extras);
	        			String tags = jData.getString("tags");
	        			if(!StringUtils.isEmpty(tags)){
//	        				mSharedPreferenceUtil.setUserTags(tags);
//	        				new LoginActivity().setTagFun(tags);
	        				
	        				setTag(tags);
	        			}
	        		}	        	               	
        		        			
        		} catch (JSONException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
	        }
	        
//	        try {
//				jsonObject = new JSONObject(extras);
//			} catch (JSONException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//	        try {
//				url = jsonObject.getString("url");
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
	//        String url = 
  //          pushdatadb.Pushdata_save(notifactionId,time,title, content,url,null,"unread");
            
            //未读推送数量
    //        int count = pushdatadb.Pushdata_count();
//            Message msg = new Message();
//            msg.obj = count;
//            msg.what = RECCOUNT;
//            pushqipaoHandler.sendMessage(msg);
        	
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
                        
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            
            if(extras != null && extras.length() > 0){ 
	        	try {
	        		
	        		JSONObject jData = null;	        		
	        		if(extras.contains("url")){
	        			jData = new JSONObject(extras);
	        			url = jData.getString("url");
	        			
	        		}	        	               	
       		        			
        		} catch (JSONException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
	        }
            
//            UIHelper uih = new UIHelper(true);
      //      UIHelper uih = new UIHelper();
//            uih.showActivity(context, url);
            
//	          int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
//	     //     sendPushNumber(context,"56");
//	          pushdatadb.Pushdata_updatebynotifactionId(notifactionId);
//	      	//打开自定义的Activity
//	      	Intent i = new Intent(context, BroadcastActivity.class);
//	      	i.putExtras(bundle);
//	      	//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//	      	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
//	      	context.startActivity(i);
	      	
       // UIHelper(true).showActivity(context, url);
            
            
            /*
	      	Intent i = new Intent();
	      	i.putExtras(bundle);
	      	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
            
            if(extras != null && extras.length() > 0){        	
            	try {		            		              	
                	JSONObject jData = new JSONObject(extras);
                	String id = jData.getString("id");
                	String type = jData.getString("type");
                	if(type != null && type.length() > 0){
                		gotoType = Integer.parseInt(type);
                	}
        			switch(gotoType){
        			case 1:    //关注创业者
        			case 3:    //发起创业邀请
        			case 4:
        			case 5:
        			case 6:     				
        			case 7:
        			case 8:
        			case 9:     				
        			case 10:
        			case 11:
        			case 12:       				
        			case 13:
        			case 14:
        			case 15:
        			{        				
        				i.putExtra("XIAOXIID", id);
						i.putExtra("XIAOXITYPEID", gotoType);
        				i.setClass(context, InformationSmallDetailActivity.class);
        				context.startActivity(i);
        				break;
        			}
        			case 2:
        			case 16:
        			{        				
        				i.putExtra("XIAOXIID", id);
						i.putExtra("XIAOXITYPEID", gotoType);
        				i.setClass(context, InformationDetailActivity.class);
        				context.startActivity(i);
        				break;
        			}
//        			case 3:
////        				i.setClass(context, InvitationBusinessActivity.class);
////        				context.startActivity(i);
//        				break;        				    				
        			default:
        				break;       				      			
        			}        			
        		} catch (JSONException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
            }
            */
        	
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
        	
        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
        	boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
        	Log.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
        } else {
        	Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} 
			else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}
	
	//send msg to MainActivity
	private void processCustomMessage(Context context, Bundle bundle) {
		if (MainActivity.isForeground) {
			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
			Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
			msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
			if (!ExampleUtil.isEmpty(extras)) {
				try {
					JSONObject extraJson = new JSONObject(extras);
					if (null != extraJson && extraJson.length() > 0) {
						msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
					}
				} catch (JSONException e) {

				}

			}
			context.sendBroadcast(msgIntent);
		}
	}
	
	private String getCurrentTime()     
    {  	  
	     SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
	     return format.format(new Date());            
    }
	
	public void sendPushNumber(Context context,String pushnumber){
		Intent intent = new Intent();
		intent.setAction(PUSHNUMBERACTION);
		
		intent.putExtra("PUSHNUMBER", pushnumber);
	//	android.content.ContextWrapper.sendBroadcast(intent);
		context.sendBroadcast(intent);
	}
	
	
	
	private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
            case MSG_SET_ALIAS:
                Log.d("ALIAS", "Set alias in handler.");
                JPushInterface.setAliasAndTags(mContext, (String) msg.obj, null, mAliasCallback);
                break;
                
            case MSG_SET_TAGS:
                Log.d("TAGS", "Set tags in handler.");
                JPushInterface.setAliasAndTags(mContext.getApplicationContext(), null, (Set<String>) msg.obj, mTagsCallback);
                break;
                
            default:
                Log.i("", "Unhandled msg - " + msg.what);
            }
        }
    };
    
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
            case 0:
                logs = "Set tag and alias success";
                Log.i("", logs);
                break;
                
            case 6002:
                logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                Log.i("", logs);
                if (ExampleUtil.isConnected(mContext)) {
                	mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                } else {
                	Log.i("", "No network");
                }
                break;
            
            default:
                logs = "Failed with errorCode = " + code;
                Log.e("", logs);
            }
            
//            ExampleUtil.showToast(logs, getApplicationContext());
        }
	    
	};
	
	private final TagAliasCallback mTagsCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
            case 0:
                logs = "Set tag and alias success";
                Log.i("", logs);
                break;
                
            case 6002:
                logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                Log.i("", logs);
                if (ExampleUtil.isConnected(mContext)) {
                	mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_TAGS, tags), 1000 * 60);
                } else {
                	Log.i("", "No network");
                }
                break;
            
            default:
                logs = "Failed with errorCode = " + code;
                Log.e("", logs);
            }
            
    //        ExampleUtil.showToast(logs, getApplicationContext());
        }
        
    };
    
    private void setTag(String state){
//		EditText tagEdit = (EditText) findViewById(R.id.et_tag);
//		String tag = tagEdit.getText().toString().trim();
		String tag = state;
		
        // 检查 tag 的有效性
		if (TextUtils.isEmpty(tag)) {
//			Toast.makeText(PushSetActivity.this,R.string.error_tag_empty, Toast.LENGTH_SHORT).show();
			return;
		}
		
		// ","隔开的多个 转换成 Set
		String[] sArray = tag.split(",");
		Set<String> tagSet = new LinkedHashSet<String>();
		for (String sTagItme : sArray) {
			if (!ExampleUtil.isValidTagAndAlias(sTagItme)) {
//				Toast.makeText(PushSetActivity.this,R.string.error_tag_gs_empty, Toast.LENGTH_SHORT).show();
				return;
			}
			tagSet.add(sTagItme);
		}
		
		//调用JPush API设置Tag
		mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_TAGS, tagSet));

	}
}
