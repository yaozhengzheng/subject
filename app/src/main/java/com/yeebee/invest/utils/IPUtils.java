package com.yeebee.invest.utils;

import com.yeebee.invest.beans.IPModel;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by DRL on 2016/8/24.
 */
public class IPUtils {
    static final String URL = "http://ip.taobao.com/service/";
    public interface IPService{

        @GET("getIpInfo.php")
        Call<IPModel> getIpMsg(@Query("ip")String ip);
    }

    static Retrofit retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();

    public static IPService ipService  = retrofit.create(IPService.class);
}
