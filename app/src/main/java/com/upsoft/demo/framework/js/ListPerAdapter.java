package com.upsoft.demo.framework.js;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.upsoft.demo.R;

import java.util.List;


public class ListPerAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;

    public ListPerAdapter(Context context, List<String> list) {
        this.list = list;
        this.context = context;
    }


    public void update(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public String getName(int po) {
        return list.get(po);
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list == null ? null : list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.pop_item, null);
            viewholder = new ViewHolder();
            viewholder.name = (TextView) convertView.findViewById(R.id.selecterDialog_name);
            convertView.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        viewholder.name.setText(list.get(position));
        return convertView;
    }

    public class ViewHolder {
        private TextView name;
    }
}
