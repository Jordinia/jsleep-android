package com.RizkiAwantaJordhieJSleepKM.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.RizkiAwantaJordhieJSleepKM.jsleep_android.model.Facility;
import com.RizkiAwantaJordhieJSleepKM.jsleep_android.model.Room;

public class DetailRoomActivity extends AppCompatActivity {

    public static Room room;
    public static String roomprice;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_room);

        room = MainActivity.listRoom.get(MainActivity.roomIndex);
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

        Button buttonBookNow = findViewById(R.id.cancelButton);

        roomName.setText(room.name);
        roomprice = String.format("%.0f", room.price.price );
        String price = "Rp. " + String.valueOf(roomprice + "/ Night");
        roomPrice.setText(price);
        String size = String.valueOf(room.size) + " M";
        roomSize.setText(size);
        roomAddress.setText(room.address);
        roomCity.setText(room.city.toString());
        roomBedtype.setText(room.bedType.toString());

        for (int i = 0; i < room.facility.size(); i++) {
            if (room.facility.get(i).equals(Facility.AC )) {
                ac.setChecked(true);
            } else if (room.facility.get(i).equals(Facility.Refrigerator)) {
                refrig.setChecked(true);
            } else if (room.facility.get(i).equals(Facility.WiFi)) {
                wifi.setChecked(true);
            } else if (room.facility.get(i).equals(Facility.Bathtub)) {
                bathtub.setChecked(true);
            } else if (room.facility.get(i).equals(Facility.Balcony)) {
                balcony.setChecked(true);
            } else if (room.facility.get(i).equals(Facility.Restaurant)) {
                restaurant.setChecked(true);
            } else if (room.facility.get(i).equals(Facility.SwimmingPool)) {
                pool.setChecked(true);
            } else if (room.facility.get(i).equals(Facility.FitnessCenter)) {
                fitness.setChecked(true);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.homemenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent MainIntent = new Intent(DetailRoomActivity.this,MainActivity.class);
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