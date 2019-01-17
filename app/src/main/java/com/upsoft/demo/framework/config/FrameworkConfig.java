package com.upsoft.demo.framework.config;

import android.app.Application;

import com.upsoft.demo.framework.base.MainBean;
import com.upsoft.demo.framework.bean.NetworkBean;
import com.upsoft.demo.framework.bean.PlugInfo;
import com.upsoft.demo.framework.bean.SkinBean;

import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename FrameworkConfig
 * @description -------------------------------------------------------
 * @date 2018/9/17 17:38
 */
public interface FrameworkConfig {
    //初始化
    FrameworkConfig init(Application app);
    //设置欢迎界面背景图
    FrameworkConfig setWelcomeBg(int drawableId);

    int getWelcomeBg();

    //设置主页 tab及fragment
    FrameworkConfig setMainBeanList(List<MainBean> list);

    List<MainBean> getMainBeanList();

    //设置网络配置
    FrameworkConfig setNetList(List<NetworkBean> list);

    //设置默认标题栏颜色
    FrameworkConfig setDefaultTitleColor(String color);
    //设置默认状态栏颜色
    FrameworkConfig setDefaultBarColor(String color);
    //设置默认按钮背景颜色
    FrameworkConfig setDefaultBtnColor(String color);
    //将标题栏、状态栏、按钮、文字等颜色缓存到内存
    FrameworkConfig cacheColorToMemory(boolean isCache);
    //设置系统皮肤配色
    FrameworkConfig setSkinList(List<SkinBean> list);
    List<SkinBean> getSkinBeanList();
    //设置项目名，文件夹名称会以此命名
    FrameworkConfig setProjectName(String projectName);
    //设置是否打印信息到Logcat
    FrameworkConfig isShowLog(boolean isShowLog);

    //设置我的-插件配置
    FrameworkConfig setMyPlugList(List<PlugInfo> list);
    List<PlugInfo> getMyPlugList();
}
