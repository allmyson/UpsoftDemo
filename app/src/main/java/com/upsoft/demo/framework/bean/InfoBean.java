package com.upsoft.demo.framework.bean;

import com.upsoft.sdk.http.BaseBean;

/**
 * @author lh
 * @version 1.0.0
 * @filename InfoBean
 * @description -------------------------------------------------------
 * @date 2017/10/10 14:57
 */
public class InfoBean extends BaseBean {

    /**
     * errorCode : null
     * data : {"createTime":1514518019000,"updateTime":1520993640000,"creatorId":"5603542baf7347ee83fb3b3bf3d7629a",
     * "updaterId":"5603542baf7347ee83fb3b3bf3d7629a","versionTime":5,"enable":true,"delFlag":false,
     * "userId":"9c7fa76bf5934bbaa7b7ff3be117f6a2","userName":"xuling","password":null,"name":"徐灵","age":"",
     * "tel":"15882318292","email":"","sex":"1","isInner":0,"userType":0,"sourcePlat":"self","sourceUserId":null,
     * "orgId":"f078627d9e1142fca68acd96f5087131","userOrg":{"createTime":1514857254000,"updateTime":1520305216000,
     * "creatorId":"5603542baf7347ee83fb3b3bf3d7629a","updaterId":"5603542baf7347ee83fb3b3bf3d7629a","versionTime":4,
     * "enable":true,"delFlag":false,"orgId":"f078627d9e1142fca68acd96f5087131","name":"综合执法中队",
     * "parentId":"228caab526124af684e0b93a9d79d91e","describtion":"","orgHead":"李罗翔宇","orgType":1,
     * "bizCode":"JCZFDD","innerCode":"004_011_001","parentOrg":null,"divisionCode":null,"divisionName":null,
     * "parent":false,"ungranted":false,"ungrantedAtSon":false},"extInfo":{"mobile":"028-68649711",
     * "memberState":"working","position":"1000","positionCode":"1000","positionName":"科员","isTaskManager":"1"},
     * "divisionCode":null,"divisionName":null}
     */
    public DataBean data;

    public static class DataBean {
        /**
         * createTime : 1514518019000
         * updateTime : 1520993640000
         * creatorId : 5603542baf7347ee83fb3b3bf3d7629a
         * updaterId : 5603542baf7347ee83fb3b3bf3d7629a
         * versionTime : 5
         * enable : true
         * delFlag : false
         * userId : 9c7fa76bf5934bbaa7b7ff3be117f6a2
         * userName : xuling
         * password : null
         * name : 徐灵
         * age :
         * tel : 15882318292
         * email :
         * sex : 1
         * isInner : 0
         * userType : 0
         * sourcePlat : self
         * sourceUserId : null
         * orgId : f078627d9e1142fca68acd96f5087131
         * userOrg : {"createTime":1514857254000,"updateTime":1520305216000,
         * "creatorId":"5603542baf7347ee83fb3b3bf3d7629a","updaterId":"5603542baf7347ee83fb3b3bf3d7629a",
         * "versionTime":4,"enable":true,"delFlag":false,"orgId":"f078627d9e1142fca68acd96f5087131","name":"综合执法中队",
         * "parentId":"228caab526124af684e0b93a9d79d91e","describtion":"","orgHead":"李罗翔宇","orgType":1,
         * "bizCode":"JCZFDD","innerCode":"004_011_001","parentOrg":null,"divisionCode":null,"divisionName":null,
         * "parent":false,"ungranted":false,"ungrantedAtSon":false}
         * extInfo : {"mobile":"028-68649711","memberState":"working","position":"1000","positionCode":"1000",
         * "positionName":"科员","isTaskManager":"1"}
         * divisionCode : null
         * divisionName : null
         */

        public long createTime;
        public long updateTime;
        public String creatorId;
        public String updaterId;
        public int versionTime;
        public boolean enable;
        public boolean delFlag;
        public String userId;
        public String userName;
        public Object password;
        public String name;
        public String age;
        public String tel;
        public String email;
        public String sex;
        public int isInner;
        public int userType;
        public String sourcePlat;
        public Object sourceUserId;
        public String orgId;
        public UserOrgBean userOrg;
        public ExtInfoBean extInfo;
        public Object divisionCode;
        public Object divisionName;

        public static class UserOrgBean {
            /**
             * createTime : 1514857254000
             * updateTime : 1520305216000
             * creatorId : 5603542baf7347ee83fb3b3bf3d7629a
             * updaterId : 5603542baf7347ee83fb3b3bf3d7629a
             * versionTime : 4
             * enable : true
             * delFlag : false
             * orgId : f078627d9e1142fca68acd96f5087131
             * name : 综合执法中队
             * parentId : 228caab526124af684e0b93a9d79d91e
             * describtion :
             * orgHead : 李罗翔宇
             * orgType : 1
             * bizCode : JCZFDD
             * innerCode : 004_011_001
             * parentOrg : null
             * divisionCode : null
             * divisionName : null
             * parent : false
             * ungranted : false
             * ungrantedAtSon : false
             */

            public long createTime;
            public long updateTime;
            public String creatorId;
            public String updaterId;
            public int versionTime;
            public boolean enable;
            public boolean delFlag;
            public String orgId;
            public String name;
            public String parentId;
            public String describtion;
            public String orgHead;
            public int orgType;
            public String bizCode;
            public String innerCode;
            public Object parentOrg;
            public Object divisionCode;
            public Object divisionName;
            public boolean parent;
            public boolean ungranted;
            public boolean ungrantedAtSon;
        }

        public static class ExtInfoBean {
            /**
             * mobile : 028-68649711
             * memberState : working
             * position : 1000
             * positionCode : 1000
             * positionName : 科员
             * isTaskManager : 1
             */

            public String mobile;
            public String memberState;
            public String position;
            public String positionCode;
            public String positionName;
            public String isTaskManager;
            public String photo;
        }
    }
}
