package com.yeebee.invest.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by DRL on 2016/8/19.
 */
public class SharedPreferenceUtil {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    public SharedPreferenceUtil(Context context , String file){
        sp = context.getSharedPreferences(file, context.MODE_PRIVATE);
        editor = sp.edit();
    }

    /**
     * 保存密码
     * @param pwd
     */
    public void setPasswd(String pwd){
        editor.putString("pwd", pwd);
        editor.commit();
    }

    /**
     * 读取密码
     * @return
     */
    public String getPasswd(){
        return sp.getString("pwd", "");
    }

    /**
     * 保存用户昵称
     * @param nickName
     */
    public void setNickName(String nickName){
        editor.putString("nickName", nickName);
        editor.commit();
    }

    /**
     * 读取用户昵称
     * @return
     */
    public String getNickName(){
        return sp.getString("nickName", "");
    }

    /**
     * 保存用户真实姓名
     * @param name
     */
    public void setName(String name){
        editor.putString("name", name);
        editor.commit();
    }

    /**
     * 获取用户真实姓名
     * @return
     */
    public String getName(){
        return sp.getString("name","");
    }

    /**
     * 保存用户联系方式（手机）
     * @param tel
     */
    public void setPhone(String tel){
        editor.putString("tel", tel);
        editor.commit();
    }

    /**
     * 读取用户联系方式（手机
     * @return
     */
    public String getPhone(){
        return sp.getString("tel", "");
    }

    /**
     * 保存用户ID
     * @param id
     */
    public void setUserId(String id){
        editor.putString("id", id);
        editor.commit();
    }

    /**
     * 读取用户ID
     * @return
     */
    public String getUserId(){
        return sp.getString("id", "0");
    }

    /**
     * 设置用户是否是第一次使用程序
     * @param isFirst
     */
    public void setIsFirst(boolean isFirst){
        editor.putBoolean("isFirst", isFirst);
        editor.commit();
    }

    /**
     * 读取用户是否是第一次使用程序
     * @return
     */
    public boolean getIsFirst(){
        return sp.getBoolean("isFirst", true);
    }

    /**
     * 设置用户是否登陆
     * @param isLogin
     */
    public void setIsLogin(boolean isLogin) {
        editor.putBoolean("isLogin", isLogin);
        editor.commit();
    }

    /**
     * 读取用户是否登陆
     * @return
     */
    public boolean getIsLogin() {
        return sp.getBoolean("isLogin", false);
    }

    /**
     * 设置用户性别
     * @param gender 0没有（默认） 1 男 2 女
     */
    public void setGender(int gender) {
        editor.putInt("gender", gender);
        editor.commit();
    }

    /**
     * 读取用户性别
     * @return
     */
    public int getGender() {
        return sp.getInt("gender", 0);
    }

    /**
     * 设置用户出生年份
     * @param year
     */
    public void setUserYear(int year) {
        editor.putInt("year", year);
        editor.commit();
    }

    /**
     * 读取用户出生年份
     * @return
     */
    public int getUserYear() {
        return sp.getInt("year", 0);
    }

    /**
     * 设置用户出生月份
     * @param year
     */
    public void setUserMonth(int year) {
        editor.putInt("month", year);
        editor.commit();
    }

    /**
     * 读取用户出生月份
     * @return
     */
    public int getUserMonth() {
        return sp.getInt("month", 0);
    }

    /**
     * 设置用户出生日
     * @param year
     */
    public void setUserDay(int year) {
        editor.putInt("day", year);
        editor.commit();
    }

    /**
     * 读取用户出生日
     * @return
     */
    public int getUserDay() {
        return sp.getInt("day", 0);
    }

    /**
     * 设置是否显示地理位置
     * @param isDisplayLocation
     */
    public void setIsDisplayLocation(boolean isDisplayLocation) {
        editor.putBoolean("isDisplay", isDisplayLocation);
        editor.commit();
    }

    /**
     * 读取是否显示地理位置
     * @return
     */
    public boolean getIsDisplayLocation() {
        return sp.getBoolean("isDisplay", false);
    }

    /**
     * 根据IP获得MAC地址
     * @param context
     * @return
     */
    public static String getLocalMacAddressFromIp(Context context) {
        String mac_s = "";
        try {
            byte[] mac;
            NetworkInterface ne = NetworkInterface.getByInetAddress(InetAddress.getByName(getLocalIpAddress()));
            mac = ne.getHardwareAddress();
            mac_s = byte2hex(mac);
        }catch (Exception e){
            e.printStackTrace();
        }
        return mac_s;
    }

    public static String getLocalIpAddress(){
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
            ex.printStackTrace();
        }
        return null;
    }

    public static String byte2hex(byte[] b) {
        StringBuffer hs = new StringBuffer(b.length);
        String stmp = "";
        int len = b.length;
        for (int n = 0; n < len; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            if (stmp.length() == 1)
                hs = hs.append("0").append(stmp);
            else {
                hs = hs.append(stmp);
            }
        }
        return String.valueOf(hs);
    }

    public static String getIMEI(Context context){
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String phoneIMEI = tm.getDeviceId();
        return phoneIMEI;
    }
}
