package com.lib.utils.app.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import example.lib_utils.R;

/**
 * Created by orangeLe on 2016/7/29 0029.
 * 描述：适用于界面的顶部标题栏，已经实现了back的点击功能
 */
public class CustomTitle extends LinearLayout {

    public CustomTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title,this);
        ImageView backImg = (ImageView) findViewById(R.id.title_back_custom);
        backImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity)getContext()).finish();
            }
        });
        // 隐藏右侧的ImageView，需要使用这个控件的时候可以自己设置
        ImageView rightImg = (ImageView) findViewById(R.id.title_right_custom);
        rightImg.setVisibility(View.GONE);
    }
}
