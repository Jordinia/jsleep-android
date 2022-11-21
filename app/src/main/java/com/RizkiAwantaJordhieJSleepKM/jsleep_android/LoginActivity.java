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

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Call;


public class LoginActivity extends AppCompatActivity {
    BaseApiService mApiService;
    EditText username,password;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mApiService = UtilsApi.getApiService();
        mContext = this;
        username = findViewById(R.id.LoginEmailTextBox);
        password = findViewById(R.id.LoginPasswordTextBox);

        TextView register = findViewById(R.id.LoginRegisterNow);
        Button login = findViewById(R.id.LoginButton);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Account account = requestAccount();
                Intent move = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(move);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(move);
            }

        });
    }

    protected Account requestAccount(){
        mApiService.getAccount(0).enqueue(new Callback<Account>(){
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){
                    Account account;
                    account = response.body();
                    System. out. println("BERHASIL") ;
                    System. out. println(account.toString()) ;
                }

            }

            @Override
            public void onFailure(Call<Account> call, Throwable t){
                System.out.println("gagal");
                System.out.println(t.toString());
                Toast.makeText(mContext,"no Account id=0",Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}