package com.upsoft.demo.framework.bean;

import com.upsoft.sdk.http.BaseBean;

import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename DownloadBean
 * @description -------------------------------------------------------
 * @date 2018/10/19 18:29
 */
public class DownloadBean extends BaseBean {

    /**
     * errorCode : null
     * data : {"pageIndex":1,"pageSize":20,"rowTotal":2,"pageTotal":1,
     * "data":[{"id":"2ebaf7350fa44feaa8b54e10a545410f","fileIcon":null,"fileName":null,"fileType":null,
     * "downloadUrl":"/mobile/2018/09/28//7576b927f377406aa1ef166ed3c2da41.apk"},
     * {"id":"52644fc6ed6746998a11676fcf4325a0",
     * "fileIcon":"/mobile/2018/10/08//12f2eb04d2c144d88fa0c03f920d5094.jpg","fileName":"文件1","fileType":"TXT文件",
     * "downloadUrl":"/mobile/2018/10/08//d02a13c88afe445d84f3167baa1f44e9.txt"}]}
     */

    public DataBeanX data;

    public static class DataBeanX {
        /**
         * pageIndex : 1
         * pageSize : 20
         * rowTotal : 2
         * pageTotal : 1
         * data : [{"id":"2ebaf7350fa44feaa8b54e10a545410f","fileIcon":null,"fileName":null,"fileType":null,
         * "downloadUrl":"/mobile/2018/09/28//7576b927f377406aa1ef166ed3c2da41.apk"},
         * {"id":"52644fc6ed6746998a11676fcf4325a0",
         * "fileIcon":"/mobile/2018/10/08//12f2eb04d2c144d88fa0c03f920d5094.jpg","fileName":"文件1","fileType":"TXT文件",
         * "downloadUrl":"/mobile/2018/10/08//d02a13c88afe445d84f3167baa1f44e9.txt"}]
         */

        public int pageIndex;
        public int pageSize;
        public int rowTotal;
        public int pageTotal;
        public List<DataBean> data;

        public static class DataBean {
            /**
             * id : 2ebaf7350fa44feaa8b54e10a545410f
             * fileIcon : null
             * fileName : null
             * fileType : null
             * downloadUrl : /mobile/2018/09/28//7576b927f377406aa1ef166ed3c2da41.apk
             */

            public String id;
            public String fileIcon;
            public String fileName;
            public String fileType;
            public String downloadUrl;
        }
    }
}
