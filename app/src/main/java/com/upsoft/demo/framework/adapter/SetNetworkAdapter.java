package com.upsoft.demo.framework.adapter;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.upsoft.demo.R;
import com.upsoft.demo.framework.api.FunctionApi;
import com.upsoft.demo.framework.bean.NetworkBean;

import java.util.List;

public class SetNetworkAdapter extends BaseAdapter {
    private Context context;
    private List<NetworkBean> list;
    private String textIconColor;

    public SetNetworkAdapter(Context context, List<NetworkBean> list) {
        this.context = context;
        this.list = list;
        textIconColor = FunctionApi.getCurrentTextIconColor(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setData(List<NetworkBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        viewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_network, null);
            holder = new viewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.tv_network_name);
            holder.address = (TextView) convertView.findViewById(R.id.tv_network_address);
            holder.im = (ImageView) convertView.findViewById(R.id.iv_choose);
            holder.name.setTextColor(Color.parseColor(textIconColor));
            holder.address.setTextColor(Color.parseColor(textIconColor));
            holder.im.setColorFilter(Color.parseColor(textIconColor));
            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();
        }
        holder.name.setText(list.get(position).getIpName());
        holder.address.setText(list.get(position).getIpAddress());

//        if(position == selectItem){
//            holder.im.setBackgroundResource(R.mipmap.network_ck_on);
//        }else {
//            holder.im.setBackgroundResource(R.mipmap.network_ck_off);
//        }
        return convertView;
    }

    public int selectItem = -1;

    public int getSelectItem() {
        return selectItem;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    class viewHolder {
        ImageView im;
        TextView name;
        TextView address;
    }
}
