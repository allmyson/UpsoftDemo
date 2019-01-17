package com.upsoft.demo.framework.adapter;

import android.content.Context;

import com.upsoft.demo.R;
import com.upsoft.demo.framework.bean.PlugInfo;
import com.upsoft.sdk.adapter.CommonAdapter;
import com.upsoft.sdk.adapter.ViewHolder;

import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename MyAdapter
 * @description -------------------------------------------------------
 * @date 2018/12/25 17:09
 */
public class MyAdapter extends CommonAdapter<PlugInfo> {
    public MyAdapter(Context context, List<PlugInfo> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, PlugInfo item, int position) {
        helper.setImageResource(R.id.iv_, item.drawableId);
        helper.setText(R.id.tv_, item.name);
    }
}
