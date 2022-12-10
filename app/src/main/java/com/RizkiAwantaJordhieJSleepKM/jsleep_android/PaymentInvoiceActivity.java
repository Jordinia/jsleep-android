package com.RizkiAwantaJordhieJSleepKM.jsleep_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.RizkiAwantaJordhieJSleepKM.jsleep_android.model.Payment;
import com.RizkiAwantaJordhieJSleepKM.jsleep_android.request.BaseApiService;
import com.RizkiAwantaJordhieJSleepKM.jsleep_android.request.UtilsApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentInvoiceActivity extends AppCompatActivity{
    BaseApiService mApiService;
    Payment payment;
    Context mContext;
    Button createButton;
    ImageView backPaymentInvoice;
    TextView cpFrom,cpTo,cpName,cpAddress,cpTotalPrice,cpPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_invoice);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        mApiService = UtilsApi.getApiService();
        mContext = this;

        backPaymentInvoice = findViewById(R.id.backPaymentInvoie);
        backPaymentInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move = new Intent(PaymentInvoiceActivity.this, PaymentActivity.class);
                startActivity(move);
            }
        });

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date endDate = dateFormat.parse(PaymentActivity.enddate);
            Date startDate = dateFormat.parse(PaymentActivity.startdate);
            long diff = endDate.getTime() - startDate.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);

            cpName = findViewById(R.id.createpayment_title_name);
            cpAddress = findViewById(R.id.createpayment_title_address);
            cpName.setText(DetailRoomActivity.room.name);
            cpAddress.setText(DetailRoomActivity.room.address);
            cpPrice = findViewById(R.id.piPrice);
            cpTotalPrice = findViewById(R.id.createpayment_price);

            createButton = findViewById(R.id.createpayment_button);
            cpFrom = findViewById(R.id.createpayment_from);
            cpFrom.setText(PaymentActivity.startdate);
            cpTo = findViewById(R.id.createpayment_to);
            cpTo.setText(PaymentActivity.enddate);
            cpPrice.setText(String.valueOf(DetailRoomActivity.room.price.price));
            cpTotalPrice.setText(String.valueOf(((double)diffDays) * DetailRoomActivity.room.price.price));



            createButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Clicked");
                    createPayment(LoginActivity.loggedAccount.id,
                            DetailRoomActivity.room.accountId,
                            DetailRoomActivity.room.id,
                            PaymentActivity.startdate,
                            PaymentActivity.enddate);
                }
            });

        } catch (ParseException e) {
            e.printStackTrace();
        }




    }


    protected Payment createPayment(int buyerId,
                                    int renterId,
                                    int roomId,
                                    String from,
                                    String to){
        System.out.println("Callback");
        //print all parameter
        System.out.println(buyerId);
        System.out.println(renterId);
        System.out.println(roomId);
        System.out.println(from);
        System.out.println(to);
        mApiService.createPayment(buyerId, renterId, roomId, from, to).enqueue(new Callback<Payment>() {

            @Override
            public void onResponse(@NonNull Call<Payment> call, @NonNull Response<Payment> response) {
                if(response.isSuccessful()){
                    System.out.println("Payment Success");
                    payment = response.body();
                    System.out.println(payment);
                    Intent move = new Intent(PaymentInvoiceActivity.this,MainActivity.class);
                    startActivity(move);
                    Toast.makeText(mContext, "Payment Success", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Payment> call, @NonNull Throwable t) {
                Toast.makeText(mContext, "PAYMENT ERROR!!", Toast.LENGTH_SHORT).show();
                System.out.println(t.toString());
            }
        });
        return null;
    }



}