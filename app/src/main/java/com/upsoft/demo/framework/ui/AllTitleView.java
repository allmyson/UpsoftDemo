package com.upsoft.demo.framework.ui;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.upsoft.demo.R;


/**
 * Copyright (c) 2015,重庆扬讯软件技术有限公司<br>
 * All rights reserved.<br>
 * <p/>
 * 文件名称：AllTitleView<br>
 * 摘要：<br>
 * -------------------------------------------------------<br>
 * 当前版本：1.1.1<br>
 * 作者：李杰<br>
 * 完成日期：2017/10/9<br>
 * -------------------------------------------------------<br>
 * 取代版本：1.1.0<br>
 * 原作者：李杰<br>
 * 完成日期：2017/10/9<br>
 */


public class AllTitleView extends LinearLayout {

    private static Context mContext;
    //title布局
    private View mAllView;
    //左右layout
    private LinearLayout mLeftLayout, mRightLayout;
    //居中文字
    private TextView mCenterText;
    //左右文字
    private TextView mLeftText, mRightText;
    //左右图标
    private ImageView mLeftImage, mRightImage;
    //回调接口
    private ITitleViewOnClick titleOnClickListener;

    private LinearLayout parentLL;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public AllTitleView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public AllTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public AllTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initView() {
        mAllView = LayoutInflater.from(mContext).inflate(R.layout.alltitle_layout, this);
        parentLL = (LinearLayout) mAllView.findViewById(R.id.ll_parent);
        mLeftLayout = (LinearLayout) mAllView.findViewById(R.id.common_ll_leftLayout);
        mRightLayout = (LinearLayout) mAllView.findViewById(R.id.common_ll_rightLayout);
        mCenterText = (TextView) mAllView.findViewById(R.id.common_tv_title);
        mLeftText = (TextView) mAllView.findViewById(R.id.common_tv_leftText);
        mRightText = (TextView) mAllView.findViewById(R.id.common_tv_rightText);
        mLeftImage = (ImageView) mAllView.findViewById(R.id.common_im_leftImage);
        mRightImage = (ImageView) mAllView.findViewById(R.id.common_im_rightImage);
        // 左点击回调
        mLeftLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                titleOnClickListener.backLeft();
            }
        });
        //右点击回调
        mRightLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                titleOnClickListener.backRight();
            }
        });
//        addView(mAllView);
    }

    /**
     * 设置背景色
     * @param color
     */
    public void setBackgroundColor(int color){
        parentLL.setBackgroundColor(color);
    }

    /**
     * 左边文字设置
     *
     * @param text
     */
    public void setLeftText(String text) {
        mLeftText.setVisibility(View.VISIBLE);
        mLeftImage.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(text)) {
            mLeftText.setText(text);

        }
    }


    /**
     * 左边图片设置
     *
     * @param path
     */
    public void setLeftImg(int path) {
        mLeftText.setVisibility(View.GONE);
        mLeftImage.setVisibility(View.VISIBLE);
        mLeftImage.setImageResource(path);
    }

    /**
     * 右边文字设置
     *
     * @param text
     */
    public void setRightText(String text) {
        mRightText.setVisibility(View.VISIBLE);
        mRightImage.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(text)) {
            mRightText.setText(text);
        }
    }

    /**
     * 右边图片设置
     *
     * @param path
     */
    public void setRightImg(int path) {
        mRightText.setVisibility(View.GONE);
        mRightImage.setVisibility(View.VISIBLE);
        mRightImage.setImageResource(path);
    }


    /**
     * 中间文字设置
     *
     * @param text
     */
    public void setCenterText(String text) {
        mCenterText.setText(text);
    }


    /**
     * 设置回调接口
     *
     * @param titleOnClickListener
     */
    public void setTitleOnClickListener(ITitleViewOnClick titleOnClickListener) {
        this.titleOnClickListener = titleOnClickListener;
    }

    /**
     * 回调
     */
    public interface ITitleViewOnClick {
        void backLeft();
        void backRight();
    }

}
