package com.upsoft.demo.framework.update;

import com.upsoft.sdk.http.BaseBean;

/**
 * @author lh
 * @version 1.0.0
 * @filename UpdateBean
 * @description -------------------------------------------------------
 * @date 2018/10/19 11:05
 */
public class UpdateBean extends BaseBean {

    /**
     * errorCode : null
     * data : {"id":"84b4f367bf424980be72aea3bcac2cc6","versionNumber":"2","versionName":"1.0.1","packageName":"com
     * .upsoft.demo","softwareInfo":"UpsoftDemo1.0.1","updateDescription":"Android框架DemoV1.0.1",
     * "downloadUrl":"http://192.168.0.212/cgi/bp/fileshttp://192.168.0.212/cgi/bp/files/mobile/2018/10/19
     * //32193e992c514fe6840f5dfbe8adfe4f.apkk","systemType":"android"}
     */

    public DataBean data;

    public static class DataBean {
        /**
         * id : 84b4f367bf424980be72aea3bcac2cc6
         * versionNumber : 2
         * versionName : 1.0.1
         * packageName : com.upsoft.demo
         * softwareInfo : UpsoftDemo1.0.1
         * updateDescription : Android框架DemoV1.0.1
         * downloadUrl : http://192.168.0.212/cgi/bp/fileshttp://192.168.0.212/cgi/bp/files/mobile/2018/10/19
         * //32193e992c514fe6840f5dfbe8adfe4f.apkk
         * systemType : android
         */

        public String id;
        public String versionNumber;
        public String versionName;
        public String packageName;
        public String softwareInfo;
        public String updateDescription;
        public String downloadUrl;
        public String systemType;
    }
}
