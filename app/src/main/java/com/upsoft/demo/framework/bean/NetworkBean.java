package com.upsoft.demo.framework.bean;

/**
 * Copyright (c) 2016,重庆扬讯软件技术股份有限公司<br>
 * All rights reserved.<br>
 * <p/>
 * 文件名称：NetworkBean<br>
 * 摘要：网络配置<br>
 * -------------------------------------------------------<br>s
 * 当前版本：1.1.1<br>
 * 作者：李杰<br>
 * 完成日期：2017/10/9<br>
 * -------------------------------------------------------<br>
 * 取代版本：1.1.0<br>
 * 原作者：李杰<br>
 * 完成日期：2017/10/9<br>
 */

public class NetworkBean {
    String ipName;
    String ipAddress;

    public NetworkBean(String name, String address) {
        this.ipName = name;
        this.ipAddress = address;
    }

    public String getIpName() {
        return ipName;
    }

    public void setIpName(String ipName) {
        this.ipName = ipName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

}
