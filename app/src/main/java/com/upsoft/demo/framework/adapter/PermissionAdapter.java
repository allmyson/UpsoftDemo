package com.upsoft.demo.framework.adapter;

import android.content.Context;

import com.upsoft.demo.R;
import com.upsoft.demo.framework.bean.PermissionBean;
import com.upsoft.sdk.adapter.CommonAdapter;
import com.upsoft.sdk.adapter.ViewHolder;
import com.upsoft.sdk.image.loader.ImageLoader;
import com.upsoft.sdk.util.StringUtil;

import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename PermissionAdapter
 * @description -------------------------------------------------------
 * @date 2018/9/18 14:20
 */
public class PermissionAdapter extends CommonAdapter<PermissionBean> {
    public PermissionAdapter(Context context, List<PermissionBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, PermissionBean item, int position) {
        helper.setText(R.id.tv_name, StringUtil.valueOf(item.name));
        helper.setText(R.id.tv_content, StringUtil.valueOf(item.description));
        ImageLoader.with(mContext).res(item.icon).into(helper.getView(R.id.iv_));
    }
}
