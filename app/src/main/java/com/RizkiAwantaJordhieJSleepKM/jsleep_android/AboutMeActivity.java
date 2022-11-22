package com.RizkiAwantaJordhieJSleepKM.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.RizkiAwantaJordhieJSleepKM.jsleep_android.model.Account;

public class AboutMeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        Account sessionAccount = LoginActivity.loggedAccount;
        System.out.println(sessionAccount.toString());
        TextView nameAccount = findViewById(R.id.nameAccount);
        TextView emailAccount = findViewById(R.id.emailAccount);
        TextView balanceAccount = findViewById(R.id.balanceAccount);

        nameAccount.setText(sessionAccount.name);
        emailAccount.setText(sessionAccount.email);
        System.out.println(emailAccount.toString());
        balanceAccount.setText(Double.toString(sessionAccount.balance));


    }
}