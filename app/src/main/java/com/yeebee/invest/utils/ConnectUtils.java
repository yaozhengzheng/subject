package com.yeebee.invest.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import android.util.Log;

import com.yeebee.invest.BaseApplication;
import com.yeebee.invest.domain.MyAppData;


/**
 * 网络连接类
 * 
 * @author 
 * 
 */

public class ConnectUtils {

	private static final String CHARSET = HTTP.UTF_8;
	private static HttpClient httpClient;
	public static final int CODE_200 = 200;
	public static final int CODE_404 = 404;
	public static final int CODE_500 = 500;
	public static final int SO_TIMEOUT = 5 * 1000;
	public static final int CONNECTION_TIMEOUT = 5 * 1000;
	
	
	public static HttpClient getHttpClient() {
		if (null == httpClient) {
			HttpParams params = new BasicHttpParams();
			// 设置一些基本参数
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, CHARSET);
			HttpProtocolParams.setUseExpectContinue(params, true);
			HttpProtocolParams
					.setUserAgent(
							params,
							"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
									+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
			// 超时设置
			/* 从连接池中取连接的超时时间 */
			ConnManagerParams.setTimeout(params, 20000);
			/* 连接超时 */
			HttpConnectionParams.setConnectionTimeout(params, 20000);
			/* 请求超时 */
			HttpConnectionParams.setSoTimeout(params, 20000);
			HttpConnectionParams.setSocketBufferSize(params, 1024);

			// 设置我们的HttpClient支持HTTP和HTTPS两种模式
			SchemeRegistry schReg = new SchemeRegistry();
			schReg.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			// schReg.register(new Scheme("https",
			// SSLSocketFactory.getSocketFactory(), 443));

			// 使用线程安全的连接管理来创建HttpClient
			ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
					params, schReg);
			httpClient = new DefaultHttpClient(conMgr, params);
		}
		return httpClient;
	}

	// 以流的方式获取数据
	public static InputStream httpPost(String Url, Map<String, String> params) {

		InputStream result = null;
		System.out.println(Url);
		HttpPost post = new HttpPost(Url);
		List<NameValuePair> list = new ArrayList<NameValuePair>();

		// 构建表单字段内容
		// list.add(new BasicNameValuePair("do", args));
		for (Entry<String, String> entry : params.entrySet()) {
			list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()
					+ ""));
		}

		System.out.println("list--------------->"+list);
		try {
			post.setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));
			HttpClient client = new DefaultHttpClient();
			client.getParams()
					.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
							CONNECTION_TIMEOUT);
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
					SO_TIMEOUT);

			HttpResponse response = client.execute(post);
			System.out.println("response--------------->"+response.getStatusLine().getStatusCode());
			// response.addHeader("Accept", "application/xml");

			if (CODE_200 == response.getStatusLine().getStatusCode()){
				// resultivan= response.getEntity().getContent().toString();
				result = response.getEntity().getContent();
				
				System.out.println("result---------------->"+result);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (null == result) {
			Log.v("webserver", "数据为null");
		} else
			Log.v("webserver", "已获取到数据");

		return result;
	}

	// 参数处理：获取服务器的参数
	public static String Post_Myparams(String M_Data, String M_Code) {	
		String MyData = "";
		String Vstr_data = "TimeTrade" + "_" + BaseApplication.PhoneIMEI + "_"
				+ M_Code;
		final String Vstr_MD5 = EncryptMd5.GetMD5Code(Vstr_data);

		Map<String, String> params = new HashMap<String, String>();
		params.put("data", M_Data);
		params.put("code", M_Code);
		params.put("usercode", BaseApplication.PhoneIMEI);
		params.put("vstr", Vstr_MD5);
		String Url = MyAppData.URL;
		InputStream m_is = httpPost(Url, params);
		
		if (m_is != null) {
			try {
				MyData = inputStream2String(m_is);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return MyData;
			
	}

	// inputStream转换成String
	public static String inputStream2String(InputStream is) throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		while ((i = is.read()) != -1) {
			baos.write(i);
		}
		return baos.toString();
	}

}
