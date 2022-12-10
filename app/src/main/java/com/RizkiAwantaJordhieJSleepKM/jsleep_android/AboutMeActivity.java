package com.RizkiAwantaJordhieJSleepKM.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.RizkiAwantaJordhieJSleepKM.jsleep_android.model.Renter;
import com.RizkiAwantaJordhieJSleepKM.jsleep_android.request.BaseApiService;
import com.RizkiAwantaJordhieJSleepKM.jsleep_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutMeActivity extends AppCompatActivity {
    BaseApiService mApiService;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        mApiService = UtilsApi.getApiService();
        mContext = this;

        //Account Details
        System.out.println(LoginActivity.loggedAccount.toString());
        TextView nameAccount = findViewById(R.id.nameAccount);
        TextView emailAccount = findViewById(R.id.emailAccount);
        TextView balanceAccount = findViewById(R.id.balanceAccount);
        nameAccount.setText(LoginActivity.loggedAccount.name);
        emailAccount.setText(LoginActivity.loggedAccount.email);
        System.out.println(emailAccount.toString());
        balanceAccount.setText(Double.toString(LoginActivity.loggedAccount.balance));
        Button topUpButton = findViewById(R.id.buttonTopUp);
        EditText topUpAmount = findViewById(R.id.editTopUpAmount);
        //Renter = Null
        ConstraintLayout nullRenterConst = findViewById(R.id.nullRenterConstraint);
        TextView nullRenter = findViewById(R.id.nullRenterInfo);
        Button registerRenter = findViewById(R.id.buttonRegister);
        //Renter = Register
        ConstraintLayout regisRenterConst = findViewById(R.id.regisRenterConstraint);
        EditText registName = findViewById(R.id.registerRenterName);
        EditText registAddress = findViewById(R.id.registerRenterAddress);
        EditText registPhone = findViewById(R.id.createRoomSize);
        Button registerButton = findViewById(R.id.Main_PrevButton);
        Button registerCancel = findViewById(R.id.bookNowButton);
        //Renter = Registered
        ConstraintLayout listRenterConst = findViewById(R.id.listRenterConstraint);
        TextView nameRent = findViewById(R.id.nameRent);
        TextView nameRenter = findViewById(R.id.nameRenter);
        TextView addressRent = findViewById(R.id.addressRent);
        TextView addressRenter = findViewById(R.id.addressRenter);
        TextView phoneRent = findViewById(R.id.phoneRent);
        TextView phoneRenter = findViewById(R.id.phoneRenter);

        topUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(topUpAmount.getText().toString().equalsIgnoreCase("0") || topUpAmount.getText().toString().equalsIgnoreCase("" )){
                    System.out.println("Top Up Failed: Enter Required Top Up Amount!!") ;
                    Toast.makeText(mContext, "Top Up Failed: Enter Required Top Up Amount!!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean topUp = requestTopUp(LoginActivity.loggedAccount.id,Integer.parseInt(topUpAmount.getText().toString()));
                }
            }
        });
        if (LoginActivity.loggedAccount.renter == null) {
            nullRenterConst.setVisibility(ConstraintLayout.VISIBLE);
            listRenterConst.setVisibility(ConstraintLayout.GONE);
            regisRenterConst.setVisibility(ConstraintLayout.GONE);

            registerRenter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nullRenterConst.setVisibility(ConstraintLayout.GONE);
                    listRenterConst.setVisibility(ConstraintLayout.GONE);
                    regisRenterConst.setVisibility(ConstraintLayout.VISIBLE);

                    registerButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Renter renter = requestRegisterRenter(LoginActivity.loggedAccount.id, registName.getText().toString(), registAddress.getText().toString(), registPhone.getText().toString());

                            nullRenterConst.setVisibility(ConstraintLayout.GONE);
                            listRenterConst.setVisibility(ConstraintLayout.GONE);
                            regisRenterConst.setVisibility(ConstraintLayout.VISIBLE);

                        }
                    });

                    registerCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            nullRenterConst.setVisibility(ConstraintLayout.VISIBLE);
                            listRenterConst.setVisibility(ConstraintLayout.GONE);
                            regisRenterConst.setVisibility(ConstraintLayout.GONE);
                        }
                    });



                }
            });
        }
        else {
            nullRenterConst.setVisibility(ConstraintLayout.GONE);
            listRenterConst.setVisibility(ConstraintLayout.VISIBLE);
            regisRenterConst.setVisibility(ConstraintLayout.GONE);
            nameRenter.setText(LoginActivity.loggedAccount.renter.username);
            addressRenter.setText(LoginActivity.loggedAccount.renter.address);
            phoneRenter.setText(LoginActivity.loggedAccount.renter.phoneNumber);
        }
    }

    protected Renter requestRegisterRenter(int id, String username, String address, String phoneNumber ) throws NullPointerException {
        System.out.println("Id: " + id);
        System.out.println("Username: " + username);
        System.out.println("Address: " + address);
        System.out.println("Phone: " + phoneNumber);
        mApiService.registerRenter(id, username, address, phoneNumber).enqueue(new Callback<Renter>() {
            @Override
            public void onResponse(Call<Renter> call, Response<Renter> response) {
                if(response.isSuccessful()){
                    Renter renter = response.body();
                    LoginActivity.loggedAccount.renter = renter;
                    System.out.println("REGISTER RENTER SUCCESSFUL!!") ;
                    Toast.makeText(mContext, "Register Renter Successful", Toast.LENGTH_SHORT).show();
                    MainActivity.reloadAccount(LoginActivity.loggedAccount.id);
                    Intent move = new Intent(AboutMeActivity.this, AboutMeActivity.class);
                    startActivity(move);
                }
            }

            @Override
            public void onFailure(Call<Renter> call, Throwable t) {
                System.out.println("REGISTER RENTER FAILED!!");
                System.out.println(t.toString());
                Toast.makeText(mContext, "Register Renter Failed", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
    protected Boolean requestTopUp(int id, int balance ){
        System.out.println("Id: " + id);
        System.out.println("TopUp: " + balance);
        mApiService.topUp(id,balance).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    Boolean topUpResult = response.body();
                    System.out.println(topUpResult + "TOPUP SUCCESSFUL!!") ;
                    LoginActivity.loggedAccount.balance += balance;
                    Toast.makeText(mContext, "Top Up Successful", Toast.LENGTH_SHORT).show();
                    MainActivity.reloadAccount(LoginActivity.loggedAccount.id);
                    Intent move = new Intent(AboutMeActivity.this, AboutMeActivity.class);
                    startActivity(move);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                System.out.println("TOPUP ERROR!!");
                System.out.println(t.toString());
                Toast.makeText(mContext,"Top Up Failed",Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}