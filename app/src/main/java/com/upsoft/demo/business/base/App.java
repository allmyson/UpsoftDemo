package com.upsoft.demo.business.base;

import android.app.Application;
import android.util.Log;

import com.taobao.weex.InitConfig;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;
import com.taobao.weex.utils.LogLevel;
import com.upsoft.demo.R;
import com.upsoft.demo.business.fragment.HomeFragment;
import com.upsoft.demo.framework.base.MainBean;
import com.upsoft.demo.framework.base.MultiDexApplication;
import com.upsoft.demo.framework.bean.NetworkBean;
import com.upsoft.demo.framework.bean.PlugInfo;
import com.upsoft.demo.framework.config.FrameworkSDK;
import com.upsoft.demo.framework.fragment.MyFragment;
import com.upsoft.demo.framework.fragment.PluginFragment;
import com.upsoft.demo.framework.weex.module.UpsoftModule;
import com.upsoft.sdk.image.loader.ImageLoader;
import com.upsoft.sdk.weex.adapter.ImageAdapter;
import com.upsoft.sdk.weex.adapter.OkHttpAdapter;
import com.vise.log.ViseLog;
import com.vise.log.inner.ConsoleTree;
import com.vise.log.inner.LogcatTree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename App
 * @description -------------------------------------------------------
 * @date 2018/8/29 17:46
 */
public class App extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        //初始化FrameworkSDK
        FrameworkSDK.getDefaultConfig().init(this)
                .setProjectName("UpsoftDemo")//设置项目名称，文件夹会以此命名
                .setWelcomeBg(R.mipmap.bg_welcome)//设置欢迎界面背景
                .setMainBeanList(getMainBeanList())//设置主页tab选项
                .setMyPlugList(getMyPlugList())//设置我的-插件模块
                .setNetList(getNetList())//设置网络配置
                .cacheColorToMemory(true)//默认为true,建议缓存
                .isShowLog(true);//是否显示log信息到Logcat
        initWeex(this);//初始化Weex
        initViseLog();//初始化Log框架
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        ImageLoader.trimMemory(level);
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        ImageLoader.clearAllMemoryCaches();
    }

    private List<PlugInfo> getMyPlugList() {
        List<PlugInfo> list = new ArrayList<>();
        list.add(new PlugInfo(R.mipmap.ic_xgxz, "相关下载"));
        list.add(new PlugInfo(R.mipmap.ic_hcql, "缓存清理"));
        list.add(new PlugInfo(R.mipmap.ic_update, "检查更新"));
        list.add(new PlugInfo(R.mipmap.ic_about, "关于"));
        list.add(new PlugInfo(R.mipmap.ic_wlpz, "网络配置"));
        return list;
    }


    private List<MainBean> getMainBeanList() {
        List<MainBean> list = new ArrayList<>();
        MainBean mainBean1 = new MainBean();
        mainBean1.title = "首页";
        mainBean1.selectIconId = R.mipmap.ic_home_select;
        mainBean1.unSelectIconId = R.mipmap.ic_home;
        mainBean1.fragment = HomeFragment.newInstance();
        mainBean1.selectTextColor = getResources().getColor(R.color.main_color);
        mainBean1.unSelectTextColor = getResources().getColor(R.color.main_gray);
        list.add(mainBean1);


        MainBean mainBean2 = new MainBean();
        mainBean2.title = "应用";
        mainBean2.selectIconId = R.mipmap.ic_plugin_select;
        mainBean2.unSelectIconId = R.mipmap.ic_plugin;
        mainBean2.fragment = PluginFragment.newInstance();
        mainBean2.selectTextColor = getResources().getColor(R.color.main_color);
        mainBean2.unSelectTextColor = getResources().getColor(R.color.main_gray);
        list.add(mainBean2);


        MainBean mainBean3 = new MainBean();
        mainBean3.title = "我的";
        mainBean3.selectIconId = R.mipmap.ic_mine_select;
        mainBean3.unSelectIconId = R.mipmap.ic_mine;
        mainBean3.fragment = MyFragment.newInstance();
        mainBean3.selectTextColor = getResources().getColor(R.color.main_color);
        mainBean3.unSelectTextColor = getResources().getColor(R.color.main_gray);
        list.add(mainBean3);

        return list;
    }

    private List<NetworkBean> getNetList() {
        List<NetworkBean> list = new ArrayList<>();
        list.add(new NetworkBean("测试环境", "172.16.19.1"));
        list.add(new NetworkBean("正式环境", "172.16.19.1"));
        list.add(new NetworkBean("开发环境", "192.168.0.212"));
        return list;
    }


    private void initViseLog() {
        ViseLog.getLogConfig()
                .configAllowLog(true)//是否输出日志
                .configShowBorders(true)//是否排版显示
                .configTagPrefix("UpsoftLog")//设置标签前缀
                .configFormatTag("%d{HH:mm:ss:SSS} %t %c{-5}")//个性化设置标签，默认显示包名
                .configLevel(Log.VERBOSE);//设置日志最小输出级别，默认Log.VERBOSE
        ViseLog.plant(new LogcatTree());//添加打印日志信息到Logcat的树
        ViseLog.plant(new ConsoleTree());
//        ViseLog.plant(new FileTree(this, "upsoft_demo"));//添加打印日志信息到Logcat的树
    }

    private void initWeex(Application context) {

        WXSDKEngine.initialize(context, new InitConfig.Builder()
                .setImgAdapter(new ImageAdapter(context))
                .setHttpAdapter(new OkHttpAdapter(context))
                .build());
        WXEnvironment.sLogLevel = LogLevel.ALL;

        try {
            WXSDKEngine.registerModule("upsoft", UpsoftModule.class);
        } catch (WXException e) {
            e.printStackTrace();
        }
    }
}
