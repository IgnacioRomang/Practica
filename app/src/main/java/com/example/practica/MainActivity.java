package com.example.practica;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    static public List<String> Casi_db;
    static public Boolean noti;
    static public String CHANNEL_NAME="Esto_es_un_examen";
    static public String ACCION="com.example.practica";
    public TextView texto;
    public EditText editText;
    public ToggleButton buttont;
    public Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        texto = findViewById(R.id.texto);
        editText = findViewById(R.id.editText);
        buttont = findViewById(R.id.botont);
        button = findViewById(R.id.buttong);
        noti=false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("21496", CHANNEL_NAME, importance);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        button.setOnClickListener(this);
        buttont.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                noti = isChecked;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttong:
                String cosa = editText.getText().toString();
                texto.setText(cosa);
                Runnable n = new Runnable() {
                    @Override
                    public void run() {
                        if(Casi_db==null){
                            Casi_db=new ArrayList<>();
                            Casi_db.add(cosa);
                        }
                        else{
                            Casi_db.add(cosa);
                        }
                        if(noti){
                            notificar(cosa);
                        }
                    }
                };
                n.run();
                break;
            default:
                break;
        }
    }
    public void notificar(String mensaje){
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(MainActivity.this, MiBroadcaster.class);
        intent.putExtra("mensaje",mensaje);
        intent.setAction(ACCION);
        PendingIntent intped= PendingIntent.getBroadcast(MainActivity.this,mensaje.hashCode(),intent,PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar t = Calendar.getInstance();
        alarm.set(AlarmManager.RTC_WAKEUP,t.getTimeInMillis()+2000, intped);
    };
}