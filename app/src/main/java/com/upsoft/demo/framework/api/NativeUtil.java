package com.upsoft.demo.framework.api;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.upsoft.demo.framework.activity.AudioActivity;
import com.upsoft.demo.framework.util.AppUtil;
import com.upsoft.sdk.Constant;
import com.upsoft.sdk.util.L;
import com.upsoft.sdk.util.StringUtil;
import com.upsoft.sdk.util.ToastUtil;

import java.io.File;


/**
 * Copyright (c) 2016,重庆扬讯软件技术有限公司<br>
 * All rights reserved.<br>
 * <p>
 * 文件名称：L.java<br>
 * 摘要：JS原生统一管理类<br>
 * -------------------------------------------------------<br>
 * 当前版本：1.1.1<br>
 * 作者：董杰科<br>
 * 完成日期：2016年4月26日<br>
 * -------------------------------------------------------<br>
 * 取代版本：1.1.0<br>
 * 原作者：董杰科<br>
 * 完成日期：2016年4月26日<br>
 */
public class NativeUtil {

    /**
     * 当前使用录音的文件名-未拼接名字
     */
    private static String audiofileName = "";
    /**
     * 录音
     */
    private static AudioRecorder audioRecorder;
    private static final String TAG = "NativeUtil";
    public static String imgPath = "";


