package com.example.asus.androidthreadtest;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final int UPDATE_TEXT = 1;
    private TextView textView;
    private Handler handler = new Handler(){
        public void handleMessage(Message mes){
            switch(mes.what){
                case UPDATE_TEXT :
                    textView.setText("nice to meet you");
                    break;
                default:break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.text);
        Button changetext = (Button)findViewById(R.id.change_text);
        changetext.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = UPDATE_TEXT;
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });
    }
}
