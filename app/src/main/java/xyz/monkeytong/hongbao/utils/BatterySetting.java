package xyz.monkeytong.hongbao.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

/**
 * @author cc.wang
 * @date 2024/2/4
 */
public class BatterySetting {

    public static String getBrand() {
        return Build.BRAND;
    }

    public static String getModel() {
        return Build.MODEL;
    }


    public static void open(Context context) {
        try {
            String brand = getBrand();
            if (brand.equalsIgnoreCase("samsung")) {
                // 此设备是三星设备
                context.startActivity(new Intent().setComponent(
                        new ComponentName("com.samsung.android.sm", "com.samsung.android.sm.ui.battery.BatteryActivity")));
            } else if (brand.equalsIgnoreCase("huawei")) {
                // 此设备是华为设备
                context.startActivity(new Intent().setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity")));
            } else if (brand.equalsIgnoreCase("oppo")) {
                // 此设备是OPPO设备
                try {
                    context.startActivity(new Intent().setComponent(new ComponentName("com.coloros.oppoguardelf", "com.coloros.powermanager.fuelgaue.PowerUsageModelActivity")));
                } catch (Exception e) {
                    context.startActivity(new Intent().setComponent(new ComponentName("com.coloros.oppoguardelf", "com.coloros.powermanager.fuelgaue.PowerAppsBgSetting")));
                }
            } else if (brand.equalsIgnoreCase("vivo")) {
                // 此设备是vivo设备
                context.startActivity(new Intent().setComponent(new ComponentName("com.vivo.abe", "com.vivo.applicationbehaviorengine.ui.ExcessivePowerManagerActivity")));
            } else if (brand.equalsIgnoreCase("xiaomi")) {
                // 此设备是小米设备
                context.startActivity(new Intent().setComponent(new ComponentName("com.miui.powerkeeper", "com.miui.powerkeeper.ui.HiddenAppsContainerManagementActivity")));
            } else {
                // 打开了系统设置页面
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                context.startActivity(intent);
            }
        } catch (Exception e) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", context.getPackageName(), null);
            intent.setData(uri);
            context.startActivity(intent);

        }
    }
}
