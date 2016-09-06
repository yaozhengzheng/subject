package com.yeebee.invest.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.yeebee.invest.R;


public class DialogUtils {
	
	/**
	 * 含有内容、两个按钮的对话框 
	 * @param context
	 * @param message
	 * @param listener
	 */
	public static void dialogMsgAndTwoBtn(Context context, String message, OnClickListener listener){
		final AlertDialog dlg = new AlertDialog.Builder(context).create();
		dlg.show();
		Window window = dlg.getWindow();
		window.setContentView(R.layout.common_msgandtwobtn_dialog);
		
		TextView tv_dialog_message = (TextView) window.findViewById(R.id.tv_dialog_message);
		tv_dialog_message.setText(message);
		Button btn_cancle = (Button) window.findViewById(R.id.btn_dialog_cancle);		
		Button btn_ok = (Button) window.findViewById(R.id.btn_dialog_ok);
		
		btn_cancle.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				dlg.dismiss();
			}
		});
		
		btn_ok.setOnClickListener(listener);

	}
	
	
	/**
	 * 只含有内容的对话框
	 */
	
	public static void dialogMsg(Context context, String message){
		final AlertDialog dlg = new AlertDialog.Builder(context).create();
		dlg.show();
		Window window = dlg.getWindow();
		window.setContentView(R.layout.common_msg_dialog);
		TextView tv_dialog_message = (TextView) window.findViewById(R.id.tv_msg_dialog);
		tv_dialog_message.setText(message);
		try {
			Thread.sleep(2000);
			dlg.dismiss();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void showInfoDialog(Context context, String message) {
		showInfoDialog(context, message, "提示", "确定", null);
	}
	
	public static void showInfoDialog(Context context, String message,
			String titleStr, String positiveStr,
			DialogInterface.OnClickListener onClickListener) {
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(context);
		localBuilder.setTitle(titleStr);
		localBuilder.setMessage(message);
		if (onClickListener == null)
			onClickListener = new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

				}
			};
		localBuilder.setPositiveButton(positiveStr, onClickListener);
		localBuilder.show();
	}
	
}
