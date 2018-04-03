package com.dohenes.permissiondemo.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;

import com.dohenes.permissiondemo.BuildConfig;

/**
 * ClassName：JumpToPermissionManagementUtils
 * Description：TODO<跳转至应用权限许可管理页的工具类>
 * Author：zihao
 * Date：2018/4/3 09:30
 * Email：crazy.zihao@gmail.com
 * Version：v1.0
 */
public class JumpToPermissionManagementUtils {

    private static final String TAG = JumpToPermissionManagementUtils.class.getSimpleName();

    /**
     * 品牌名称
     * Build.MANUFACTURER
     */
    private interface Manufacturer {
        String XIAOMI = "Xiaomi";// 小米
        String HUAWEI = "Huawei";// 华为
        String MEIZU = "Meizu";// 魅族
        String OPPO = "OPPO";// OPPO
        String VIVO = "vivo";// vivo
        String LENOVO = "LENOVO";// 联想
        String LETV = "Letv";// 乐视
        String ZTE = "ZTE";// 中兴
        String _360 = "ulong";// 360
        String YULONG = "YuLong";// 酷派（宇龙计算机通信科技有限公司）
        String SONY = "Sony";// 索尼
        String SAMSUNG = "samsung";// 三星
        String LG = "LG";// 乐金
        String LGE = "LGE";// 乐金
        String GOOGLE = "google";// 谷歌
    }

    /**
     * 跳转至权限设置页
     *
     * @param activity Activity
     */
    public static void goToPermissionSettingPage(@NonNull Activity activity) {
        String manufacturer = RomUtils.getManufacturer();
        Log.e(TAG, "start to goto setting page, manufacturer is : " + manufacturer);

        try {
            switch (manufacturer) {
                case Manufacturer.XIAOMI:// 测试通过{[Redmi Note 2](MIUI 8/9)}
                    gotoXiaomiSetting(activity);
                    break;
                case Manufacturer.HUAWEI:
                    gotoHuaweiSetting(activity);
                    break;
                case Manufacturer.MEIZU:// 测试通过{[Meizu M2 Note](Flyme 6.8.1)}
                    gotoMeizuSetting(activity);
                    break;
                case Manufacturer.OPPO:
                    gotoOPPOSetting(activity);
                    break;
                case Manufacturer.VIVO:
                    gotoVivoSetting(activity);
                    break;
//            case Manufacturer.LENOVO:
//                break;
                case Manufacturer.LETV:
                    gotoLetvSetting(activity);
                    break;
//            case Manufacturer.ZTE:
//                break;
//            case Manufacturer.YULONG:
//                break;
                case Manufacturer._360:
                    goto360Setting(activity);
                    break;
                case Manufacturer.SONY:
                    gotoSonySetting(activity);
                    break;
                case Manufacturer.SAMSUNG:
                    gotoSamsungSetting(activity);
                    break;
                case Manufacturer.LG:
                    gotoLGSetting(activity);
                    break;
                case Manufacturer.LGE:// 测试通过{[Nexus 5](Android 6.0),[Nexus 6p](Android 6.0)}
                case Manufacturer.GOOGLE:
                    gotoApplicationInfo(activity);
                    break;
                default:
                    gotoApplicationInfo(activity);
                    break;
            }
        } catch (Exception e) {// 有任何异常，直接跳转至设置页
            Log.e(TAG, "jump to setting error, start to goto systemSetting.");
            gotoSystemSetting(activity);
        }
    }

