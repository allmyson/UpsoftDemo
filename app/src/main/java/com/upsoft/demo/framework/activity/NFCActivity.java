package com.upsoft.demo.framework.activity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.NdefFormatable;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Gravity;

import com.upsoft.demo.framework.base.BaseActivity;
import com.upsoft.sdk.util.ToastUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Copyright (c) 2016,重庆扬讯软件技术有限公司<br>
 * All rights reserved.<br>
 * <p/>
 * 文件名称：hbt_new<br>
 * 摘要：扫描NFC主要ACITVITY<br>
 * -------------------------------------------------------<br>
 * 当前版本：1.1.1<br>
 * 作者：董杰科<br>
 * 完成日期：2016/11/3<br>
 * -------------------------------------------------------<br>
 * 取代版本：1.1.0<br>
 * 原作者：董杰科<br>
 * 完成日期：2016/11/3<br>
 */
public abstract class NFCActivity extends BaseActivity {

    // NFC适配器
    private NfcAdapter mNfcAdapter;
    private PendingIntent mPendingIntent;
    private IntentFilter[] mFilters;
    private String[][] mTechLists;

    /**
     * 回调函数
     *
     * @param intent
     * @author： WX
     */
    protected abstract void onNFCAction(Intent intent);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initNFCFunction();
    }

    @Override
    public void onNewIntent(Intent intent) {
        onNFCAction(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        initNFCFunction();
        if (mNfcAdapter != null && mNfcAdapter.isEnabled()) {
            mNfcAdapter.enableForegroundDispatch(this, mPendingIntent,
                    mFilters, mTechLists);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mNfcAdapter != null && mNfcAdapter.isEnabled()) {
            mNfcAdapter.disableForegroundDispatch(this);
        }
    }

    /**
     * 初始化NFC
     *
     * @author： WX
     */
    private void initNFCFunction() {
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
            ToastUtil.show(this, "NFC功能不可用！", Gravity.TOP);
            return;
        }
        if (!mNfcAdapter.isEnabled()) {
            ToastUtil.show(this, "请在系统设置中先启用NFC功能！", Gravity.TOP);
            return;
        }
        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);

        try {
            ndef.addDataType("*/*");
        } catch (MalformedMimeTypeException e) {
            throw new RuntimeException("fail", e);
        }

        IntentFilter techNdef = new IntentFilter(
                NfcAdapter.ACTION_TECH_DISCOVERED);
        IntentFilter tagNdef = new IntentFilter(
                NfcAdapter.ACTION_TAG_DISCOVERED);
        mFilters = new IntentFilter[]{ndef, techNdef, tagNdef};
        mTechLists = new String[][]{new String[]{NfcA.class.getName()},
                new String[]{MifareClassic.class.getName()},
                new String[]{NdefFormatable.class.getName()}};
    }

    /**
     * 获取NFC标签的rfid编码
     *
     * @param intent
     * @return
     * @author： WX
     */
    public static String praseRfid(Intent intent, Context context) {
        String rfid = "";
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
            Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            byte[] by = tagFromIntent.getId();
            rfid = getId(by);
        }
        return rfid;
    }

    public static String getId(byte[] str) {
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = str.length - 1; i >= 0; i--) {
            String hex = Integer.toHexString(str[i] & 0xFF);
            if (hex.length() == 1) {
                hex = "0" + hex;
            }
            stringBuilder.append(hex.toUpperCase());
        }
        return stringBuilder.toString();
    }

    /**
     * 解析NFC标签数据
     *
     * @param intent
     * @param context
     * @return
     * @author： WX
     */
    public static String praseData(Intent intent, Context context) {
        String action = intent.getAction();
        ByteArrayOutputStream bas = new ByteArrayOutputStream();
        String nfcStr = "";
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
            Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            if (tagFromIntent != null) {
                MifareClassic mf = MifareClassic.get(tagFromIntent);
                try {
                    mf.connect();
                    int scount = mf.getSectorCount();
                    int bcount = 0;

                    for (int i = 0; i < scount; i++) {
                        boolean auth = mf.authenticateSectorWithKeyA(i,
                                MifareClassic.KEY_DEFAULT);
                        if (auth) {
                            bcount = mf.getBlockCountInSector(i);
                            for (int j = 0; j < bcount; j++) {
                                int blockNo = i * bcount + j;
                                if (blockNo == 0 || (j + 1) % 4 == 0) {
                                    continue;
                                }
                                bas.write(mf.readBlock(blockNo));
                            }
                        }
                    }
                    nfcStr = new String(bas.toByteArray(), "gbk");
                } catch (Exception e) {
                    ToastUtil.show(context, "请等待标签信息读完后再离开标签！", Gravity.TOP);
                } finally {
                    try {
                        if (mf != null) {
                            mf.close();
                        } else {
                            ToastUtil.show(context, "无法识别标签信息！", Gravity.TOP);
                            nfcStr = null;
                        }
                        if (bas != null) {
                            bas.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            try {
                Parcelable[] rawArray = intent
                        .getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
                for (Parcelable parcelable : rawArray) {
                    if (parcelable != null) {
                        NdefMessage mNdefMsg = (NdefMessage) parcelable;
                        NdefRecord[] mNdefRecords = mNdefMsg.getRecords();
                        for (NdefRecord record : mNdefRecords) {
                            if (record != null) {
                                bas.write(record.getPayload());
                            }
                        }
                    }
                }
                nfcStr = new String(bas.toByteArray(), "gbk");
            } catch (UnsupportedEncodingException e) {

            } catch (IOException e) {

            }
        }
        return nfcStr;
    }

}
