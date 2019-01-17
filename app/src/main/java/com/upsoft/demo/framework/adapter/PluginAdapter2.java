package com.upsoft.demo.framework.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.upsoft.demo.R;
import com.upsoft.demo.framework.api.FunctionApi;
import com.upsoft.demo.framework.bean.PlugBean2;
import com.upsoft.demo.framework.util.UrlUtil;
import com.upsoft.sdk.adapter.CommonAdapter;
import com.upsoft.sdk.adapter.ViewHolder;
import com.upsoft.sdk.image.loader.ImageLoader;
import com.upsoft.sdk.util.StringUtil;

import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename PluginAdapter2
 * @description -------------------------------------------------------
 * @date 2018/10/19 16:07
 */
public class PluginAdapter2 extends CommonAdapter<PlugBean2.DataBeanX.DataBean> {
    private String currentIp;

    public PluginAdapter2(Context context, List<PlugBean2.DataBeanX.DataBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        currentIp = FunctionApi.getHttpIp(mContext);
    }

    @Override
    public void convert(ViewHolder helper, PlugBean2.DataBeanX.DataBean item, int position) {
        helper.setText(R.id.tv_item, StringUtil.valueOf(item.pluginName));
        ImageLoader.with(mContext).url(UrlUtil.getFileUrl(currentIp,item.pluginIcon)).placeHolder(R.mipmap.bg_default02).into(helper
                .getView(R.id
                .iv_item));
        ImageView typeIV = helper.getView(R.id.iv_type);
        switch (StringUtil.StringToInt(item.pluginType)) {
            case 0:
                ImageLoader.with(mContext).res(R.mipmap.f_ic_n).into(typeIV);
                break;
            case 1:
                ImageLoader.with(mContext).res(R.mipmap.f_ic_h).into(typeIV);
                break;
            case 2:
                ImageLoader.with(mContext).res(R.mipmap.f_ic_w).into(typeIV);
                break;
        }
    }
}
