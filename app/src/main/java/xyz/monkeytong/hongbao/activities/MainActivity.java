package xyz.monkeytong.hongbao.activities;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.preference.PreferenceManager;

import java.util.List;

import xyz.monkeytong.hongbao.BuildConfig;
import xyz.monkeytong.hongbao.HBApplication;
import xyz.monkeytong.hongbao.R;
import xyz.monkeytong.hongbao.fragments.GeneralSettingsFragment;
import xyz.monkeytong.hongbao.services.HongbaoNotificationService;
import xyz.monkeytong.hongbao.services.KeepAliveService;


public class MainActivity extends AppCompatActivity implements AccessibilityManager.AccessibilityStateChangeListener {

    //AccessibilityService 管理
    private AccessibilityManager accessibilityManager;
    private OnePixelReceiver mReceiver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.preferences_fragment, new GeneralSettingsFragment());
        fragmentTransaction.commit();

        explicitlyLoadPreferences();

        //监听AccessibilityService 变化
        accessibilityManager = (AccessibilityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);
        accessibilityManager.addAccessibilityStateChangeListener(this);
        updateHongbaoServiceStatus();
        if (mReceiver == null) {
            mReceiver = new OnePixelReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            filter.addAction(Intent.ACTION_SCREEN_ON);
            registerReceiver(mReceiver, filter);
        }

    }

    private void explicitlyLoadPreferences() {
        PreferenceManager.setDefaultValues(this, R.xml.general_preferences, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateHongbaoServiceStatus();
    }

    @Override
    public void onBackPressed() {
        String listeners = Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners");
        if (listeners != null && listeners.contains(HBApplication.LISTENER_PATH)) {
            Toast.makeText(this, "通知服务会一直运行。只能手动关闭！！！", Toast.LENGTH_SHORT).show();
        }
        if (!moveTaskToBack(false)) {
            super.onBackPressed();
        }
    }

    @Override
    public void onAccessibilityStateChanged(boolean enabled) {
        updateHongbaoServiceStatus();
    }

    /**
     * 更新当前 HongbaoService 显示状态
     */
    private void updateHongbaoServiceStatus() {
        if (isServiceEnabled()) {
            Intent service = new Intent(this, KeepAliveService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(service);
            } else {
                startService(service);
            }
            //打开无障碍服务

        } else {
            Intent service = new Intent(this, KeepAliveService.class);
            stopService(service);
            //关闭无障碍服务
        }
    }


    /**
     * 获取 HongbaoService 是否启用状态
     *
     * @return
     */
    private boolean isServiceEnabled() {
        List<AccessibilityServiceInfo> accessibilityServices =
                accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC);
        for (AccessibilityServiceInfo info : accessibilityServices) {
            if (info.getId().equals(getPackageName() + "/.services.HongbaoService")) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        String listeners = Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners");
        if (listeners != null && listeners.contains(HBApplication.LISTENER_PATH)) {
            Toast.makeText(this, "通知监听服务运行中，请关闭", Toast.LENGTH_SHORT).show();
        }
        //移除监听服务
        accessibilityManager.removeAccessibilityStateChangeListener(this);
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
            mReceiver = null;
        }
        super.onDestroy();
    }


    private static class OnePixelReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {    //屏幕关闭启动1像素Activity
                Intent it = new Intent(context, OnePixelActivity.class);
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(it);
            } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {   //屏幕打开 结束1像素
                LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(OnePixelActivity.ACTION_FINISH));
            }
        }
    }
}
