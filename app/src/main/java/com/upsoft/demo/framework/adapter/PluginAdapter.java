package com.upsoft.demo.framework.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.upsoft.demo.R;
import com.upsoft.demo.framework.bean.PluginBean;
import com.upsoft.sdk.adapter.CommonAdapter;
import com.upsoft.sdk.adapter.ViewHolder;
import com.upsoft.sdk.image.loader.ImageLoader;
import com.upsoft.sdk.util.StringUtil;

import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename PluginAdapter
 * @description -------------------------------------------------------
 * @date 2018/9/20 16:08
 */
public class PluginAdapter extends CommonAdapter<PluginBean> {
    public PluginAdapter(Context context, List<PluginBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, PluginBean item, int position) {
        helper.setText(R.id.tv_item, StringUtil.valueOf(item.title));
        ImageLoader.with(mContext).res(item.iconId).placeHolder(R.mipmap.bg_default02).into(helper.getView(R.id
                .iv_item));
        ImageView typeIV = helper.getView(R.id.iv_type);
        switch (item.handleType){
            case 0:
                ImageLoader.with(mContext).res(R.mipmap.f_ic_n).into(typeIV);
                break;
            case 1:
                ImageLoader.with(mContext).res(R.mipmap.f_ic_h).into(typeIV);
                break;
            case 2:
                break;
        }
    }
}