    /**
     * 跳转至小米权限设置页
     *
     * @param activity activity
     */
    private static void gotoXiaomiSetting(Activity activity) {
        int miuiVersion = RomUtils.getMiuiVersion();
        Intent intent = null;

        switch (miuiVersion) {
            case 5:// MIUI_V5
                Uri packageURI = Uri.parse("package:" + activity.getApplicationInfo().packageName);
                intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                break;
            case 6:// MIUI_V6
            case 7:// MIUI_V7
                intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                intent.setClassName("com.miui.securitycenter",
                        "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                intent.putExtra("extra_pkgname", activity.getPackageName());
                break;
            case 8:// MIUI_V8
            case 9:// MIUI_V9
                intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                intent.setClassName("com.miui.securitycenter",
                        "com.miui.permcenter.permissions.PermissionsEditorActivity");
                intent.putExtra("extra_pkgname", activity.getPackageName());
                break;
            default:
                Log.e(TAG, "goto xiaomi setting error,the miuiVersion is : " + miuiVersion);
                try {
                    gotoApplicationInfo(activity);
                } catch (Exception e) {
                    gotoSystemSetting(activity);
                }
                break;
        }

        if (intent != null) {
            activity.startActivity(intent);
        }
    }

    /**
     * 跳转至华为权限设置页
     *
     * @param activity activity
     */
    private static void gotoHuaweiSetting(Activity activity) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        ComponentName comp = new ComponentName("com.huawei.systemmanager",
                "com.huawei.permissionmanager.ui.MainActivity");
        intent.setComponent(comp);
        activity.startActivity(intent);
    }

    /**
     * 跳转至魅族权限设置页
     *
     * @param activity activity
     */
    private static void gotoMeizuSetting(Activity activity) {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        activity.startActivity(intent);
    }

    /**
     * 跳转至OPPO权限设置页
     *
     * @param activity activity
     */
    private static void gotoOPPOSetting(Activity activity) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        ComponentName comp = new ComponentName("com.color.safecenter",
                "com.color.safecenter.permission.PermissionManagerActivity");
        intent.setComponent(comp);
        activity.startActivity(intent);
    }

    /**
     * 跳转至vivo权限设置页
     *
     * @param activity activity
     */
    private static void gotoVivoSetting(Activity activity) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        ComponentName comp = ComponentName
                .unflattenFromString("com.iqoo.secure/.safeguard.PurviewTabActivity");
        intent.setComponent(comp);
        activity.startActivity(intent);
    }

    /**
     * 跳转至Letv权限设置页
     *
     * @param activity activity
     */
    private static void gotoLetvSetting(Activity activity) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        ComponentName comp = new ComponentName("com.letv.android.letvsafe",
                "com.letv.android.letvsafe.PermissionAndApps");
        intent.setComponent(comp);
        activity.startActivity(intent);
    }

    /**
     * 跳转至Sony权限设置页
     *
     * @param activity activity
     */
    private static void gotoSonySetting(Activity activity) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        ComponentName comp = new ComponentName("com.sonymobile.cta",
                "com.sonymobile.cta.SomcCTAMainActivity");
        intent.setComponent(comp);
        activity.startActivity(intent);
    }

    /**
     * 跳转至Samsung权限设置页
     *
     * @param activity activity
     */
    private static void gotoSamsungSetting(Activity activity) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        ComponentName comp = new ComponentName("com.samsung.android.sm_cn",
                "com.samsung.android.sm.ui.ram.AutoRunActivity");
        intent.setComponent(comp);
        activity.startActivity(intent);
    }

    /**
     * 跳转至LG权限设置页
     *
     * @param activity activity
     */
    private static void gotoLGSetting(Activity activity) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        ComponentName comp = new ComponentName("com.android.settings",
                "com.android.settings.Settings$AccessLockSummaryActivity");
        intent.setComponent(comp);
        activity.startActivity(intent);
    }

    /**
     * 360只能打开到自带安全软件
     *
     * @param activity activity
     */
    private static void goto360Setting(Activity activity) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        ComponentName comp = new ComponentName("com.qihoo360.mobilesafe",
                "com.qihoo360.mobilesafe.ui.index.AppEnterActivity");
        intent.setComponent(comp);
        activity.startActivity(intent);
    }

    /**
     * 跳转至锤子设置界面
     *
     * @param activity activity
     */
    private static void SmartisanSetting(Activity activity) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        ComponentName comp = new ComponentName("com.smartisanos.security", "com.smartisanos.security.MainActivity");
        intent.setComponent(comp);
        activity.startActivity(intent);
    }

    /**
     * 跳转至应用信息界面
     *
     * @param activity activity
     */
    private static void gotoApplicationInfo(Activity activity) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        localIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        localIntent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        activity.startActivity(localIntent);
    }

    /**
     * 跳转至系统设置界面
     *
     * @param activity activity
     */
    private static void gotoSystemSetting(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        activity.startActivity(intent);
    }
}
