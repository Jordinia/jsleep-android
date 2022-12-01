package com.RizkiAwantaJordhieJSleepKM.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.RizkiAwantaJordhieJSleepKM.jsleep_android.model.Account;
import com.RizkiAwantaJordhieJSleepKM.jsleep_android.model.Room;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    Account sessionAccount = LoginActivity.loggedAccount;
//    Account test = LoginActivity.requestAccount();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        System.out.println(test.toString());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InputStream filepath = null;
        Gson gson = new Gson();
        ArrayList<Room> roomList = new ArrayList<>();
        ArrayList<String> idList = new ArrayList<>();

        try {
            filepath = getAssets().open("randomRoomList.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(filepath));
            Room[] temp = gson.fromJson(reader, Room[].class);
            Collections.addAll(roomList, temp);
        } catch (IOException t) {
            t.printStackTrace();
        }
        for (Room temp : roomList ) {
            idList.add(temp.name);
        }
        ArrayAdapter<String> roomAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,idList);
        ListView view = findViewById(R.id.Main_EntryList);
        view.setAdapter(roomAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.topmenu, menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent aboutMeIntent = new Intent(MainActivity.this,AboutMeActivity.class);
        switch (item.getItemId()){
            case R.id.person_menu:
                Toast.makeText(this, "About me", Toast.LENGTH_SHORT).show();
                startActivity(aboutMeIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}