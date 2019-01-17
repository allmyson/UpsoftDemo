package com.upsoft.demo.framework.bean;

import com.upsoft.sdk.http.BaseBean;

import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename UploadFileBean
 * @description -------------------------------------------------------
 * @date 2018/9/27 18:14
 */
public class UploadFileBean extends BaseBean {

    /**
     * data : b6a16fb6177f4e29adac6c7b123c4f65
     * fileId : b6a16fb6177f4e29adac6c7b123c4f65
     * filePath : /upsoftsdk/app/2018/09/27//118e90386a244039ac98b19f5694f030.JPEG
     * fileResults : [{"fileId":"b6a16fb6177f4e29adac6c7b123c4f65","fileName":"ImageSelector_20180927_181317.JPEG",
     * "filePath":"/upsoftsdk/app/2018/09/27//118e90386a244039ac98b19f5694f030.JPEG"}]
     */

    public String data;
    public String fileId;
    public String filePath;
    public List<FileResultsBean> fileResults;

    public static class FileResultsBean {
        /**
         * fileId : b6a16fb6177f4e29adac6c7b123c4f65
         * fileName : ImageSelector_20180927_181317.JPEG
         * filePath : /upsoftsdk/app/2018/09/27//118e90386a244039ac98b19f5694f030.JPEG
         */

        public String fileId;
        public String fileName;
        public String filePath;
    }
}
