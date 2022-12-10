package com.RizkiAwantaJordhieJSleepKM.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.RizkiAwantaJordhieJSleepKM.jsleep_android.model.BedType;
import com.RizkiAwantaJordhieJSleepKM.jsleep_android.model.City;
import com.RizkiAwantaJordhieJSleepKM.jsleep_android.model.Facility;
import com.RizkiAwantaJordhieJSleepKM.jsleep_android.model.Room;
import com.RizkiAwantaJordhieJSleepKM.jsleep_android.request.BaseApiService;
import com.RizkiAwantaJordhieJSleepKM.jsleep_android.request.UtilsApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateRoomActivity extends AppCompatActivity {
    BaseApiService mApiService;
    Context mContext;
    ArrayList<Facility> facility = new ArrayList<Facility>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        mApiService = UtilsApi.getApiService();
        mContext = this;

        //components
        EditText name = findViewById(R.id.createRoomName);
        EditText price = findViewById(R.id.createRoomPrice);
        EditText address = findViewById(R.id.createRoomAddress);
        EditText size = findViewById(R.id.createRoomSize);
        //facility
        ToggleButton ac = findViewById(R.id.acFacility);
        ToggleButton fridge = findViewById(R.id.refrigFacility);
        ToggleButton bathtub = findViewById(R.id.bathtubFacility);
        ToggleButton balcony = findViewById(R.id.balconyFacility);
        ToggleButton pool = findViewById(R.id.poolFacility);
        ToggleButton fitness = findViewById(R.id.fitnessFacility);
        ToggleButton resto = findViewById(R.id.restoFacility);
        ToggleButton wifi = findViewById(R.id.wifiFacility);
        //spinner
        Spinner spinnerBed = findViewById(R.id.spinnerBedType);
        Spinner spinnerCity = findViewById(R.id.spinnerCity);
        spinnerBed.setAdapter(new ArrayAdapter<BedType>(this, android.R.layout.simple_spinner_item, BedType.values()));
        spinnerCity.setAdapter(new ArrayAdapter<City>(this, android.R.layout.simple_spinner_item, City.values()));
        //button
        Button submitRoom = findViewById(R.id.Main_PrevButton);
        Button roomCancel = findViewById(R.id.bookNowButton);

        roomCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move = new Intent(CreateRoomActivity.this, MainActivity.class);
                startActivity(move);
            }
        });
        submitRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LoginActivity.loggedAccount.renter != null){
                    if (ac.isChecked()) {
                        facility.add(Facility.AC);
                    }
                    if (fridge.isChecked()) {
                        facility.add(Facility.Refrigerator);
                    }
                    if (wifi.isChecked()) {
                        facility.add(Facility.WiFi);
                    }
                    if (bathtub.isChecked()) {
                        facility.add(Facility.Bathtub);
                    }
                    if (balcony.isChecked()) {
                        facility.add(Facility.Balcony);
                    }
                    if (resto.isChecked()) {
                        facility.add(Facility.Restaurant);
                    }
                    if (pool.isChecked()) {
                        facility.add(Facility.SwimmingPool);
                    }
                    if (fitness.isChecked()) {
                        facility.add(Facility.FitnessCenter);
                    }
                    String bed = spinnerBed.getSelectedItem().toString();
                    String cityStr = spinnerCity.getSelectedItem().toString();
                    BedType bedType = BedType.valueOf(bed);
                    City city = City.valueOf(cityStr);

                    Integer roomPrice = new Integer(price.getText().toString());
                    Integer roomSize = new Integer(size.getText().toString());

                    int fixedPrice = roomPrice.parseInt(price.getText().toString());
                    int fixedSIze = roomSize.parseInt(size.getText().toString());
                    //price.price = priceInt;
                    Room room = requestRoom(
                            LoginActivity.loggedAccount.id,
                            name.getText().toString(),
                            fixedSIze,
                            fixedPrice,
                            facility,
                            city,
                            address.getText().toString(),
                            bedType);
                } else if (LoginActivity.loggedAccount.renter != null){

                }

            }
        });
    }

    protected Room requestRoom(int id, String name, int size, int price, ArrayList<Facility> facility, City city, String address, BedType bedType) {
        mApiService.createRoom(id, name, size, price, facility, city, address, bedType).enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                if (response.isSuccessful()) {
                    Room room = response.body();
                    System.out.println("CREATE ROOM SUCCESSFUL!!") ;
                    Toast.makeText(mContext, "Create Room Successful", Toast.LENGTH_SHORT).show();
                    MainActivity.reloadAccount(LoginActivity.loggedAccount.id);
                    Intent move = new Intent(CreateRoomActivity.this, MainActivity.class);
                    startActivity(move);
                }
            }

            @Override
            public void onFailure(Call<Room> call, Throwable t) {
                System.out.println("CREATE ROOM FAILED!!");
                System.out.println(t.toString());
                Toast.makeText(mContext, "Create Room Failed", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}