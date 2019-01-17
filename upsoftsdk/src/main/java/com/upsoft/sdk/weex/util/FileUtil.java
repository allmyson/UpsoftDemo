package com.upsoft.sdk.weex.util;

import android.content.Context;
import android.text.TextUtils;

import com.taobao.weex.utils.WXLogUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author lh
 * @version 1.0.0
 * @filename FileUtil
 * @description -------------------------------------------------------
 * @date 2017/8/23 13:47
 */
public class FileUtil {


    /**
     * 创建文件和文件夹
     *
     * @param filename
     * @param isDir
     * @return
     * @throws IOException
     * @author： WX
     */
    public static File buildFile(String filename, boolean isDir)
            throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            if (isDir) {
                file.mkdirs();
            } else {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                    file = new File(file.getAbsolutePath());
                }
            }
        }
        return file;
    }


    /**
     * Load file in asset directory.
     * @param path    FilePath
     * @param context Weex Context
     * @return the Content of the file
     */
    public static String loadAsset(String path, Context context) {
        if (context == null || TextUtils.isEmpty(path)) {
            return null;
        }
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        try {
            inputStream = context.getAssets().open(path);
            StringBuilder builder = new StringBuilder(inputStream.available() + 10);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            char[] data = new char[4096];
            int len = -1;
            while ((len = bufferedReader.read(data)) > 0) {
                builder.append(data, 0, len);
            }

            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            WXLogUtils.e("", e);
        } finally {
            try {
                if (bufferedReader != null)
                    bufferedReader.close();
            } catch (IOException e) {
                WXLogUtils.e("WXFileUtils loadAsset: ", e);
            }
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                WXLogUtils.e("WXFileUtils loadAsset: ", e);
            }
        }

        return "";
    }

    public static boolean saveFile(String path, byte[] content, Context context) {
        if (TextUtils.isEmpty(path) || content == null || context == null) {
            return false;
        }
        FileOutputStream outStream = null;
        try {
            outStream = new FileOutputStream(path);
            outStream.write(content);
            return true;
        } catch (Exception e) {
            WXLogUtils.e("WXFileUtils saveFile: " + WXLogUtils.getStackTrace(e));
        } finally {
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static String readSDFile(String fileName) {
        String res = "";
        FileInputStream fis = null;
        try {
            File file = new File(fileName);

            fis = new FileInputStream(file);

            int length = fis.available();

            byte[] buffer = new byte[length];
            fis.read(buffer);
            res = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res;
    }
}
