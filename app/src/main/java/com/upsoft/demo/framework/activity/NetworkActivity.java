package com.upsoft.demo.framework.activity;

import android.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.upsoft.demo.R;
import com.upsoft.demo.framework.adapter.SetNetworkAdapter;
import com.upsoft.demo.framework.api.FunctionApi;
import com.upsoft.demo.framework.base.BaseActivity;
import com.upsoft.demo.framework.bean.NetworkBean;
import com.upsoft.demo.framework.sp.ColorSP;
import com.upsoft.demo.framework.sp.IpSP;
import com.upsoft.demo.framework.ui.AllTitleView;
import com.upsoft.sdk.util.L;
import com.upsoft.sdk.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename NetworkActivity
 * @description -------------------------------------------------------
 * @date 2018/9/21 17:31
 */
public class NetworkActivity extends BaseActivity implements AllTitleView.ITitleViewOnClick {
    private ListView mListView;
    private List<NetworkBean> list;
    private SetNetworkAdapter mSetNetworkAdapter;
    private String Ip;
    private Button mButton;
    private String setIP, setAddress;
    private int size = 0;
    public static String NETWORK_DATA = "networkData";

    @Override
    public int getLayoutId() {
        return R.layout.activity_network;
    }

    @Override
    public void initView() {
        list = new ArrayList<>();
        allTitleView = (AllTitleView) this.findViewById(R.id.title);
        allTitleView.setLeftImg(R.mipmap.ic_back);
        allTitleView.setCenterText("网络设置");
        allTitleView.setRightText("自定义");
        allTitleView.setTitleOnClickListener(this);
        mListView = (ListView) this.findViewById(R.id.lv_setting);
        mButton = (Button) findViewById(R.id.btn_input);
        mButton.setOnClickListener(this);
        initData();
        mSetNetworkAdapter = new SetNetworkAdapter(mContext, list);
        mListView.setAdapter(mSetNetworkAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Ip = list.get(position).getIpAddress();
                L.e("选择Ip", Ip);
            }
        });
        setState();
    }

    @Override
    public void getData() {
        String buttonColor = ColorSP.getBtnColor(mContext);
        shapeSolid(mButton, buttonColor);
    }


    @Override
    public void backLeft() {
        finish();
    }

    @Override
    public void backRight() {
        View vi = LayoutInflater.from(mContext).inflate(R.layout.network_pop, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(vi);
        builder.create();
        final AlertDialog dialog = builder.show();
        final EditText name = (EditText) vi.findViewById(R.id.ed_addnet_name);
        final EditText address = (EditText) vi.findViewById(R.id.ed_addnet_address);
        name.setText(IpSP.getAddIpName(mContext));
        address.setText(IpSP.getAddIpAres(mContext));
        vi.findViewById(R.id.ed_addnet_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        vi.findViewById(R.id.ed_addnet_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(name.getText().toString())) {
                    ToastUtil.show(mContext, "请输入IP名字");
                    return;
                }
                if (TextUtils.isEmpty(address.getText().toString())) {
                    ToastUtil.show(mContext, "请输入IP地址");
                    return;
                }
                setIP = name.getText().toString();
                setAddress = address.getText().toString();
                if (list.size() == size) {
                    list.add(new NetworkBean(setIP, setAddress));
                    mSetNetworkAdapter.setData(list);
                } else if (list.size() == size + 1) {
                    list.remove(list.size() - 1);
                    list.add(new NetworkBean(setIP, setAddress));
                    mSetNetworkAdapter.setData(list);
                }
                IpSP.saveAddIp(mContext, setIP, setAddress);
                dialog.dismiss();
            }
        });
    }


    @Override
    public void onClick(View v) {
        IpSP.saveIp(mContext, Ip);
        finish();
    }


    private void setState() {
        String ip = IpSP.getIp(mContext);
        if (TextUtils.isEmpty(ip)) {
            mListView.performItemClick(mListView.getAdapter().getView(0, null, null), 0, mListView
                    .getItemIdAtPosition(0));
            IpSP.saveIp(mContext, list.get(0).getIpAddress());
        } else {
            for (int i = 0; i < list.size(); i++) {
                if (ip.equals(list.get(i).getIpAddress())) {
                    mListView.performItemClick(mListView.getAdapter().getView(0, null, null), i, mListView
                            .getItemIdAtPosition(0));
                }
            }
        }
    }


    private void initData() {
        Ip = IpSP.getIp(mContext);
        list = FunctionApi.getIPList(mContext);
        if (list == null) {
            list = new ArrayList<>();
        }
        size = list.size();
        List<String> addList = IpSP.getNameAndAddress(mContext);
        if (addList.size() == 2) {
            list.add(new NetworkBean(addList.get(0), addList.get(1)));
        }
    }
}
