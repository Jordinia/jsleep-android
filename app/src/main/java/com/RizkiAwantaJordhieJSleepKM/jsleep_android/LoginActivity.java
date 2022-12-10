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
    public static BaseApiService mApiService;
    EditText email,password;
    public static Context mContext;
    public static Account loggedAccount = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mApiService = UtilsApi.getApiService();
        mContext = this;
        email = findViewById(R.id.LoginEmailTextBox);
        password = findViewById(R.id.LoginPasswordTextBox);

        TextView register = findViewById(R.id.LoginRegisterNow);
        Button login = findViewById(R.id.LoginButton);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(email.getText().toString());
                System.out.println(password.getText().toString());
                Account account = requestLogin();
                if(loggedAccount != null){
                    movetoMainActivity();
                }


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

    protected Account requestLogin(){
        mApiService.login(email.getText().toString(),password.getText().toString()).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){
                    Toast.makeText(mContext,"Login Succesful",Toast.LENGTH_SHORT).show();
                    loggedAccount = response.body();
                    System.out.println("LOGIN SUCCESSFUL!!") ;
                    System.out.println(loggedAccount.toString()) ;
                    System.out.println(loggedAccount.id) ;
                    movetoMainActivity();
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                System.out.println("LOGIN ERROR!!");
                System.out.println(t.toString());
                Toast.makeText(mContext,"Login Error",Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
    public static Account requestAccount(){
        mApiService.getAccount(loggedAccount.id).enqueue(new Callback<Account>(){
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){
                    Account account;
                    account = response.body();
                    System. out. println("REQUESTACCOUNT SUCCESSFUL") ;
                    System. out. println(account.toString()) ;
                }

            }

            @Override
            public void onFailure(Call<Account> call, Throwable t){
                System.out.println("REQUESTACCOUNT FAILED");
                System.out.println(t.toString());
                Toast.makeText(mContext,"no Account id=0",Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
    public void movetoMainActivity(){
        Intent move = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(move);
    }
}