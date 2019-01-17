package com.upsoft.demo.framework.bean;

import com.upsoft.demo.R;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lh
 * @version 1.0.0
 * @filename PermissionBean
 * @description -------------------------------------------------------
 * @date 2018/9/18 14:10
 */
public class PermissionBean {
    public String code;
    public int icon;
    public String name;
    public String description;

    public PermissionBean() {
    }

    public PermissionBean(int icon, String name, String description) {
        this.icon = icon;
        this.name = name;
        this.description = description;
    }

    public static Map<String, PermissionBean> map = new HashMap<>();

    static {
        map.put("CALENDAR", new PermissionBean(R.mipmap.f_ic_rl, "日历", "开启日历权限,方便在应用中使用日历查看功能"));
        map.put("CAMERA", new PermissionBean(R.mipmap.ic_camera2, "相机", "开启相机使用权限,方便在应用中使用拍照、摄像等功能"));
        map.put("CONTACTS", new PermissionBean(R.mipmap.f_ic_lxr, "联系人", "开启联系人使用权限,方便在应用中使用联系人等功能"));
        map.put("LOCATION", new PermissionBean(R.mipmap.ic_location, "位置", "开启位置使用权限,方便在应用中使用定位等功能"));
        map.put("MICROPHONE", new PermissionBean(R.mipmap.f_ic_mkf, "麦克风", "开启麦克风使用权限,方便录音"));
        map.put("PHONE", new PermissionBean(R.mipmap.ic_phone, "手机", "开启手机使用权限,方便拨打电话"));
        map.put("SENSORS", new PermissionBean(R.mipmap.f_ic_cgq, "传感器", "开启传感器使用权限,方便在应用中获取传感器数据"));
        map.put("SMS", new PermissionBean(R.mipmap.f_ic_dx, "短信", "开启短信使用权限,方便发送短信"));
        map.put("STORAGE", new PermissionBean(R.mipmap.ic_file, "存储卡", "开启存储卡使用权限,方便在应用中访问本地文件"));
    }
}
