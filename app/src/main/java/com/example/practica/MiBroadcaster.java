package com.example.practica;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class MiBroadcaster extends BroadcastReceiver {
    static public String CHANNEL_NAME="Esto_es_un_examen";
    static public String ACCION="com.example.practica";
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(ACCION)){
            //---------------------------
            String texto;
            texto = intent.getExtras().getString("mensaje");
            Log.w("HOLA","AQUI: "+texto);
            NotificationCompat.Builder mBuilder;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mBuilder = new NotificationCompat.Builder(context,"21496");
            }
            else{
                mBuilder = new NotificationCompat.Builder(context);
            }

            mBuilder.setSmallIcon(R.drawable.ic_baseline_access_alarm_24)
                    .setContentTitle("Escribiste")
                    .setContentText(texto)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);

            NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1, mBuilder.build());
        }
    }
}
