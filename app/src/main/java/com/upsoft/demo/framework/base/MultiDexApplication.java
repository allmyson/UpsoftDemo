package com.upsoft.demo.framework.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * @author lh
 * @version 1.0.0
 * @filename MultiDexApplication
 * @description -------------------------------------------------------
 * @date 2017/12/13 9:45
 */
public class MultiDexApplication extends Application {

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
