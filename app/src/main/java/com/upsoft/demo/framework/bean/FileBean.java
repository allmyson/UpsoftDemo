package com.upsoft.demo.framework.bean;

import com.upsoft.sdk.http.BaseBean;

/**
 * @author lh
 * @version 1.0.0
 * @filename FileBean
 * @description -------------------------------------------------------
 * @date 2018/11/14 10:47
 */
public class FileBean extends BaseBean {

    /**
     * errorCode : null
     * msg : null
     * data : {"fileId":"ee080d4e63fa4a449402a878c31100eb","fileName":"ImageSelector_20181114_103345.JPEG",
     * "fileOriginal":"ImageSelector_20181114_103345.JPEG",
     * "filePath":"/upsoftsdk/app/2018/11/14/ImageSelector_20181114_103345.JPEG","fileType":".JPEG",
     * "fileSize":"37745","fileSystem":"","delFlag":"0","createTime":1542162741112,"creatorId":"bp_01",
     * "creator":"管理员","creatorOrgId":"1","creatorOrgName":"数据中心","catalogId":"16fb5d0ba44e445f997ab983526ea9ab",
     * "moduleId":"upsoftsdk/app","fileExtend":""}
     */

    public DataBean data;

    public static class DataBean {
        /**
         * fileId : ee080d4e63fa4a449402a878c31100eb
         * fileName : ImageSelector_20181114_103345.JPEG
         * fileOriginal : ImageSelector_20181114_103345.JPEG
         * filePath : /upsoftsdk/app/2018/11/14/ImageSelector_20181114_103345.JPEG
         * fileType : .JPEG
         * fileSize : 37745
         * fileSystem :
         * delFlag : 0
         * createTime : 1542162741112
         * creatorId : bp_01
         * creator : 管理员
         * creatorOrgId : 1
         * creatorOrgName : 数据中心
         * catalogId : 16fb5d0ba44e445f997ab983526ea9ab
         * moduleId : upsoftsdk/app
         * fileExtend :
         */

        public String fileId;
        public String fileName;
        public String fileOriginal;
        public String filePath;
        public String fileType;
        public String fileSize;
        public String fileSystem;
        public String delFlag;
        public long createTime;
        public String creatorId;
        public String creator;
        public String creatorOrgId;
        public String creatorOrgName;
        public String catalogId;
        public String moduleId;
        public String fileExtend;
    }
}
