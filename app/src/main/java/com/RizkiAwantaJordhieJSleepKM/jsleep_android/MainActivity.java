package com.RizkiAwantaJordhieJSleepKM.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.RizkiAwantaJordhieJSleepKM.jsleep_android.model.Account;
import com.RizkiAwantaJordhieJSleepKM.jsleep_android.model.Room;
import com.RizkiAwantaJordhieJSleepKM.jsleep_android.request.BaseApiService;
import com.RizkiAwantaJordhieJSleepKM.jsleep_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    static BaseApiService mApiService;
    String name;


    List<String> nameStr;
    static List<String> roomName = new ArrayList<>();
    List<Room> temp ;
    public static ArrayList<Room> listRoom;
    ListView list;

    Context mContext;
    Button nextButton, prevButton, goButton, myOrder;
    EditText editPage;
    public static int currentPage, tempPage, roomIndex;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentPage = 0;

        mApiService = UtilsApi.getApiService();
        mContext = this;

        nextButton  = findViewById(R.id.Main_NextButton);
        prevButton  = findViewById(R.id.createRoomButton);
        myOrder     = findViewById(R.id.main_OrderButton);
        goButton    = findViewById(R.id.Main_GoButton);
        editPage    = findViewById(R.id.Main_PageText);
        list = (ListView) findViewById(R.id.Main_RoomList);
        list.setOnItemClickListener(this::onItemClick);
        temp = getALLRoom(currentPage);

        myOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myOrderIntent = new Intent(MainActivity.this,BuyerOrderListActivity.class);
                startActivity(myOrderIntent);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                currentPage += 1;
                temp = getALLRoom(currentPage);
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentPage == 0){
                    Toast.makeText(mContext, "You are at the first page", Toast.LENGTH_SHORT).show();
                }
                else{
                    currentPage -= 1;
                    temp = getALLRoom(currentPage);
                }

            }
        });
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tempPage = Integer.parseInt(editPage.getText().toString());
                if (tempPage < 0){
                    Toast.makeText(mContext, "Wrong page input!!", Toast.LENGTH_SHORT).show();
                }
                currentPage += 1;
                temp = getALLRoom(tempPage);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.topmenu, menu);
        return true;
    }
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        MenuItem createRoom = menu.findItem(R.id.createRoom);
        MenuItem orderList = menu.findItem(R.id.orderList);
        if(LoginActivity.loggedAccount.renter == null){
            createRoom.setVisible(false);
            orderList.setVisible(false);
        }
        else {
            createRoom.setVisible(true);
            orderList.setVisible(true);
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent aboutMeIntent = new Intent(MainActivity.this,AboutMeActivity.class);
        Intent createRoomIntent = new Intent(MainActivity.this,CreateRoomActivity.class);
        Intent orderListIntent = new Intent(MainActivity.this,OrderListActivity.class);
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
            case R.id.orderList:
                if (LoginActivity.loggedAccount.renter != null){
                    Toast.makeText(this, "Order List", Toast.LENGTH_SHORT).show();
                    startActivity(orderListIntent);
                    return true;
                } else if (LoginActivity.loggedAccount.renter == null){
                    Toast.makeText(this, "You have not registered as Renter", Toast.LENGTH_SHORT).show();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void onItemClick (AdapterView<?> l, View v, int position, long id){
        System.out.println("onItemClick Success");
        Log.i("ListView", "You clicked Item np : " + id + " at position:" + position);

        Intent intent = new Intent(this, DetailRoomActivity.class);
        roomIndex = position;
        System.out.println("clicked");
        intent.setClass(mContext, DetailRoomActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("id", id);
        startActivity(intent);
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
    protected List<Room> getALLRoom(int page) {
        mApiService.getAllRoom(page, 5).enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (response.isSuccessful()) {
                    temp = response.body();
                    System.out.println(temp.toString());
                    assert temp != null;
                    listRoom = new ArrayList<Room>(temp);
                    CustomListAdapter adapter = new CustomListAdapter(mContext, listRoom);
                    list.setAdapter(adapter);
                    System.out.println("Get Room Success");


                }
            }
            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(mContext, "get room failed", Toast.LENGTH_SHORT).show();
            }

        });
        return null;
    }
    public static ArrayList<String> getName(List<Room> list) {
        ArrayList<String> ret = new ArrayList<String>();
        int i;
        for (i = 0; i < list.size(); i++) {
            ret.add(list.get(i).name);
        }
        return ret;
    }
}