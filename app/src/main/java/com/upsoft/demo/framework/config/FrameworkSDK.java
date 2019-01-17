package com.upsoft.demo.framework.config;

/**
 * @author lh
 * @version 1.0.0
 * @filename FrameworkSDK
 * @description -------------------------------------------------------
 * @date 2018/9/14 17:44
 */
public class FrameworkSDK {
    //默认配置
    private static final FrameworkConfig FRAME_DEFAULT_CONFIG = DefaultConfig.getInstance();
    public static FrameworkConfig getDefaultConfig() {
        return FRAME_DEFAULT_CONFIG;
    }
    private FrameworkSDK(){

    }

}
