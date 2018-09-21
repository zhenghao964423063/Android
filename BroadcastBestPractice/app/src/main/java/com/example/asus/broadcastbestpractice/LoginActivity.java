package com.example.asus.broadcastbestpractice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private CheckBox remember;
    private EditText accontEdit;
    private EditText passwordEdit;
    private Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        accontEdit = (EditText)findViewById(R.id.accont);
        passwordEdit = (EditText)findViewById(R.id.password);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        remember = (CheckBox)findViewById(R.id.remenber_pass);
        boolean isremember = pref.getBoolean("remember_pass",false);
        if(isremember){
            String account = pref.getString("account","");
            String password = pref.getString("password","");
            accontEdit.setText(account);
            passwordEdit.setText(password);
            remember.setChecked(true);
        }
        login = (Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                String accont = accontEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                if(accont.equals("admin")&&password.equals("123456")){
                    editor = pref.edit();
                    if(remember.isChecked()){
                        editor.putString("account",accont);
                        editor.putString("password",password);
                        editor.putBoolean("remember_pass",true);
                    }else
                        editor.clear();
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else
                    Toast.makeText(LoginActivity.this,"accont or password is invalid",Toast.LENGTH_LONG).show();
            }
        });
    }
}
