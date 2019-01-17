package com.upsoft.demo.framework.api;

import android.os.Environment;
import android.text.TextUtils;

import com.upsoft.sdk.Constant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2016,重庆扬讯软件技术有限公司<br>
 * All rights reserved.<br>
 *
 * 文件名称：L.java<br>
 * 摘要：管理文件类<br>
 * -------------------------------------------------------<br>
 * 当前版本：1.1.1<br>
 * 作者：董杰科<br>
 * 完成日期：2016年11月1日<br>
 * -------------------------------------------------------<br>
 * 取代版本：1.1.0<br>
 * 原作者：董杰科<br>
 * 完成日期：2016年11月1日<br>
 */
public class AudioFileUtil {
    /** 根目录 */
    private static String rootPath = Constant.AUDIO_PATH;
    /** 原始文件(不能播放) */
    private final static String AUDIO_PCM_BASEPATH = rootPath + "/pcm";
    /** 可播放的高质量音频文件 */
    private final static String AUDIO_WAV_BASEPATH = rootPath + "/arw";

    public static String getAudioWavBasepath() {
        return AUDIO_WAV_BASEPATH;
    }



    /**
     * @author 董杰科
     * @version 1.0
     * @Description: 得到PCM的路径
     * @return void
     * @time： 2016/11/7
     */
    public static String getPcmFileAbsolutePath(String fileName){
        if(TextUtils.isEmpty(fileName)){
            throw new NullPointerException("fileName isEmpty");
        }
        if(!isSdcardExit()){
            throw new IllegalStateException("sd card no found");
        }
        String mAudioRawPath = "";
        if (isSdcardExit()) {
            if (!fileName.endsWith(".pcm")) {
                fileName = fileName + ".pcm";
            }
            String fileBasePath = AUDIO_PCM_BASEPATH;
            File file = new File(fileBasePath);
            //创建目录
            if (!file.exists()) {
                file.mkdirs();
            }
            mAudioRawPath = fileBasePath + fileName;
        }
        return mAudioRawPath;
    }
    /**
     * @author 董杰科
     * @version 1.0
     * @Description: 得到WAV文件路径
     * @return void
     * @time： 2016/11/7
     */
    public static String getWavFileAbsolutePath(String fileName) {
        if(fileName==null){
            throw new NullPointerException("fileName can't be null");
        }
        if(!isSdcardExit()){
            throw new IllegalStateException("sd card no found");
        }

        String mAudioWavPath = "";
        if (isSdcardExit()) {
            if (!fileName.endsWith(".raw")) {
                fileName = fileName + ".raw";
            }
            String fileBasePath = AUDIO_WAV_BASEPATH;
            File file = new File(fileBasePath);
            //创建目录
            if (!file.exists()) {
                file.mkdirs();
            }
            mAudioWavPath = fileBasePath + fileName;
        }
        return mAudioWavPath;
    }

    /**
     * @author 董杰科
     * @version 1.0
     * @Description: 判断是否有外部存储设备sdcard
     * @return void
     * @time： 2016/11/7
     */
    public static boolean isSdcardExit() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * @author 董杰科
     * @version 1.0
     * @Description: 获取全部pcm文件列表
     * @return void
     * @time： 2016/11/7
     */
    public static List<File> getPcmFiles() {
        List<File> list = new ArrayList<>();
        String fileBasePath = AUDIO_PCM_BASEPATH;

        File rootFile = new File(fileBasePath);
        if (!rootFile.exists()) {
        } else {

            File[] files = rootFile.listFiles();
            for (File file : files) {
                list.add(file);
            }

        }
        return list;

    }

    /**
     * @author 董杰科
     * @version 1.0
     * @Description: 获取全部wav文件列表
     * @return void
     * @time： 2016/11/7
     */
    public static List<File> getWavFiles() {
        List<File> list = new ArrayList<>();
        String fileBasePath = AUDIO_WAV_BASEPATH;

        File rootFile = new File(fileBasePath);
        if (!rootFile.exists()) {
        } else {
            File[] files = rootFile.listFiles();
            for (File file : files) {
                list.add(file);
            }
        }
        return list;
    }
    /**
     * @author 董杰科
     * @version 1.0
     * @Description: 截取字符串 确定 文件类型
     * @return void
     * @time： 2016/11/7
     */
    public static String getAudioFileType(String url){
        String path = "";
        if (!TextUtils.isEmpty(url)){
            path = url.substring(url.length()-2, url.length());
            path = exChange(path);
        }
        return path;
    }

    /**
     * @author 董杰科
     * @version 1.0
     * @Description: 把一个字符串中的小写转换为大写
     * @return void
     * @time： 2016/11/7
     */
    public static String exChange(String str){
        for(int i=0;i<str.length();i++){
            //如果是小写
            if(str.substring(i, i+1).equals(str.substring(i, i+1).toLowerCase())){
                str.substring(i, i+1).toUpperCase();
            }else{
                //str.substring(i, i+1).toLowerCase();
            }
        }
        return str;
    }
}
