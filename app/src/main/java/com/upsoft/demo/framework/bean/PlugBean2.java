package com.upsoft.demo.framework.bean;

import com.upsoft.sdk.http.BaseBean;

import java.util.List;

/**
 * @author lh
 * @version 1.0.0
 * @filename PlugBean2
 * @description -------------------------------------------------------
 * @date 2018/10/19 15:55
 */
public class PlugBean2 extends BaseBean{

    /**
     * status : 1
     * errorCode : null
     * msg : 查询Plugin列表成功！
     * data : {"pageIndex":1,"pageSize":20,"rowTotal":3,"pageTotal":1,
     * "data":[{"id":"4e60a5a65fd54e79b733e6140738aeba",
     * "pluginIcon":"/mobile/2018/10/19//a2872dbc1ef147b7a1499087f7589e23.png","pluginName":"任务处理","pluginType":"2",
     * "pluginDescription":"weex插件","handleUrl":"file://assets/js/task.js","status":"1","available":"1"},
     * {"id":"8e56bbf2dd89419abe415a55a9e64197",
     * "pluginIcon":"/mobile/2018/10/19//04562b51366741f4b2d471e3dc334b15.png","pluginName":"一企一档","pluginType":"1",
     * "pluginDescription":"H5插件","handleUrl":"/static/mweb/poll/page/list.html","status":"1","available":"1"},
     * {"id":"d25c53dda9c54b89975124c051022fd1","pluginIcon":"/mobile/2018/10/19//e9c95a2b54cd4e0aabf40845e01f7eab
     * .png","pluginName":"环保地图","pluginType":"0","pluginDescription":"原生插件","handleUrl":"com.upsoft.demo.business
     * .activity","status":"1","available":"1"}]}
     */

    public DataBeanX data;

    public static class DataBeanX {
        /**
         * pageIndex : 1
         * pageSize : 20
         * rowTotal : 3
         * pageTotal : 1
         * data : [{"id":"4e60a5a65fd54e79b733e6140738aeba",
         * "pluginIcon":"/mobile/2018/10/19//a2872dbc1ef147b7a1499087f7589e23.png","pluginName":"任务处理",
         * "pluginType":"2","pluginDescription":"weex插件","handleUrl":"file://assets/js/task.js","status":"1",
         * "available":"1"},{"id":"8e56bbf2dd89419abe415a55a9e64197",
         * "pluginIcon":"/mobile/2018/10/19//04562b51366741f4b2d471e3dc334b15.png","pluginName":"一企一档",
         * "pluginType":"1","pluginDescription":"H5插件","handleUrl":"/static/mweb/poll/page/list.html","status":"1",
         * "available":"1"},{"id":"d25c53dda9c54b89975124c051022fd1",
         * "pluginIcon":"/mobile/2018/10/19//e9c95a2b54cd4e0aabf40845e01f7eab.png","pluginName":"环保地图",
         * "pluginType":"0","pluginDescription":"原生插件","handleUrl":"com.upsoft.demo.business.activity","status":"1",
         * "available":"1"}]
         */

        public int pageIndex;
        public int pageSize;
        public int rowTotal;
        public int pageTotal;
        public List<DataBean> data;

        public static class DataBean {
            /**
             * id : 4e60a5a65fd54e79b733e6140738aeba
             * pluginIcon : /mobile/2018/10/19//a2872dbc1ef147b7a1499087f7589e23.png
             * pluginName : 任务处理
             * pluginType : 2
             * pluginDescription : weex插件
             * handleUrl : file://assets/js/task.js
             * status : 1
             * available : 1
             */

            public String id;
            public String pluginIcon;
            public String pluginName;
            public String pluginType;
            public String pluginDescription;
            public String handleUrl;
            public String status;
            public String available;
        }
    }
}
