package com.pangdemo;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by pangw on 2018/6/12.
 */

public class BottomSelectView {
    private Context mContext;
    private List<String> list;
    private Dialog mCameraDialog;
    private OnClickItemListent listen;

    public BottomSelectView(Context context, List<String> list) {
        mContext = context;
        this.list = list;
        initView();
    }

    private void initView() {

        mCameraDialog = new Dialog(mContext, R.style.BottomDialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(mContext).inflate(
                R.layout.view_bottom_select, null);
        //初始化视图
        for(int i = 0;i<list.size();i++){
            RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.item_view_bottom_tv,null);
            TextView tv = relativeLayout.findViewById(R.id.item_view_bottom_tv);
            tv.setText(list.get(i));
            final int finalI = i;
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listen.onClick(finalI);
                }
            });
            root.addView(relativeLayout);
        }
        mCameraDialog.setContentView(root);
        Window dialogWindow = mCameraDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.width = (int) mContext.getResources().getDisplayMetrics().widthPixels; // 宽度
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();
        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
    }

    public void show() {
       if(mCameraDialog!=null){
           mCameraDialog.show();
       }
    }
    public void cancel(){
        if(mCameraDialog!=null){
            mCameraDialog.cancel();
        }
    }
    interface OnClickItemListent{
        void onClick(int postion);
    }
    public void ssetOnItemClickListent(OnClickItemListent listen){
        this.listen = listen;
    }

}
