package com.example.diemdanhhocvienandroid2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

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
import com.example.diemdanhhocvienandroid2.api.account.LoginRequest;
import com.example.diemdanhhocvienandroid2.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class account_login extends AppCompatActivity {

    private Button btnLogin;
    private EditText edUserName, edPassWord;
    private TextView txRegisterNow;
    public User userCurrent;

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

                if (
                        TextUtils.isEmpty(edUserName.getText().toString())
                                || TextUtils.isEmpty(edUserName.getText().toString())
                                || TextUtils.isEmpty(edPassWord.getText().toString())
                ) {
                    Toast.makeText(account_login.this, "All request", Toast.LENGTH_SHORT).show();
                } else {


                    LoginRequest loginRequest = new LoginRequest();
                    loginRequest.setUsername(edUserName.getText().toString());
                    loginRequest.setPassword(edPassWord.getText().toString());

                    LoginUser(loginRequest);
                }
            }
        });

    }

    public void LoginUser(LoginRequest loginRequest){

        Call<User> userCall = ApiClient.getService().loginUser(loginRequest);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    User user = response.body();
                    //goToHomeFragment
                    goToHomeFragment(user);

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                String msg = "user or pass false .. ";
                Toast.makeText(account_login.this, msg, Toast.LENGTH_SHORT).show();
                Log.d("onFailure: ", t.getMessage());
            }
        });

    }


    public void goToHomeFragment(User user) {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_user", user);
        intent.putExtras(bundle);
        startActivity(intent);

    }


}