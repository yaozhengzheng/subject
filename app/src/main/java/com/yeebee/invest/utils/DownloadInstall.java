package com.yeebee.invest.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.webkit.URLUtil;

public class DownloadInstall extends AsyncTask<String, Integer, String> {

	// private AlertDialog dialog;
	private ProgressDialog pdialog;
	private Context context;
	// private String currentTempFilePath = "";
	private String fileName;

	public DownloadInstall(Context context) {
		super();
		this.context = context;
	}

	@Override
	protected void onPreExecute() {
		pdialog = new ProgressDialog(context, 0);
		pdialog.setIcon(android.R.drawable.stat_sys_download);
		pdialog.setTitle("下载中...");		
		pdialog.setCancelable(false);
		pdialog.setMax(100);
		pdialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pdialog.show();
	}
	
	// doInBackground方法内部执行后台任务,不可在此方法内修改UI
	@Override
	protected String doInBackground(String... params) {
		String strURL = params[0];
		// 判断strPath是否为网络地址
		if (!URLUtil.isNetworkUrl(strURL)) {
			return null;
		}
		InputStream is = null;
		FileOutputStream fos = null;
		// 生成一个临时文件
		// File file = null;
		try {
			URL myURL = new URL(strURL);
			HttpURLConnection conn = (HttpURLConnection) myURL.openConnection();
			conn.connect();
			long total = conn.getContentLength();
			is = conn.getInputStream();
			if (is == null) {
				return null;
			}
			// 获得文件文件扩展名字符串
			String fileEx = strURL.substring(strURL.lastIndexOf(".") + 1, strURL.length()).toLowerCase();
			// 获得文件文件名字符串
			String fileNa = strURL.substring(strURL.lastIndexOf("/") + 1, strURL.lastIndexOf("."));
			fileName = fileNa + "." + fileEx;
			// myTempFile = File.createTempFile(fileEx,"."+ fileNa);
			// // 安装包文件临时路径
			// currentTempFilePath = myTempFile.getAbsolutePath();
			// file=new File(fileEx+"."+ fileNa);
			// fos = new FileOutputStream(fileEx+"."+ fileNa);
			fos = context.openFileOutput(fileName, Context.MODE_WORLD_READABLE | context.MODE_WORLD_WRITEABLE);
			// long total=is.get
			byte buf[] = new byte[1024];
			int count = 0;
			do {
				int numread = is.read(buf);
				if (numread <= 0) {
					break;
				}
				fos.write(buf, 0, numread);
				count += numread;
				// 调用publishProgress公布进度,最后onProgressUpdate方法将被执行
				publishProgress((int) ((count / (float) total) * 100));
			} while (true);
		} catch (IOException e) {
			return null;
		} finally {
			try {
				is.close();
				fos.close();
			} catch (Exception ex) {
				return null;
			}
		}
		return "success";
	}
	
	// onProgressUpdate方法用于更新进度信息
		@Override
		protected void onProgressUpdate(Integer... progresses) {
			pdialog.setProgress(progresses[0]);
		}

		// onPostExecute方法用于在执行完后台任务后更新UI,显示结果
		@Override
		protected void onPostExecute(String result) {
			pdialog.dismiss();
			if (result != null) {
				// 打开文件
				openFile();
			} else {
				AlertDialog.Builder dialog = new AlertDialog.Builder(context);
				dialog.setTitle("错误！");
				dialog.setMessage("文件下载出错！");
				dialog.setNegativeButton("关闭", new OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				dialog.show();
				dialog.setCancelable(false);
			}
		}

		// onCancelled方法用于在取消执行中的任务时更改UI
		@Override
		protected void onCancelled() {
			// progressBar.setProgress(0);
			AlertDialog.Builder dialog = new AlertDialog.Builder(context);
			dialog.setTitle("取消");
			dialog.setMessage("您已经取消操作");
			dialog.setNegativeButton("关闭", new OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			dialog.show();
			dialog.setCancelable(false);
		}

		/**
		 * 删除临时路径里的安装包
		 */
		public void delFile() {
			// 应用程序的项目数据存储路径，（data/data）
			String filePath = context.getFilesDir().getAbsolutePath();
			filePath = filePath + File.separator;// 路径加一个“/”
			File myFile = new File(filePath);
			if (myFile.exists()) {
				myFile.delete();
			}
		}

		/**
		 * 安装apk
		 * 
		 * @param
		 * @param
		 */
		protected void openFile() {
			// 应用程序的项目数据存储路径，（data/data）
			String filePath = context.getFilesDir().getAbsolutePath();
			filePath = filePath + File.separator + fileName;// 路径加一个“/”
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			Uri uri = Uri.parse("file:" + filePath);
			// Uri uri = Uri.fromFile(filePath);
			String type = "application/vnd.android.package-archive";
			intent.setDataAndType(uri, type);
			context.startActivity(intent);
			android.os.Process.killProcess(android.os.Process.myPid());
		}

}
