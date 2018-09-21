package com.example.asus.servicetest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MyService.DownloadBinder downloadBinder;
    private ServiceConnection connection = new ServiceConnection()
    {
    public void onServiceDisconnected(ComponentName name){
    }
    public void onServiceConnected(ComponentName name, IBinder service){
        downloadBinder = (MyService.DownloadBinder)service;
        downloadBinder.getProgress();
        downloadBinder.startDownload();;
    }
};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startService = (Button) findViewById(R.id.start_servoce);
        startService.setOnClickListener(this);
        Button stopService = (Button) findViewById(R.id.stop_service);
        stopService.setOnClickListener(this);
        Button bindService = (Button)findViewById(R.id.bind_service);
        bindService.setOnClickListener(this);
        Button unbindService = (Button)findViewById(R.id.unbind_service);
        unbindService.setOnClickListener(this);
        Button startIntentService = (Button)findViewById(R.id.start_intentservice);
        startIntentService.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_servoce:
                Intent startintent = new Intent(this, MyService.class);
                startService(startintent);
                break;
            case R.id.stop_service:
                Intent stopintent = new Intent(this, MyService.class);
                stopService(stopintent);
                break;
            case R.id.bind_service:
                Intent bindIntent = new Intent(this,MyService.class);
                bindService(bindIntent,connection,BIND_AUTO_CREATE);
                break;
            case R.id.unbind_service:
                unbindService(connection);
                break;
            case R.id.start_intentservice:
                Log.d("MainActivity","thread id id"+Thread.currentThread().getId());
                Intent intentService = new Intent(this,MyIntentService.class);
                startService(intentService);
                break;
            default:
                break;
        }
    }
}

