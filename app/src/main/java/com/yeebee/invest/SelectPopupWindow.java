package com.yeebee.invest;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 16245 on 2016/09/06.
 */
public class SelectPopupWindow extends PopupWindow{

    @BindView(R.id.tianshilun)
    TextView tv_tsl;
    @BindView(R.id.alun)
    TextView tv_al;
    @BindView(R.id.blun)
    TextView tv_bl;
    @BindView(R.id.cancel)
    TextView tv_cancel;

    private View mView;
    public SelectPopupWindow(Context context, View.OnClickListener itemsClick) {
        super(context);
        LayoutInflater inflater= (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView=inflater.inflate(R.layout.popwindows,null);
        ButterKnife.bind(this,mView);
        //设置按键监听
        tv_tsl.setOnClickListener(itemsClick);
        tv_al.setOnClickListener(itemsClick);
        tv_bl.setOnClickListener(itemsClick);
        //设置SelectPopupWindow的view
        this.setContentView(mView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.AnimBottom);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        mView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height=mView.findViewById(R.id.pop_layout).getTop();
                int y=(int) event.getY();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }
                return true;
            }
        });

    }
    //取消键
    @OnClick(R.id.cancel)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.cancel:
                dismiss();
                break;
        }
    }
}
