package com.RizkiAwantaJordhieJSleepKM.jsleep_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.RizkiAwantaJordhieJSleepKM.jsleep_android.model.Payment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CustomOrderListAdapter extends ArrayAdapter<Payment> {


    public CustomOrderListAdapter(@NonNull Context context, ArrayList<Payment> order) {
        super(context, 0, order);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        View currentItemView = convertView;
        if(currentItemView == null){
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.order_list,parent,false);
        }

        Payment order = getItem(position);

        TextView date = currentItemView.findViewById(R.id.payment_date);
        TextView status = currentItemView.findViewById(R.id.payment_status);

        String dateText = dateFormat.format(order.from) + " - " + dateFormat.format(order.to);
        date.setText(dateText);

        String statusText = "Status: " + order.status;
        status.setText(statusText);


        return currentItemView;
    }


}