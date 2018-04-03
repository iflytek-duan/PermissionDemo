package com.dohenes.permissiondemo.util;

import android.annotation.SuppressLint;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * ClassName：RomUtils
 * Description：TODO<Rom工具类，用于获取一些设备信息>
 * Author：zihao
 * Date：2018/4/3 10:52
 * Email：crazy.zihao@gmail.com
 * Version：v1.0
 */
public class RomUtils {

    private static final String TAG = RomUtils.class.getSimpleName();

    /**
     * 获取设备的硬件生产商名称
     *
     * @return manufacturer
     */
    public static String getManufacturer() {
        String manufacturer = Build.MANUFACTURER;
        return !TextUtils.isEmpty(manufacturer) ? manufacturer : "";
    }

    /**
     * 获取小米 rom 版本号，获取失败返回 -1
     *
     * @return miui rom version code, if fail , return -1
     */
    public static int getMiuiVersion() {
        String version = getSystemProperty("ro.miui.ui.version.name", "-1");

        if (version != null) {
            try {
                return Integer.parseInt(version.substring(1));
            } catch (Exception e) {
                Log.e(TAG, "get miui version code error, version : " + version);
            }
        }

        return Integer.parseInt(version);
    }

    /**
     * 通过反射获取系统属性
     *
     * @param key          key
     * @param defaultValue 默认值
     * @return 属性值
     */
    @SuppressLint("PrivateApi")
    private static String getSystemProperty(String key, String defaultValue) {
        try {
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method get = clz.getMethod("get", String.class, String.class);
            return (String) get.invoke(clz, key, defaultValue);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return defaultValue;
    }

}
