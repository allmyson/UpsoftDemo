package com.upsoft.demo.framework.bean;

import com.upsoft.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename PluginBean
 * @description -------------------------------------------------------
 * @date 2018/9/20 16:06
 */
public class PluginBean {
    public int iconId;
    public String title;
    public int handleType;//0-原生  1-h5 2-weex
    public String handleUri;//处理地址
    private String ip;

    public PluginBean() {
    }

    public PluginBean(int iconId, String title) {
        this.iconId = iconId;
        this.title = title;
    }

    public PluginBean(int iconId, String title, int handleType, String handleUri) {
        this.iconId = iconId;
        this.title = title;
        this.handleType = handleType;
        this.handleUri = handleUri;
    }

    public static List<PluginBean> getDefaultData() {
        List<PluginBean> list = new ArrayList<>();
        list.add(new PluginBean(R.mipmap.ic_yqyd, "一企一档", 1, "/static/mweb/poll/page/list.html"));
        list.add(new PluginBean(R.mipmap.ic_hbdt, "环保地图", 0, ""));
        list.add(new PluginBean(R.mipmap.ic_sjfx, "数据分析", 0, ""));
        list.add(new PluginBean(R.mipmap.ic_tjyy, "统计应用", 0, ""));
        list.add(new PluginBean(R.mipmap.ic_fxwt, "问题统计", 1, "/static/mweb/emms/page/statistic/hbt-new-wttj.html"));
        list.add(new PluginBean(R.mipmap.ic_zsk, "知识库", 1, "/static/mweb/kbms/page/main.html"));
        list.add(new PluginBean(R.mipmap.ic_txl, "通讯录", 0, ""));
        list.add(new PluginBean(R.mipmap.ic_xmsb, "项目申报", 0, ""));
        list.add(new PluginBean(R.mipmap.f_ic_task, "任务处理", 2, "file://assets/js/task.js"));
        return list;
    }
}
