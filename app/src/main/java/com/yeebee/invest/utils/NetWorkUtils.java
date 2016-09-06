package com.yeebee.invest.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.util.Log;

public class NetWorkUtils {
	
//	Context context;
	public static boolean isNetWorkAvaliable(Context context){
		
		ConnectivityManager connect = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(connect == null){
			return false;
		}else{
			NetworkInfo[] info = connect.getAllNetworkInfo();
			if(info != null){
				for(int i=0;i<info.length;i++){
					if(info[i].getState() == NetworkInfo.State.CONNECTED){
						//网络可用
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static void setNetwork(final Context context){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
				context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
				
				
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
	
	
	public static String getIpAddress(Context context) {
		
		ConnectivityManager connect = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		//检查网络连接   
		NetworkInfo info = connect.getActiveNetworkInfo(); 
		
		int netType = info.getType(); 
		int netSubtype = info.getSubtype(); 

		if (netType == ConnectivityManager.TYPE_WIFI) {  //WIFI
			return getWifiIpAddress(context);
		} else if (netType == ConnectivityManager.TYPE_MOBILE ) {   //MOBILE
			return getLocalIpAddress();
		} else { 
		   return "127.0.0.1"; 
		}	
	}
	
	//使用wifi
	public static String getWifiIpAddress(Context context) {
		
		//获取wifi服务
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		//判断wifi是否开启  
//        if (!wifiManager.isWifiEnabled()) {  
//        	wifiManager.setWifiEnabled(true);    
//        }
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		int ipAddress = wifiInfo.getIpAddress();
		String ip = intToIp(ipAddress);
		return ip;
	}
	
	private static String intToIp(int i) {
		return (i & 0xFF ) + "." +
		((i >> 8 ) & 0xFF) + "." +
		((i >> 16 ) & 0xFF) + "." +
		( i >> 24 & 0xFF) ;
	}
	
	//使用GPRS
	public static String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
			Log.e("Exception", ex.toString());
		}
		return "127.0.0.1";
	}

}