    /**
     * @param i 返回值
     * @return void
     * @author 董杰科
     * @version 1.0
     * @Description: 跳转相机页面--传入图片的保存路径
     * @time： 2016/11/7
     */
    public static void intentToCamera(Context mContext, int i) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imgPath = Constant.TEMPIM_PATH + "/" + StringUtil.getNowTimeStr(1);
        File f = new File(imgPath);//
        if (!new File(Constant.TEMPIM_PATH).exists()) new File(Constant.TEMPIM_PATH).mkdirs();
        String authority = AppUtil.getPackageName(mContext) + FunctionApi.AUTHORITY;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//android-7.0
            Uri contentUri = FileProvider.getUriForFile(mContext, authority, f);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);//
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            L.e("path>>", contentUri.toString());
        } else {
            Uri u = Uri.fromFile(f);//
            L.e("path>>", u.toString());
            intent.putExtra(MediaStore.EXTRA_OUTPUT, u);//
        }

        ((Activity) mContext).startActivityForResult(intent, i);
    }


    /**
     * @return void
     * @author 董杰科
     * @version 1.0
     * @Description: 暂停录音
     * @time： 2016/11/7
     */
    public static boolean pauseAudioRecorder() {
        boolean pauseRecorder = false;
        try {
            if (audioRecorder.getStatus() == AudioRecorder.Status.STATUS_START) {
                //暂停录音
                audioRecorder.pauseRecord();
                pauseRecorder = true;
                Log.e("audio_pauser", "success");
            }
        } catch (IllegalStateException e) {
            pauseRecorder = false;
            Log.e("暂停失败", e.getMessage());
        }
        return pauseRecorder;
    }


    public static boolean continueAudioRecorder(RecordStreamListener listener) {
        boolean continueRecorder = false;
        try {
            if (audioRecorder.getStatus() != AudioRecorder.Status.STATUS_START) {
                Log.e("audio_continue", "success");
                audioRecorder.startRecord(listener);
                continueRecorder = true;
            }
        } catch (IllegalStateException e) {
            continueRecorder = false;
            Log.e("继续录音", e.getMessage());
        }
        return continueRecorder;
    }


    /**
     * @return void
     * @author 董杰科
     * @version 1.0
     * @Description: 退出录音需要 释放资源
     * @time： 2016/11/7
     */
    public static void destoryAudioRecorder() {
        if (audioRecorder != null) {
            audioRecorder.release();
        }
    }

    /**
     * @return void
     * @author 董杰科
     * @version 1.0
     * @Description: 播放录音
     * @time： 2016/11/7
     */
    public static boolean playAduio(Context mContext, String audioFileParh) {
        boolean playStatue = true;
        try {
            String fileType = AudioFileUtil.getAudioFileType(audioFileParh);
            Intent it = new Intent(Intent.ACTION_VIEW);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//// TODO: 2017/4/1  jack 暂时不要
                it.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                //Uri u = FileProvider.getUriForFile(mContext, "com.upsoft.sdk.fileprovider", new File(problemEntity
                // .getFilePath()));
                Uri u = Uri.parse(audioFileParh);
                it.setDataAndType(u, "video/*");
            } else {
                it.setDataAndType(Uri.parse("file://" + audioFileParh), "audio/*");
            }
            //it.setDataAndType(Uri.parse("file://" + audioFileParh), "audio/" + fileType);
            it.putExtra("videoUrl", "file://" + audioFileParh);
            mContext.startActivity(it);
        } catch (Exception e) {
            playStatue = false;
        }
        return playStatue;
    }


    /**
     * @return void
     * @author 董杰科
     * @version 1.0
     * @Description: 开始录像
     * @time： 2016/11/7
     */
    public static void startVideo(Context mContext, int i) {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            try {
                File dir = new File(Constant.VIDEO_PATH);
                if (!dir.exists())
                    dir.mkdirs();
                File f = new File(dir, StringUtil.getNowTimeStr(3));
                Uri u = null;
                String authority = AppUtil.getPackageName(mContext) + FunctionApi.AUTHORITY;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//android-7.0
                    u = FileProvider.getUriForFile(mContext, authority, f);
                } else {
                    u = Uri.fromFile(f);
                }
                Intent mIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                mIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);//相机辨识度
                mIntent.putExtra(MediaStore.EXTRA_OUTPUT, u);
                ((Activity) mContext).startActivityForResult(mIntent, i);
            } catch (ActivityNotFoundException e) {
                ToastUtil.show(mContext, "没有找到储存目录");
            }
        } else {
            ToastUtil.show(mContext, "没有储存卡");
        }
    }


    /**
     * @return void
     * @author 董杰科
     * @version 1.0
     * @Description: 播放本地视频
     * @time： 2016/11/7
     */
    public static void playVideo(Context mContext, String path) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            String authority = AppUtil.getPackageName(mContext) + FunctionApi.AUTHORITY;
            Uri u = FileProvider.getUriForFile(mContext, authority, new File(path));
            intent.setDataAndType(u, "video/*");
        } else {
            intent.setDataAndType(Uri.parse("file://" + path), "video/*");
        }
        intent.putExtra("videoUrl", "file://" + path);
        mContext.startActivity(intent);
    }


    /**
     * 跳转视频播放页面  websockt播放
     *
     * @param url  播放地址 eg:"ws://192.168.0.95:7100/h264"
     * @param data 发送的json字符串
     */
    @JavascriptInterface
    public void playVedioBySocket(Context mContext, String url, String data) {
        if (url == null) {
            throw new NullPointerException("视频播放地址不能为空!");
        }
//		Intent intent = new Intent(context,PlayVedioActivity.class);
        /**----跳转软解码播放界面 @by lh 2016/7/5 11:12------**/
        Intent intent = new Intent();
        intent.setClassName(mContext, "com.upsoft.ep.app.view.PlayVedioActivity");
        intent.putExtra("address", url);
        intent.putExtra("data", StringUtil.valueOf(data));
        mContext.startActivity(intent);
    }


    /**
     * @return void
     * @author 董杰科
     * @version 1.0
     * @Description: 开始录音
     * @time： 2016/12/16
     */
    public static boolean takeAudio2(Context mContext) {
        try {
            Intent intent = null;
            intent = new Intent(mContext, AudioActivity.class);
            ((Activity) mContext).startActivityForResult(intent, JSApi.REQUEST_FOR_AUDIO);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public static void playTel(Context mContext, String url, String i) {
        Log.e("tel=", "" + url + "type=" + i);
        int j = Integer.parseInt(i);
        if (!TextUtils.isEmpty(url)) {
            Intent callIntent;
            if (j == 0) {
                callIntent = new Intent("android.intent.action.DIAL", Uri.parse("tel:" + url));
                mContext.startActivity(callIntent);
            } else if (j == 1) {
                callIntent = new Intent();
                callIntent.setAction("android.intent.action.CALL");
                callIntent.setData(Uri.parse("tel:" + url));
                mContext.startActivity(callIntent);
            }
        }

    }


}
