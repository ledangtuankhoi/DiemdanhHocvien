package com.example.diemdanhhocvien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class account_login extends AppCompatActivity {

    private Button btnLogin;
    private EditText edUserName, edPassWord;
    private TextView txRegisterNow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_login);

        btnLogin =  findViewById(R.id.btnLogin);
        edUserName =  findViewById(R.id.edUsername);
        edPassWord =  findViewById(R.id.edPassword);
        txRegisterNow = findViewById(R.id.loginNow);

        txRegisterNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(account_login.this,account_register.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    code login
            }
        });

    }
}