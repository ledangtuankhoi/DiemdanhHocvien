package com.example.diemdanhhocvienandroid2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diemdanhhocvienandroid2.api.ApiClient;
import com.example.diemdanhhocvienandroid2.api.account.RegisterRequest;
import com.example.diemdanhhocvienandroid2.api.account.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class account_register extends AppCompatActivity {


    private EditText edUserName, edFirstName, edLastName, edEmail, edPassword;
    private Button btnRegister;
    private TextView txLoginNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_register);

        edUserName = findViewById(R.id.edUsername);
        edFirstName = findViewById(R.id.edFirstName);
        edLastName = findViewById(R.id.edLastName);
        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        txLoginNow = findViewById(R.id.loginNow);
        btnRegister = findViewById(R.id.btnRegister);

        txLoginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(account_register.this, account_login.class));
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (
                        TextUtils.isEmpty(edUserName.getText().toString())
                                || TextUtils.isEmpty(edEmail.getText().toString())
                                || TextUtils.isEmpty(edFirstName.getText().toString())
                                || TextUtils.isEmpty(edLastName.getText().toString())
                                || TextUtils.isEmpty(edPassword.getText().toString())
                ) {
                    Toast.makeText(account_register.this, "All request", Toast.LENGTH_SHORT).show();
                } else {

                    RegisterRequest registerRequest = new RegisterRequest();

                    registerRequest.setUsername(edUserName.getText().toString());
                    registerRequest.setEmail(edEmail.getText().toString());
                    registerRequest.setFirstName(edFirstName.getText().toString());
                    registerRequest.setLastName(edLastName.getText().toString());
                    registerRequest.setPassword(edPassword.getText().toString());
                    registerUser(registerRequest);
                }
            }
        });

    }

    public void registerUser(RegisterRequest registerRequest) {
        Call<RegisterResponse> registerResponseCall = ApiClient.getService().RegisterUser(registerRequest);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                if (response.isSuccessful()) {
                    String msg = "Success... ";
                    Toast.makeText(account_register.this, msg, Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(account_register.this, account_login.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                String msg = "an error occurred please try again later... ";
                Toast.makeText(account_register.this, msg, Toast.LENGTH_SHORT).show();
                Log.d("onFailure: ", t.getMessage());

            }
        });
    }
}