/*
 * Copyright © 2014 Marko Filipović sifilis@kset.org
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2, as published by Sam Hocevar
 */

package hr.kodbiro.quickbyte.activities;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.kodbiro.quickbyte.adapters.TrolleyListAdapter;
import hr.kodbiro.quickbyte.models.Order;
import hr.kodbiro.quickbyte.network.QuickAsyncHttpClient;

/**
 * Created by marko on 26.8.2014..
 *
 * Trolley Activity
 * shows items in trolley and waits for order confirmation before sending it
 *
 */
public class TrolleyActivity extends ListActivity {

    private List<Order> orderList;
    AlertDialog.Builder alertDialogBuilder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get Intent from MenuActivity with orderList
        Intent intent = getIntent();
        orderList = intent.getParcelableArrayListExtra("orderList");
        TrolleyListAdapter trolleyListAdapter = new TrolleyListAdapter(getApplicationContext(), orderList);

        // add list header
        View header = getLayoutInflater().inflate(R.layout.listview_header_trolley, null);
        header.setClickable(false);
        ListView listView = getListView();
        listView.addHeaderView(header);

        // populate ListView
        getListView().setAdapter(trolleyListAdapter);

        alertDialogBuilder = new AlertDialog.Builder(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.trolley_menu, menu);
        return true;
    }

    // handle ActionBar menu selections
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.confirm_order:
                Map<String, Integer> confirmMap;

                List<Map<String, Integer>> confirmList = new ArrayList<Map<String, Integer>>();

                // get each item's ID, parse to Json and send request (order)
                for (Order order : orderList){
                    for (int i = 0; i < order.getOrderQuantity(); i++) {
                        confirmMap = new HashMap<String, Integer>();
                        confirmMap.put("id", order.getOrderID());
                        confirmList.add(confirmMap);
                    }
                }
                Gson gson = new Gson();
                sendOrder(gson.toJson(confirmList));
                break;
            case R.id.clear_trolley:
                orderList.clear();
                finishActivityWithList();
            default:
                break;
        }
        return true;
    }

    // make AsyncHttpClient request with item ID's
    private void sendOrder(String order) {
        RequestParams params = new RequestParams();
        params.put("application/json", order);
        QuickAsyncHttpClient.post("order", params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String sFinal = response.getString("status") + response.getString("wait_time");


                    alertDialogBuilder.setMessage(String.format("%s %s", getString(R.string.dialog_order_status_success_textview), response.getString("wait_time"))).setPositiveButton(R.string.dialog_positive_button, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            orderList.clear();
                            finishActivityWithList();
                        }
                    });
                    AlertDialog dialog = alertDialogBuilder.create();
                    dialog.show();

                    Log.i("json response", sFinal);
                }catch(JSONException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                alertDialogBuilder.setMessage(getString(R.string.dialog_order_status_failure_textview)).setPositiveButton(R.string.dialog_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = alertDialogBuilder.create();
                dialog.show();
            }
        });
    }

    private void finishActivityWithList()
    {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra("orderList", (ArrayList<? extends android.os.Parcelable>) orderList);
        setResult(1, intent);
        finish();
    }

    // on Back pressed, return with still populated list
    @Override
    public void onBackPressed() {
        finishActivityWithList();
    }
}
