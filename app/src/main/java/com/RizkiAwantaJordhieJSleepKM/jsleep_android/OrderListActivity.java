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

import com.RizkiAwantaJordhieJSleepKM.jsleep_android.model.Payment;
import com.RizkiAwantaJordhieJSleepKM.jsleep_android.request.BaseApiService;
import com.RizkiAwantaJordhieJSleepKM.jsleep_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderListActivity extends AppCompatActivity {

    public static int currentPage, orderIndex;
    public static ArrayList<Payment> orderListData;
    Context mContext;
    BaseApiService mApiService;
    ListView orderList;
    Button nextButton, prevButton, goButton;
    EditText editPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        currentPage = 0;


        mApiService = UtilsApi.getApiService();
        mContext = this;

        nextButton  = findViewById(R.id.OrderList_NextButton);
        prevButton  = findViewById(R.id.OrderList_PrevButton);
        goButton    = findViewById(R.id.OrderList_GoButton);
        editPage    = findViewById(R.id.OrderList_PageText);
        orderList = findViewById(R.id.OrderList_RoomList);
        orderList.setOnItemClickListener(this::onItemClick);
        getOrderForRenter(LoginActivity.loggedAccount.id, 0);
        Toast.makeText(mContext, "Available Orders", Toast.LENGTH_SHORT).show();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                currentPage += 1;
                getOrderForRenter(LoginActivity.loggedAccount.id, currentPage);
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
                    getOrderForRenter(LoginActivity.loggedAccount.id, currentPage);
                }

            }
        });

    }

    protected void getOrderForRenter(int renterId, int page){
        mApiService.getOrderForRenter(renterId,page,5).enqueue(new Callback<List<Payment>>() {
            @Override
            public void onResponse(Call<List<Payment>> call, Response<List<Payment>> response) {
                if(response.isSuccessful()){
                    List<Payment> orderlist = response.body();
                    assert orderlist != null;
                    orderListData = new ArrayList<Payment>(orderlist);
                    CustomOrderListAdapter adapter = new CustomOrderListAdapter(mContext,orderListData);
                    orderList.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<List<Payment>> call, Throwable t) {
                Toast.makeText(mContext, "Order List Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void onItemClick (AdapterView<?> l, View v, int position, long id){
        System.out.println("onItemClick Success");
        Log.i("ListView", "You clicked Item np : " + id + " at position:" + position);

        Intent intent = new Intent();
        orderIndex = position;
        System.out.println("clicked");
        intent.setClass(mContext, OrderDetailActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.homemenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent MainIntent = new Intent(OrderListActivity.this,MainActivity.class);
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