package com.upsoft.demo.framework.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.upsoft.demo.R;
import com.upsoft.demo.framework.UploadService;
import com.upsoft.demo.framework.adapter.MainFragmentAdapter;
import com.upsoft.demo.framework.base.BaseActivity;
import com.upsoft.demo.framework.base.MainBean;
import com.upsoft.demo.framework.config.FrameworkSDK;
import com.upsoft.demo.framework.ui.LhViewPager;
import com.upsoft.demo.framework.update.UpdateManager;
import com.upsoft.demo.framework.util.AssertUtil;
import com.upsoft.sdk.Constant;
import com.upsoft.sdk.util.L;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private TabLayout mTabLayout;
    private LhViewPager mViewPager;
    private MainFragmentAdapter mainFragmentAdapter;
    private List<MainBean> list;


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        registColorReceiver();
        mTabLayout = getView(R.id.tabs);
        mViewPager = getView(R.id.vp_view);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        list = new ArrayList<>();
        list.addAll(FrameworkSDK.getDefaultConfig().getMainBeanList());
        mainFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager(), mContext, list);
        mViewPager.setAdapter(mainFragmentAdapter);
        mViewPager.setOffscreenPageLimit(5);
        mTabLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < mainFragmentAdapter.getCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);//获得每一个tab
            tab.setCustomView(mainFragmentAdapter.getTabView(i));//给每一个tab设置view
        }
        mTabLayout.setSelectedTabIndicatorHeight(0);
        mTabLayout.setBackgroundColor(Color.WHITE);
        setTabColor();
        ((TextView) mTabLayout.getTabAt(0).getCustomView().findViewById(R.id.tv)).setTextColor(Color.parseColor
                (textIconColor));

        ((ImageView) mTabLayout.getTabAt(0).getCustomView().findViewById(R.id.iv)).setImageResource(list.get(0)
                .selectIconId);
        ((ImageView) mTabLayout.getTabAt(0).getCustomView().findViewById(R.id.iv)).setColorFilter(Color
                .parseColor(textIconColor));
//        ((ImageView) mTabLayout.getTabAt(0).getCustomView().findViewById(R.id.iv)).setAlpha(alpha);
    }

    private void setTabColor() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ((TextView) tab.getCustomView().findViewById(R.id.tv)).setTextColor(Color.parseColor(textIconColor));
                ((ImageView) tab.getCustomView().findViewById(R.id.iv)).setImageResource(list.get(tab.getPosition())
                        .selectIconId);
                ((ImageView) tab.getCustomView().findViewById(R.id.iv)).setColorFilter(Color
                        .parseColor(textIconColor));
//                ((ImageView) tab.getCustomView().findViewById(R.id.iv)).setAlpha(alpha);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ((TextView) tab.getCustomView().findViewById(R.id.tv)).setTextColor(list.get(tab.getPosition())
                        .unSelectTextColor);
                ((ImageView) tab.getCustomView().findViewById(R.id.iv)).setImageResource(list.get(tab.getPosition())
                        .unSelectIconId);
                ((ImageView) tab.getCustomView().findViewById(R.id.iv)).setColorFilter(null);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void getData() {
        new UpdateManager(mContext, false).checkUpdate();
        uploadLog();
        copyAssertToLocal();
    }

    private void copyAssertToLocal() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AssertUtil.copyAssetsFile2Phone(mActivity, Constant.WEEX_PATH,"RichText.js");
                AssertUtil.copyAssetsFile2Phone(mActivity, Constant.WEEX_PATH,"ic_weex.png");
            }
        });

    }

    @Override
    public void onClick(View v) {

    }

    private ColorReceiver colorReceiver;

    private class ColorReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String color = intent.getStringExtra(IndividualizationActivity.KEY_TEXT_ICON_COLOR);
            L.e(MainActivity.this.getClass().getName() + "接收到标题栏颜色变化通知color=" + color);
            textIconColor = color;
            setTabColor();
            int position = mTabLayout.getSelectedTabPosition();
            ((ImageView) mTabLayout.getTabAt(position).getCustomView().findViewById(R.id.iv)).setColorFilter(Color
                    .parseColor(color));
            ((TextView) mTabLayout.getTabAt(position).getCustomView().findViewById(R.id.tv)).setTextColor(Color
                    .parseColor(color));
        }
    }

    private void registColorReceiver() {
        colorReceiver = new ColorReceiver();
        IntentFilter intentFilter = new IntentFilter(IndividualizationActivity.ACTION_TITLE_COLOR_CHANGE);
        registerReceiver(colorReceiver, intentFilter);
    }

    private void unRegistColorReceiver() {
        if (colorReceiver != null) {
            unregisterReceiver(colorReceiver);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegistColorReceiver();
    }


    /**
     * 返回退出
     */
    private long firstTime = 0;

    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long time = System.currentTimeMillis();
            if (time - firstTime > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
                firstTime = time;
                return true;
            } else {
                //退出相关操作
                System.exit(0);
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void uploadLog(){
        startService(new Intent(mContext, UploadService.class));
    }
}
