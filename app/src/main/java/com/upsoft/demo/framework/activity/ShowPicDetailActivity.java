package com.upsoft.demo.framework.activity;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.upsoft.demo.R;
import com.upsoft.demo.framework.base.BaseActivity;
import com.upsoft.demo.framework.ui.HackyViewPager;
import com.upsoft.sdk.image.loader.ImageLoader;
import com.upsoft.sdk.util.L;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * Copyright (c) 2017,重庆扬讯软件技术有限公司<br>
 * All rights reserved.<br>
 * <p/>
 * 文件名称：EpApp<br>
 * 摘要：简要描述本文件的内容<br>
 * -------------------------------------------------------<br>s
 * 当前版本：1.1.1<br>
 * 作者：彭粟<br>
 * 完成日期：2017/3/7<br>
 * -------------------------------------------------------<br>
 * 取代版本：1.1.0<br>
 * 原作者：彭粟<br>
 * 完成日期：2017/3/7<br>
 */

public class ShowPicDetailActivity extends BaseActivity {

    private ViewPager mViewPager;
    /**
     * 存放图片地址的数组
     */
    private String[] images;
    private SamplePagerAdapter mAdapter;
    /**
     * 装导航点的父布局
     */
    private LinearLayout bottomLL;
    /**
     * 小点点的个数
     */
    private int count;
    /**
     * 存放导航点的数组
     */
    private ImageView[] imageViews;
    /**
     * viewpager的位置
     */
    private int mPosition;
    /**
     * 初始位置
     */
    private int position;


    @Override
    public int getLayoutId() {
        return R.layout.activity_show_pic;
    }

    @Override
    public void initView() {
        setTransparent();
        mViewPager = (HackyViewPager) this.findViewById(R.id.hackyviewpagerId);
        bottomLL = (LinearLayout) this.findViewById(R.id.linearlayout_bottomId);
        mViewPager.addOnPageChangeListener(new MyListener());
        images = getIntent().getStringArrayExtra("imageUrl");
        position = getIntent().getIntExtra("position", 0);
        count = images.length;
        makePointView(count);
        mAdapter = new SamplePagerAdapter(mContext, images);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(position, false);
    }

    @Override
    public void getData() {

    }


    @Override
    public void onClick(View v) {

    }

    private class SamplePagerAdapter extends PagerAdapter {

        private LayoutInflater mLayoutInflater;
        private String[] imageUrl;
        private Context context;


        public SamplePagerAdapter(Context context, String[] imageUrl) {
            this.context = context;
            this.imageUrl = imageUrl;
            mLayoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            if (imageUrl != null && imageUrl.length > 0) {
                return imageUrl.length;
            } else {
                return 0;
            }
            // return sDrawables.length;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());

//            ImageLoader.with(mContext).url(imageUrl[position]).into(photoView);k
//            photoView.setImageURI(Uri.parse(imageUrl[position]));
            String url = imageUrl[position];
            if (url != null && url.startsWith("http")) {
                ImageLoader.with(mContext).url(imageUrl[position]).into(photoView);
            } else {
                ImageLoader.with(mContext).file(imageUrl[position]).into(photoView);
            }
            Glide.with(mContext).load(imageUrl[position]).into(photoView);
            L.e("图片地址===" + imageUrl[position]);
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float v, float v1) {
                    ShowPicDetailActivity.this.finish();
                }
            });
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }


    /**
     * 生成小点点view
     *
     * @param count 小点点的个数
     * @创建时间：2015-6-8 上午11:43:15
     * @作者： 龙辉
     * @描述: TODO
     * @retrun void
     */
    private void makePointView(int count) {
        if (count > 0) {
            imageViews = new ImageView[count];
            for (int i = 0; i < count; i++) {
                ImageView iv = new ImageView(mContext);
                LinearLayout.LayoutParams ll1 = new LinearLayout.LayoutParams(10, 10);
                ll1.setMargins(0, 0, 10, 0);
                iv.setLayoutParams(ll1);
                imageViews[i] = iv;
                if (i == position) {
                    imageViews[i].setBackgroundResource(R.drawable.circle_point_white);
                } else {
                    imageViews[i].setBackgroundResource(R.drawable.circle_point_grey);
                }
                bottomLL.addView(iv);
            }
        }
    }

    /**
     * @author 龙辉
     * @version 1.0
     * @Description: 定位点的位置
     * @time:2015-4-17 上午10:01:09
     * @modification:
     */
    public void makesurePosition(int count) {
        mPosition = mViewPager.getCurrentItem();
        if (count > 0) {
            for (int j = 0; j < count; j++) {
                if (mPosition == j) {
                    imageViews[mPosition].setBackgroundResource(R.drawable.circle_point_white);
                } else {
                    imageViews[j].setBackgroundResource(R.drawable.circle_point_grey);
                }
            }
        }
    }

    // 自定义监听
    private class MyListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int arg0) {
            // TODO Auto-generated method stub
            makesurePosition(count);
        }

    }


}

