package com.RizkiAwantaJordhieJSleepKM.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Calendar;
public class PaymentActivity extends AppCompatActivity {
    private CalendarView calendarView;
    public static String enddate;
    public static String startdate;
    Button continueInvoiceButton;
    ImageView backCreatePayment;
    EditText checkInDate, checkOutDate;
    DatePickerDialog datePickerDialogEnd,datePickerDialogStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        calendarView = findViewById(R.id.pdCalender);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        backCreatePayment = findViewById(R.id.backCreatePayment);

        backCreatePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move = new Intent(PaymentActivity.this, DetailRoomActivity.class);
                startActivity(move);
            }
        });


        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        datePickerDialogStart = new DatePickerDialog(PaymentActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        checkInDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        startdate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                    }
                }, mYear, mMonth, mDay);

        datePickerDialogEnd = new DatePickerDialog(PaymentActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        checkOutDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        enddate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                    }
                }, mYear, mMonth, mDay);

        checkInDate = findViewById(R.id.checkInDate);
        checkOutDate = findViewById(R.id.chechOutDate);

        checkInDate.setOnClickListener(v -> {
            datePickerDialogStart.show();
        });

        checkOutDate.setOnClickListener(v -> {
            datePickerDialogEnd.show();
        });

        continueInvoiceButton = findViewById(R.id.paymentdetail_button);

        continueInvoiceButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                startdate = checkInDate.getText().toString();
                enddate = checkOutDate.getText().toString();
                Intent move = new Intent(PaymentActivity.this, PaymentInvoiceActivity.class);
                startActivity(move);
            }
        });
    }
}