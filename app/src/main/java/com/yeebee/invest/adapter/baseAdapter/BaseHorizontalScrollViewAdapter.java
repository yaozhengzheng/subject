package com.yeebee.invest.adapter.baseAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class BaseHorizontalScrollViewAdapter {
//	private Context mContext;
//	private LayoutInflater mInflater;
//	private List<Integer> mDatas;
//	
//	private List<Map<String,Object>> mlist = new ArrayList<Map<String, Object>>();
//	
//	DisplayImageOptions options; // DisplayImageOptions是用于设置图片显示的类
	
//	public HorizontalScrollViewAdapter(Context mContext,List mlist) {
//		
//		this.mContext = mContext;
//		this.mlist = mlist;
//		this.options = new DisplayImageOptions.Builder()
//		.displayer(new RoundedBitmapDisplayer(0, false))
//		.cacheInMemory().cacheOnDisc().build();
//		mInflater = LayoutInflater.from(mContext);
//	}

//	public HorizontalScrollViewAdapter(Context context, List<Integer> mDatas)
//	{
//		this.mContext = context;
//		mInflater = LayoutInflater.from(context);
//		this.mDatas = mDatas;
//	}

	public abstract int getCount();

	public abstract Object getItem(int position);

	public abstract long getItemId(int position);

	public abstract View getView(int position, View convertView, ViewGroup parent);
	
//
//	private abstract class ViewHolder
//	{
//		ImageView mImg;
//		TextView mText;
//	}
}
