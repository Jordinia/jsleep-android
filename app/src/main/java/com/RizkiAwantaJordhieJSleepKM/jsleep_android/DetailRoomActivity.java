package com.RizkiAwantaJordhieJSleepKM.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.RizkiAwantaJordhieJSleepKM.jsleep_android.model.Facility;
import com.RizkiAwantaJordhieJSleepKM.jsleep_android.model.Room;

import java.util.Collections;
import java.util.List;

public class DetailRoomActivity extends AppCompatActivity {

    public static Room room;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_room);

        room = MainActivity.listRoom.get(MainActivity.roomIndex);
        List<Facility> facilityList = Collections.singletonList(MainActivity.listRoom.get(MainActivity.roomIndex).facility);
        TextView roomName = findViewById(R.id.roomName);
        TextView roomBedtype = findViewById(R.id.roomBedType);
        TextView roomCity = findViewById(R.id.roomCity);
        TextView roomPrice = findViewById(R.id.roomPrice);
        TextView roomSize = findViewById(R.id.roomSize);
        TextView roomAddress = findViewById(R.id.roomAddress);

        CheckBox ac = findViewById(R.id.acFacility);
        CheckBox refrig = findViewById(R.id.refrigFacility);
        CheckBox wifi = findViewById(R.id.wifiFacility);
        CheckBox bathtub = findViewById(R.id.bathtubFacility);
        CheckBox balcony = findViewById(R.id.balconyFacility);
        CheckBox restaurant = findViewById(R.id.restoFacility);
        CheckBox pool = findViewById(R.id.poolFacility);
        CheckBox fitness = findViewById(R.id.fitnessFacility);

        Button buttonBookNow = findViewById(R.id.bookNowButton);

        roomName.setText(room.name);
        String roomprice = String.format("%.0f", room.price.price );
        String price = "Rp. " + String.valueOf(roomprice + "/ Night");
        roomPrice.setText(price);
        String size = String.valueOf(room.size) + " M";
        roomSize.setText(size);
        roomAddress.setText(room.address);
        roomCity.setText(room.city.toString());
        roomBedtype.setText(room.bedType.toString());

//        for (int i = 0; i < room.facility.size(); i++) {
//            if (room.facility.get(i).equals(Facility.AC)) {
//                ac.setChecked(true);
//            } else if (room.facility.get(i).equals(Facility.Refrigerator)) {
//                refrig.setChecked(true);
//            } else if (room.facility.get(i).equals(Facility.WiFi)) {
//                wifi.setChecked(true);
//            } else if (room.facility.get(i).equals(Facility.Bathtub)) {
//                bathtub.setChecked(true);
//            } else if (room.facility.get(i).equals(Facility.Balcony)) {
//                balcony.setChecked(true);
//            } else if (room.facility.get(i).equals(Facility.Restaurant)) {
//                restaurant.setChecked(true);
//            } else if (room.facility.get(i).equals(Facility.SwimmingPool)) {
//                pool.setChecked(true);
//            } else if (room.facility.get(i).equals(Facility.FitnessCenter)) {
//                fitness.setChecked(true);
//            }
//        }
        for(Facility facility: facilityList){
            if (facility == Facility.AC){
                ac.setChecked(true);
            }else if(facility == Facility.Balcony){
                balcony.setChecked(true);
            }else if(facility == Facility.Bathtub) {
                bathtub.setChecked(true);
            }else if(facility == Facility.FitnessCenter){
                fitness.setChecked(true);
            }else if(facility == Facility.Refrigerator){
                refrig.setChecked(true);
            }else if(facility == Facility.Restaurant){
                restaurant.setChecked(true);
            }else if(facility == Facility.SwimmingPool){
                pool.setChecked(true);
            }else if(facility == Facility.WiFi){
                wifi.setChecked(true);
            }
        }


        buttonBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move = new Intent(DetailRoomActivity.this, PaymentActivity.class);
                startActivity(move);
            }
        });
    }
}