package com.example.music;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button play, pause;
    MediaPlayer mediaPlayer;
    SensorManager manager;
    Sensor proxy;
    SmsManager smsa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = findViewById(R.id.play);
        pause = findViewById(R.id.pause);


        mediaPlayer = MediaPlayer.create(this, R.raw.song);

        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        assert manager != null;
        proxy = manager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        manager.registerListener(proxylistner, proxy, SensorManager.SENSOR_DELAY_NORMAL);

        smsa=SmsManager.getDefault();
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
            }
        });
    }

    SensorEventListener proxylistner =new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.values[0] == 0) {
                mediaPlayer.pause();
                smsa.sendTextMessage("+917814388776","","denger",null,null);
            } else {
                mediaPlayer.start();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

}