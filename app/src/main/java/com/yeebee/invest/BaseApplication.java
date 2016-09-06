package com.yeebee.invest;

import java.io.File;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.LinkedList;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.Vibrator;
import android.widget.Toast;
//import cn.jpush.android.api.JPushInterface;

import com.baidu.frontia.FrontiaApplication;
//import com.baidu.location.BDLocation;
//import com.baidu.location.BDLocationListener;
//import com.baidu.location.LocationClient;
//import com.baidu.location.LocationClientOption;
//import com.baidu.location.LocationClientOption.LocationMode;
//import com.baidu.location.GeofenceClient;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
//import com.dream.makerspace.Constants;
//import com.dream.makerspace.domain.MyAppData;
//import com.dream.makerspace.utils.SharedPreferenceUtil;
//import com.easemob.EMCallBack;
//import com.easemob.chat.EMChat;
//import com.easemob.chat.EMChatManager;
//import com.easemob.chat.EMChatOptions;
//import com.easemob.chatuidemo.DemoHXSDKHelper;
//import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
//import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
//import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
//import com.nostra13.universalimageloader.utils.StorageUtils;
//import com.tencent.mm.sdk.openapi.IWXAPI;
//import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yeebee.invest.utils.SharedPreferenceUtil;

public class BaseApplication extends FrontiaApplication {
	
	public static String PhoneIMEI = "0";
	public static String device_model;
	public static String userId;
    private static String PREF_NAME = "creativelocker.pref";
	public static Context context;
	static Context _context;
	private static Handler handler;
	private LinkedList<Activity> activityList = new LinkedList<Activity>();
	public static BaseApplication instance;
	
	public MyLocationListener mMyLocationListener;
//	public GeofenceClient mGeofenceClient;
	
	//定位
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	public Vibrator mVibrator;
	public static double latitude;  //纬度
	public static double lontitude;  // 经度
	
	/**
	 * 默认标题壁纸
	 */
	public Bitmap mDefault_TitleWallpager;
	/**
	 * 标题壁纸名称
	 */
	public String[] mTitleWallpagersName;
	
	/**
	 * 标题壁纸缓存
	 */
	public HashMap<String, SoftReference<Bitmap>> mTitleWallpagersCache = new HashMap<String, SoftReference<Bitmap>>();

	/**
	 * 当前壁纸编号
	 */
	public int mWallpagerPosition = 0;
		
	
	private static final String TAG= BaseApplication.class.getSimpleName();

   
//    private ImageLoader imageLoader;
    
//    public static DemoHXSDKHelper hxSDKHelper = new DemoHXSDKHelper();

    SharedPreferenceUtil mSharedPreferenceUtil;
    
	
	public BaseApplication() {
	}

	public static BaseApplication getInstance() {
		if (null == instance) {
			instance = new BaseApplication();
		}
		return instance;
	}
	
	public static synchronized BaseApplication context() {
		return (BaseApplication) _context;
	}

	public void addActivity(Activity activity) {
		activityList.add(activity);
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		SDKInitializer.initialize(getApplicationContext());
		context = getBaseContext();
		_context = getApplicationContext();
		handler = new Handler();
		
//		File cacheDir = StorageUtils.getCacheDirectory(getApplicationContext());
//		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
//				getApplicationContext())
//				.threadPriority(Thread.NORM_PRIORITY - 2).threadPoolSize(3)
//				.memoryCacheSize(getMemoryCacheSize(getApplicationContext()))
//				.denyCacheImageMultipleSizesInMemory()
//				.discCache(new UnlimitedDiscCache(cacheDir))
//				.discCacheFileNameGenerator(new Md5FileNameGenerator())
//				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
//		ImageLoader.getInstance().init(config);
		
		try {
			mTitleWallpagersName = getAssets().list("title_wallpager");
		} catch (IOException e) {
			e.printStackTrace();
		}
		//定位
//		mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
//	    mLocationClient.registerLocationListener( myListener );    //注册监听函数		
//	    initLocation();
//	    mLocationClient.start();
//	    mLocationClient.requestLocation();
	    
//	    final IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);
//
//		// 将该app注册到微信
//		msgApi.registerApp(Constants.APP_ID);
//
//		JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
//        JPushInterface.init(this);     		// 初始化 JPush
        
        
        mSharedPreferenceUtil = new SharedPreferenceUtil(
				this, "userInfo");
		userId = mSharedPreferenceUtil.getUserId();
        PhoneIMEI =mSharedPreferenceUtil.getIMEI(getBaseContext());
        device_model = Build.MODEL;
        
//        //环信 V3.1.4
//        EMOptions options = new EMOptions();
//        // 默认添加好友时，是不需要验证的，改成需要验证
// //       options.setAcceptInvitationAlways(false);
//        //初始化
//        EMClient.getInstance().init(getApplicationContext(), options);
//        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
//        EMClient.getInstance().setDebugMode(true);
        
     // 初始化环信SDK  2.x
//     		Log.d("DemoApplication", "Initialize EMChat SDK");
     //		SpeechUtility.createUtility(MyApplication.this, SpeechConstant.APPID +"=546db2f3");
//     		EMChat.getInstance().init(getApplicationContext());
//
//     		// 获取到EMChatOptions对象
//     		EMChatOptions options = EMChatManager.getInstance().getChatOptions();
//     		// 默认添加好友时，是不需要验证的，改成需要验证
//     		options.setAcceptInvitationAlways(false);
//     		// 设置收到消息是否有新消息通知，默认为true
//     		options.setNotificationEnable(false);
//     		// 设置收到消息是否有声音提示，默认为true
//     		options.setNoticeBySound(false);
//     		// 设置收到消息是否震动 默认为true
//     		options.setNoticedByVibrate(false);
//     		// 设置语音消息播放是否设置为扬声器播放 默认为true
//     		options.setUseSpeaker(false);
     		
//     		hxSDKHelper.onInit(getApplicationContext());
							
	}
	
