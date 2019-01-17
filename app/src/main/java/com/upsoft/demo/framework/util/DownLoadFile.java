package com.upsoft.demo.framework.util;


import com.upsoft.sdk.util.FileUtil;
import com.upsoft.sdk.util.L;
import com.upsoft.sdk.util.StringUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class DownLoadFile extends Thread {
    private static boolean dolState = true;

    /**
     * 文件下载  没有文件后缀
     *
     * @param urlString
     * @param
     */
    public static void downFile(String urlString, String path, String cookieStr, IDownloadCallBack callbacks) {
        try {
            L.e(cookieStr + "==cookie==下载地址=" + urlString);
            URL url = new URL(urlString);
            URLConnection con = url.openConnection();
            con.setRequestProperty("Cookie", StringUtil.valueOf(cookieStr));
            byte[] bs = new byte[1024];
            int len;
            int lenAdd = 0;
            int totalLength = con.getContentLength();
            BufferedInputStream bis = new BufferedInputStream(con.getInputStream());
            String fileName = "";
            String fileType = HttpURLConnection.guessContentTypeFromStream(bis);
            //判断连接是否有后缀名
            if (urlString.substring(urlString.lastIndexOf("/")).indexOf(".") == -1) {
                //没有后缀名
                Map<String, List<String>> map = con.getHeaderFields();
                Iterator it = map.keySet().iterator();
                //跳过第一个
                it.next();
                while (it.hasNext()) {
                    String header = it.next().toString();
                    for (String str : map.get(header)) {
                        if (str.contains("filename")) {
                            fileName = str.split(";")[1].split("=")[1];
                            break;
                        }
                    }
                }
                if (StringUtil.isBlank(fileName)) {
                    //下载链接出问题了
                    L.e("没有获取到文件名=======更新失败");
                    callbacks.onFail("未获取到文件名");
                    return;
                }
                fileType = fileName.substring(fileName.lastIndexOf("."));
                fileName = fileName.substring(0, fileName.lastIndexOf("."));
            } else {
                //有后缀
                fileName = urlString.substring(urlString.lastIndexOf("/") + 1, urlString.lastIndexOf("."));
                fileType = urlString.substring(urlString.lastIndexOf(".") + 1, urlString.length());
            }
            // 输出的文件流
            File newFile = new File(path + "/" + fileName + "." + fileType);
            if (newFile.exists()) {
                newFile.delete();
            }
            FileUtil.createFile(path, fileName + "." + fileType);
            OutputStream os = new FileOutputStream(newFile);
            // 打开连接
            con = url.openConnection();
            con.setRequestProperty("Cookie", StringUtil.valueOf(cookieStr));
            // 输入流
            InputStream is = con.getInputStream();
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
                lenAdd = lenAdd + len;
                callbacks.onProgress(totalLength, lenAdd);
                if (!dolState) {
                    os.close();
                    is.close();
                }
            }
            // 完毕，关闭所有链接
            os.close();
            is.close();
            callbacks.onSuccess(newFile.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            callbacks.onFail("文件路径错误");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            callbacks.onFail("流异常错误");
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            callbacks.onFail("文件解析错误");
            e.printStackTrace();
        }
    }

    public static void setState(boolean state) {
        dolState = state;
    }
}
