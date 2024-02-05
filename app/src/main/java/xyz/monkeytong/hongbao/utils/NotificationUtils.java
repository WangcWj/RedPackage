package xyz.monkeytong.hongbao.utils;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import xyz.monkeytong.hongbao.R;


public class NotificationUtils {

    private final static int REQUEST_CODE = 1024;

    public static void checkNotificationPermission(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (manager != null && !manager.areNotificationsEnabled()) {
                context.requestPermissions(new String[]{"android.permission.POST_NOTIFICATIONS"}, REQUEST_CODE);
            }
        }
    }

    public static boolean haveNotificationPermission(Activity context) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return manager != null && !manager.areNotificationsEnabled();
        }
        return true;
    }

    public static void onRequestPermissionsResult(Context context, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (requestCode == REQUEST_CODE) {
                try {
                    NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    if (manager.areNotificationsEnabled()) {
                        String CHANNEL_ID = "channel_id_example";
                        int notificationId = 1;

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            CharSequence name = "RedPacket";
                            String description = "RedPacket description";
                            int importance = NotificationManager.IMPORTANCE_DEFAULT;
                            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
                            channel.setDescription(description);
                            manager.createNotificationChannel(channel);
                        }

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("RedPacket")
                                .setContentText("Hello World!")
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                        manager.notify(notificationId, builder.build());

                    }
                } catch (Exception e) {

                }
            }
        }
    }
}
