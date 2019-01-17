package com.upsoft.demo.framework.js;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.upsoft.demo.R;
import com.upsoft.sdk.util.L;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Copyright (c) 2016,重庆扬讯软件技术有限公司<br>
 * All rights reserved.<br>
 * <p/>
 * 文件名称：ListDialog<br>
 * 摘要：任务<br>
 * -------------------------------------------------------<br>
 * 当前版本：1.1.1<br>
 * 作者：李杰<br>
 * 完成日期：2017/2/5<br>
 * -------------------------------------------------------<br>
 * 取代版本：1.1.0<br>
 * 原作者：李杰<br>
 * 完成日期：2017/2/5<br>
 */


public class ListDialog extends Dialog {
    private ListView listView;
    private Context context;
    private List<String> list;
    private MyListDialogListener myListDialogListener;
    private ListPerAdapter adapter;
    private EditText mText;


    public ListDialog(Context context, List<String> list, MyListDialogListener myListDialogListener) {
        super(context, R.style.CustomDialog);
        this.context = context;
        this.list = list;
        this.myListDialogListener = myListDialogListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// NoTitle
        setContentView(R.layout.dialog_list_dialog);
        listView = (ListView) findViewById(R.id.list_per);
        mText = (EditText) findViewById(R.id.list_per_sedia);
        adapter = new ListPerAdapter(context, list);
        listView.setAdapter(adapter);
        mText.addTextChangedListener(watcher);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> map = new HashMap<>();
                if (!adapter.getName(position).equals(list.get(position))) {
                    for (int i = 0; i < list.size(); i++) {
                        if (adapter.getName(position).equals(list.get(i))) {
                            map.put("position", i);
                            break;
                        }
                    }
                } else {
                    map.put("position", position);
                }
                map.put("name", adapter.getName(position));
                String json = new Gson().toJson(map);
                L.e("列表框返回给H5的结果：" + json);
                myListDialogListener.back(json);
                dismiss();
            }
        });
    }


    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            String te = s.toString().trim();
            if (null != te && !TextUtils.isEmpty(te)) {
                List<String> li = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    String text = list.get(i);
                    if (text.contains(te)) {
                        li.add(text);
                    }
                }
                adapter.update(li);
            } else {
                adapter.update(list);
            }
        }
    };


    public interface MyListDialogListener {
        public void back(String name);
    }
}
