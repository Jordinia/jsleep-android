package com.RizkiAwantaJordhieJSleepKM.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.RizkiAwantaJordhieJSleepKM.jsleep_android.model.Account;
import com.RizkiAwantaJordhieJSleepKM.jsleep_android.request.BaseApiService;
import com.RizkiAwantaJordhieJSleepKM.jsleep_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    BaseApiService mApiService;
    EditText username,email,password;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mApiService = UtilsApi.getApiService();
        mContext = this;
        username = findViewById(R.id.RegisterUsernameTextBox);
        email = findViewById(R.id.RegisterEmailTextBox);
        password = findViewById(R.id.RegisterPasswordTextBox);
        Button Register = findViewById(R.id.RegisterButtonMain);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(username.getText().toString());
                System.out.println(email.getText().toString());
                System.out.println(password.getText().toString());
                requestRegister();

            }
        });
    }

    protected Account requestRegister(){
        mApiService.register(username.getText().toString(),email.getText().toString(), password.getText().toString()).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){
                    LoginActivity.loggedAccount = response.body();
                    Intent move = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(move);
                    Toast.makeText(mContext, "Register Successfull", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t){
                System.out.println(t.toString());
                Toast.makeText(mContext, "Already registered", Toast.LENGTH_SHORT).show();
            }
        });

        return null;
    }
}