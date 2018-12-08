package br.pablo.arduinoproject.service;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import br.pablo.arduinoproject.BuildConfig;
import br.pablo.arduinoproject.PersistenceUtil;
import br.pablo.arduinoproject.R;
import br.pablo.arduinoproject.activity.MainActivity;
import br.pablo.arduinoproject.model.Notification;
import io.realm.Realm;

public class FirebaseCloudMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        try {

            if (remoteMessage != null && remoteMessage.getData() != null) {
                String date = remoteMessage.getData().get("date");
                Long ordenation = Long.parseLong(remoteMessage.getData().get("ordenation"));

                final Realm realm = Realm.getInstance(PersistenceUtil.configurationRealm());

                realm.executeTransaction(realm1 -> {
                    Notification notification = new Notification();

                    notification.setNotificationId(Integer.parseInt("" + realm.where(Notification.class).count()));
                    notification.setDate(date);
                    notification.setOrdenation(ordenation);

                    realm.copyToRealmOrUpdate(notification);

                    sendNotification(0);
                });
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
    }


    private void sendNotification(int requestCode) {
        try {

                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                Uri defaultSoundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.notification_sound);


                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, BuildConfig.APPLICATION_ID)
                        .setSmallIcon(R.drawable.ic_arduino2)
                        .setColor(ContextCompat.getColor(getBaseContext(), R.color.colorArduino))
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_arduino))
                        .setContentTitle("O Arduino identificou algum barulho!")
                        .setContentText("Clique aqui para ver o horário da ocorrência.")
                        .setAutoCancel(true)
                        .setPriority(2)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent)
                        .setDefaults(android.app.Notification.DEFAULT_VIBRATE);

                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
                    notificationBuilder = new NotificationCompat.Builder(this, BuildConfig.APPLICATION_ID)
                            .setSmallIcon(R.drawable.ic_arduino2)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_arduino))
                            .setContentTitle("O Arduino identificou algum barulho!")
                            .setContentText("Clique aqui para ver o horário da ocorrência.")
                            .setAutoCancel(true)
                            .setSound(defaultSoundUri)
                            .setContentIntent(pendingIntent)
                            .setDefaults(android.app.Notification.DEFAULT_VIBRATE);
                }

                notificationBuilder.build().flags |= android.app.Notification.FLAG_AUTO_CANCEL;
                NotificationManager notificationManager = getNotificactionManager();
                if (notificationManager != null) {
                    notificationManager.notify(requestCode, notificationBuilder.build());
                }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private NotificationManager getNotificactionManager() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String channelId = BuildConfig.APPLICATION_ID;
            CharSequence channelName = "ArduinoProject";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            if (notificationManager != null) {
                NotificationChannel notificationChannel = notificationManager.getNotificationChannel(channelId);

                if (notificationChannel == null) {
                    notificationChannel = new NotificationChannel(channelId, channelName, importance);
                    notificationChannel.enableLights(true);
                    notificationChannel.setLightColor(Color.argb(255, 217, 93, 48));
                    notificationChannel.enableVibration(true);
                    notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

                    notificationManager.createNotificationChannel(notificationChannel);
                }
            }
        }

        return notificationManager;
    }

}
