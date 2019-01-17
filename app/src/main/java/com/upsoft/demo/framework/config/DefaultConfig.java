package com.upsoft.demo.framework.config;

import android.app.Application;

import com.upsoft.demo.framework.api.FunctionApi;
import com.upsoft.demo.framework.base.MainBean;
import com.upsoft.demo.framework.bean.NetworkBean;
import com.upsoft.demo.framework.bean.PlugInfo;
import com.upsoft.demo.framework.bean.SkinBean;
import com.upsoft.demo.framework.sp.ColorSP;
import com.upsoft.demo.framework.sp.IpSP;
import com.upsoft.sdk.Constant;
import com.upsoft.sdk.crash.AppCrashHandler;
import com.upsoft.sdk.http.nohttp.CookieListener;
import com.upsoft.sdk.image.loader.ImageLoader;
import com.upsoft.sdk.util.L;
import com.upsoft.sdk.util.StringUtil;
import com.vise.log.config.LogDefaultConfig;
import com.yanzhenjie.nohttp.InitializationConfig;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.cookie.DBCookieStore;

import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename DefaultConfig
 * @description -------------------------------------------------------
 * @date 2018/9/17 17:40
 */
public class DefaultConfig implements FrameworkConfig {
    private Application app;
    private static DefaultConfig singleton;
    private int drawableId;
    private List<MainBean> mainBeanList;
    private List<SkinBean> skinBeanList;
    private boolean isCacheColorToMemory = true;//将颜色配置缓存到内存


    private List<PlugInfo> myPlugList;
    public static DefaultConfig getInstance() {
        if (singleton == null) {
            synchronized (LogDefaultConfig.class) {
                if (singleton == null) {
                    singleton = new DefaultConfig();
                }
            }
        }
        return singleton;
    }

    @Override
    public FrameworkConfig init(Application app) {
        if (app == null) {
            throw new NullPointerException("初始化Application为空!");
        }
        this.app = app;
        //初始化Nohttp
        NoHttp.initialize(InitializationConfig.newBuilder(app).cookieStore(new DBCookieStore(app)
                .setCookieStoreListener(new CookieListener(app))).build());
//        Logger.setDebug(true);
        //初始化图片加载框架
        ImageLoader.init(app);
//        //初始化崩溃日志
//        AppCrashHandler.getInstance().init(app);
        return this;
    }

    @Override
    public FrameworkConfig setWelcomeBg(int drawableId) {
        this.drawableId = drawableId;
        return this;
    }

    @Override
    public int getWelcomeBg() {
        return drawableId;
    }

    @Override
    public FrameworkConfig setMainBeanList(List<MainBean> list) {
        this.mainBeanList = list;
        return this;
    }

    @Override
    public List<MainBean> getMainBeanList() {
        return mainBeanList;
    }

    @Override
    public FrameworkConfig setNetList(List<NetworkBean> list) {
        FunctionApi.saveIplist(app, list);
        if (StringUtil.isBlank(FunctionApi.getIp(app))) {
            IpSP.saveIp(app, list.get(0).getIpAddress());
        }
        return this;
    }

    @Override
    public FrameworkConfig setDefaultTitleColor(String color) {
        ColorSP.setDefaultTitleColor(color);
        return this;
    }

    @Override
    public FrameworkConfig setDefaultBarColor(String color) {
        ColorSP.setDefaultBarColor(color);
        return this;
    }

    @Override
    public FrameworkConfig setDefaultBtnColor(String color) {
        ColorSP.setDefaultBtnColor(color);
        return this;
    }

    @Override
    public FrameworkConfig cacheColorToMemory(boolean isCache) {
        this.isCacheColorToMemory = isCache;
        if (isCacheColorToMemory) {
            FunctionApi.cacheColorToMemory(app);
        }
        return this;
    }

    @Override
    public FrameworkConfig setSkinList(List<SkinBean> list) {
        this.skinBeanList = list;
        return this;
    }

    @Override
    public List<SkinBean> getSkinBeanList() {
        if (skinBeanList != null && skinBeanList.size() > 0) {
            return skinBeanList;
        }
        return SkinBean.getDefaultSkinList();
    }

    @Override
    public FrameworkConfig setProjectName(String projectName) {
        Constant.PROJECT_NAME = projectName;
        Constant.buildFile();
        //初始化崩溃日志
        AppCrashHandler.getInstance().init(app);
        return this;
    }

    @Override
    public FrameworkConfig isShowLog(boolean isShowLog) {
        L.setDebugMode(isShowLog);
        Logger.setDebug(isShowLog);
        return this;
    }

    @Override
    public FrameworkConfig setMyPlugList(List<PlugInfo> list) {
        this.myPlugList = list;
        return this;
    }

    @Override
    public List<PlugInfo> getMyPlugList() {
        return myPlugList;
    }

    public String getCurrentTitleColor() {
        return ColorSP.getTitleColor(app);
    }


    public String getCurrentBarColor() {
        return ColorSP.getBarColor(app);
    }
}