	private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span= 0;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }
	
	/**
	 * 实现实位回调监听
	 */	                      
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			//Receive Location 
			latitude = location.getLatitude();
			lontitude = location.getLongitude();
//			System.out.println("MyApplication  latitude =  " + latitude + "\n lontitude  = " + lontitude);
//			StringBuffer sb = new StringBuffer(256);
//			sb.append("time : ");
//			sb.append(location.getTime());
//			sb.append("\nerror code : ");
//			sb.append(location.getLocType());
//			sb.append("\nlatitude : ");
//			sb.append(location.getLatitude());
//			sb.append("\nlontitude : ");
//			sb.append(location.getLongitude());
//			sb.append("\nradius : ");
//			sb.append(location.getRadius());
//			if (location.getLocType() == BDLocation.TypeGpsLocation){
//				sb.append("\nspeed : ");
//				sb.append(location.getSpeed());
//				sb.append("\nsatellite : ");
//				sb.append(location.getSatelliteNumber());
//				sb.append("\ndirection : ");
//				sb.append("\naddr : ");
//				sb.append(location.getAddrStr());
//				sb.append(location.getDirection());
//			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
//				sb.append("\naddr : ");
//				sb.append(location.getAddrStr());
//				//运营商信息
//				sb.append("\noperationers : ");
//				sb.append(location.getOperators());
//			}
//			logMsg(sb.toString());
//			System.out.println("````````````2" + sb.toString());
//			Log.i("BaiduLocationApiDem", sb.toString());
		}
	}

	public static void showToast(final int resId) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
			}
		});
	}

	public static void showToast(final String text) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	public int getMemoryCacheSize(Context context) {
		int memoryCacheSize;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
			int memClass = ((ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE))
					.getMemoryClass();
			memoryCacheSize = (memClass / 8) * 1024 * 1024; // 1/8 of app memory
															// limit
		} else {
			memoryCacheSize = 2 * 1024 * 1024;
		}
		return memoryCacheSize;
	}
	
	/**
	 * 根据壁纸编号获取标题壁纸
	 */
	public Bitmap getTitleWallpager(int position) {
		try {
			String titleWallpagerName = mTitleWallpagersName[position];
			Bitmap bitmap = null;
			if (mTitleWallpagersCache.containsKey(titleWallpagerName)) {
				SoftReference<Bitmap> reference = mTitleWallpagersCache
						.get(titleWallpagerName);
				bitmap = reference.get();
				if (bitmap != null) {
					return bitmap;
				}
			}
			bitmap = BitmapFactory.decodeStream(getAssets().open(
					"title_wallpager/" + titleWallpagerName));
			mTitleWallpagersCache.put(titleWallpagerName,
					new SoftReference<Bitmap>(bitmap));
			return bitmap;
		} catch (Exception e) {
			return mDefault_TitleWallpager;
		}
	}
	
	public static boolean get(String key, boolean defValue) {
		return getPreferences().getBoolean(key, defValue);
	}
	
	 public static SharedPreferences getPreferences() {
		SharedPreferences pre = context.getSharedPreferences(PREF_NAME,
			Context.MODE_MULTI_PROCESS);
		return pre;
	 }
	 
	 //环信 
	 
//	 /**
//	 * 获取当前登陆用户名
//	 *
//	 * @return
//	 */
//	public String getUserName() {
//	    return hxSDKHelper.getHXId();
//	}
//
//	/**
//	 * 获取密码
//	 *
//	 * @return
//	 */
//	public String getPassword() {
//		return hxSDKHelper.getPassword();
//	}
//
//	/**
//	 * 设置用户名
//	 *
//	 * @param user
//	 */
//	public void setUserName(String username) {
//	    hxSDKHelper.setHXId(username);
//	}
//
//	/**
//	 * 设置密码 下面的实例代码 只是demo，实际的应用中需要加password 加密后存入 preference 环信sdk
//	 * 内部的自动登录需要的密码，已经加密存储了
//	 *
//	 * @param pwd
//	 */
//	public void setPassword(String pwd) {
//	    hxSDKHelper.setPassword(pwd);
//	}
//
//	/**
//	 * 退出登录,清空数据
//	 */
//	public void logout(final boolean isGCM,final EMCallBack emCallBack) {
//		// 先调用sdk logout，在清理app中自己的数据
//	    hxSDKHelper.logout(isGCM,emCallBack);
//	}
			
}
