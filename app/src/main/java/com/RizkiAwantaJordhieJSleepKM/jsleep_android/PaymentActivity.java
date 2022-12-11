package com.RizkiAwantaJordhieJSleepKM.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.RizkiAwantaJordhieJSleepKM.jsleep_android.model.Room;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PaymentActivity extends AppCompatActivity {
    public static String enddate;
    public static String startdate;
    Button continueInvoiceButton;
    ImageView backDetailRoom;
    EditText checkInDate, checkOutDate;
    DatePickerDialog datePickerDialogEnd,datePickerDialogStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        backDetailRoom = findViewById(R.id.backCreatePayment);

        backDetailRoom.setOnClickListener(new View.OnClickListener() {
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

        checkInDate = findViewById(R.id.dateCheckIn);
        checkOutDate = findViewById(R.id.dateCheckOut);

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
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                startdate = checkInDate.getText().toString();
                enddate = checkOutDate.getText().toString();
                Intent move = new Intent(PaymentActivity.this, PaymentInvoiceActivity.class);
                startActivity(move);
                try {
                    Date start = dateFormat.parse(startdate);
                    Date end = dateFormat.parse(enddate);
                    if(availability(start,end,DetailRoomActivity.room)){
                        Intent intent = new Intent(PaymentActivity.this, PaymentInvoiceActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(PaymentActivity.this, "Room Already Booked on Selected Date", Toast.LENGTH_SHORT).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public boolean availability(Date from, Date to, Room room){


        if(from.after(to) || from.equals(to)){
            return false;
        }

        for (Date i : room.booked) {
            if (from.equals(i)) {
                return false;
            } else if(from.before(i)){
                if(from.before(i) && to.after(i)){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.homemenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent MainIntent = new Intent(PaymentActivity.this,MainActivity.class);
        switch (item.getItemId()){
            case R.id.homeMainIntent:
                Toast.makeText(this, "Returning to Home", Toast.LENGTH_SHORT).show();
                startActivity(MainIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}