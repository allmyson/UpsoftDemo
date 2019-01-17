package com.upsoft.demo.framework.activity;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.upsoft.demo.R;
import com.upsoft.demo.framework.api.FunctionApi;
import com.upsoft.demo.framework.base.BaseActivity;
import com.upsoft.qrlibrary.CaptureActivity;
import com.upsoft.qrlibrary.ShowActivity;
import com.upsoft.sdk.util.L;
import com.vise.log.ViseLog;
import com.yongchun.library.view.ImageSelectorActivity;

import java.util.ArrayList;
import java.util.List;

public class NativeTestActivity extends BaseActivity {
    private ListView lv;
    private ArrayAdapter<String> mAdapter;
    String[] list = new String[]{"查看大图", "图片选择器", "二维码扫描", "NFC扫描", "拍照", "图片压缩", "直接拨打电话", "跳转拨号界面", "发短信","测试Crash监控"};
    private final int CODE_XZ = 0;
    private final int CODE_YS = 1;
    private int code = CODE_XZ;

    @Override
    public int getLayoutId() {
        return R.layout.activity_native_test;
    }

    @Override
    public void initView() {
        lv = getView(R.id.lv);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        lv.setAdapter(mAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        FunctionApi.LargerImage(mContext, new String[]{
                                "https://timgsa.baidu" +
                                        ".com/timg?image&quality=80&size=b9999_10000&sec=1535715352135&di" +
                                        "=ae4f60d6a79b4e8002f5e4d6ec6fe7dd&imgtype=0&src=http%3A%2F%2Fimg17.3lian" +
                                        ".com%2Fd%2Ffile%2F201702%2F09%2F65345f70b77ad89ae45a028e99d62ea8.jpg",
                                "https://timgsa.baidu" +
                                        ".com/timg?image&quality=80&size=b9999_10000&sec=1535715352134&di" +
                                        "=063110d2817f16fcbf16a4fc71601ea9&imgtype=0&src=http%3A%2F%2Fimg.zcool" +
                                        ".cn%2Fcommunity%2F01c1b75541b2cd000001a64bb14a39.jpg",
                                "https://timgsa.baidu" +
                                        ".com/timg?image&quality=80&size=b9999_10000&sec=1535715352134&di" +
                                        "=00676352a31cff84b9ef8e6eb6a809ea&imgtype=0&src=http%3A%2F%2Fpic39.nipic" +
                                        ".com%2F20140311%2F10600816_135850674000_2.jpg",
                                "https://timgsa.baidu" +
                                        ".com/timg?image&quality=80&size=b9999_10000&sec=1535715352133&di" +
                                        "=68d6019711883e099117d7da4398a49c&imgtype=0&src=http%3A%2F%2Fpic41.photophoto.cn%2F20161217%2F1155116491394363_b.jpg",
                                "https://timgsa.baidu" +
                                        ".com/timg?image&quality=80&size=b9999_10000&sec=1535715352132&di" +
                                        "=428a09dd0a84120bf1f64e374716f44f&imgtype=0&src=http%3A%2F%2Fpic31.photophoto.cn%2F20140604%2F0038038047490161_b.jpg"
                        }, 0);
                        break;
                    case 1:
                        code = CODE_XZ;
//                        FunctionApi.takePicture(mContext, 1, 2, false, false, false);
                        startActivity(new Intent(mContext, SelectImageActivity.class));
                        break;
                    case 2:
                        FunctionApi.intentToCapture(mActivity, 100);
                        break;
                    case 3:
                        FunctionApi.intentToNFC(mActivity, 101);
                        break;
                    case 4:
                        FunctionApi.takeCamera(mActivity, 102);
                        break;
                    case 5:
                        code = CODE_YS;
                        FunctionApi.takePicture(mActivity, 1, 2, true, false, false);
                        break;
                    case 6:
                        FunctionApi.callPhone(mContext, "13678789898");
                        break;
                    case 7:
                        FunctionApi.diallPhone(mContext, "13029389022");
                        break;
                    case 8:
                        FunctionApi.sendSMS(mContext, "10086", "1");
                        break;
                    case 9:
                        List<String> list = null;
                        list.add("123");
                        break;
                }
            }
        });
    }

    @Override
    public void getData() {

    }

    @Override
    public void onClick(View v) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == ImageSelectorActivity.REQUEST_IMAGE) {
            ArrayList<String> images = (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity
                    .REQUEST_OUTPUT);
            L.e("图片url", Uri.parse(images.get(0)) + "");
            if (code == CODE_XZ) {
                FunctionApi.LargerImage(mContext, new String[]{images.get(0)}, 0);
            } else if (code == CODE_YS) {
                CompressActivity.intentToCompress(mContext, images.get(0));
            }

        } else if (resultCode == RESULT_OK && requestCode == 100) {
            //二维码扫描返回
            String result = data.getStringExtra(CaptureActivity.SCAN_RESULT);
            ViseLog.d(result);
            ShowActivity.showQRResult(mContext, result);

        } else if (resultCode == RESULT_OK && requestCode == 101) {
            String result = data.getStringExtra(NFCScanActivity.NFC_RESULT);
            ViseLog.d(result);
            ShowActivity.showQRResult(mContext, result);
        } else if (resultCode == RESULT_OK && requestCode == 102) {
            String path = FunctionApi.TEMP_PHOTO_PATH;
            FunctionApi.LargerImage(mContext, new String[]{path}, 0);
        }
    }
}
