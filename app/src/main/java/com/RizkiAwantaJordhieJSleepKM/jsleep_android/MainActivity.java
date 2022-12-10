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
import com.RizkiAwantaJordhieJSleepKM.jsleep_android.request.BaseApiService;
import com.RizkiAwantaJordhieJSleepKM.jsleep_android.request.UtilsApi;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    static BaseApiService mApiService;
    Account sessionAccount = LoginActivity.loggedAccount;
//    Account test = LoginActivity.requestAccount();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mApiService = UtilsApi.getApiService();
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
    protected static Account reloadAccount(int id){
        mApiService.getAccount(id).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful()) {
                    LoginActivity.loggedAccount = response.body();
                    System.out.println("reloadAccount SUCCESS") ;
                    System. out. println(LoginActivity.loggedAccount.toString()) ;
                }
            }
            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                System.out.println("REQUESTACCOUNT FAILED");
                System.out.println(t.toString());
                t.printStackTrace();
            }
        });
        return null;
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
        Intent createRoomIntent = new Intent(MainActivity.this,CreateRoomActivity.class);
        switch (item.getItemId()){
            case R.id.aboutMe:
                Toast.makeText(this, "About Me", Toast.LENGTH_SHORT).show();
                startActivity(aboutMeIntent);
                return true;
            case R.id.createRoom:
                if (LoginActivity.loggedAccount.renter != null){
                    Toast.makeText(this, "Create Room", Toast.LENGTH_SHORT).show();
                    startActivity(createRoomIntent);
                    return true;
                } else if (LoginActivity.loggedAccount.renter == null){
                    Toast.makeText(this, "You have not registered as Renter", Toast.LENGTH_SHORT).show();
                }
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}