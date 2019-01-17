package com.upsoft.demo.framework.dialog;

import android.content.Context;

import com.upsoft.demo.R;
import com.upsoft.sdk.adapter.CommonAdapter;
import com.upsoft.sdk.adapter.ViewHolder;
import com.upsoft.sdk.util.StringUtil;

import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename ListAdapter
 * @description -------------------------------------------------------
 * @date 2017/6/28 14:07
 */
public class ListAdapter extends CommonAdapter<ListBean> {
    public ListAdapter(Context context, List<ListBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, ListBean item, int position) {
        helper.setText(R.id.tv, StringUtil.valueOf(item.getValue()));
    }
}
