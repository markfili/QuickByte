/*
 * Copyright © 2014 Marko Filipović sifilis@kset.org
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2, as published by Sam Hocevar
 */

package hr.kodbiro.quickbyte.activities;

import android.app.DialogFragment;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.message.BasicNameValuePair;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import hr.kodbiro.quickbyte.usables.DialogCreator;
import hr.kodbiro.quickbyte.models.Food;
import hr.kodbiro.quickbyte.models.Order;
import hr.kodbiro.quickbyte.adapters.MenuListAdapter;
import hr.kodbiro.quickbyte.network.NetworkCheck;
import hr.kodbiro.quickbyte.network.NetworkOps;

/**
 * MenuActivity
 * main activity for listing available items fetched from network query
 */

public class MenuActivity extends ListActivity {

    private List<Food> listViewObjectList;
    private ListView listViewLayout;
    private List<Order> trolleyList;

    Integer chosenItemPosition;
    DialogFragment newFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_menu_activity);

        trolleyList = new ArrayList<Order>();

        listViewLayout = (ListView) findViewById(android.R.id.list);

        // add list header
        View header = getLayoutInflater().inflate(R.layout.listview_header_menu, null);
        header.setClickable(false);
        ListView listView = getListView();
        listView.addHeaderView(header);

        // get url value and initiate list loading in AsyncTask class
        String urlString = getString(R.string.url_get_menu);
        new FillMenu().execute(urlString);
    }

    // get list after TrolleyActivity shuts down
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        trolleyList = data.getExtras().getParcelableArrayList("orderList");
    }

    // when item clicked, get item ID
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        chosenItemPosition = position;
        showDialog(android.R.id.list, R.layout.dialog_quantity, getString(R.string.dialog_order_quantity));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        switch (id) {
            case R.id.open_trolley:
                showTrolleyActivity();
                return true;
            case R.id.clear_trolley:
                showDialog(R.id.clear_trolley, R.layout.dialog_clear_trolley, getString(R.string.dialog_clear_trolley));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // AsyncTask class for getting data from network and populating listView
    private class FillMenu extends AsyncTask<String, String, String> {

        // before proceeding, check network status, cancel AsyncTask if no connection found
        @Override
        protected void onPreExecute() {
            String networkStatusString;
            NetworkCheck networkCheck = new NetworkCheck();
            if (networkCheck.getConnectionStatus(getApplicationContext())) {
                networkStatusString = getString(R.string.connected_to_internet);
                makeToast(networkStatusString);
            } else {
                networkStatusString = getString(R.string.not_connected);
                makeToast(networkStatusString);
                cancel(true);
            }

        }

        // get JSON from URL, parse it to a List
        @Override
        protected String doInBackground(String... strings) {

            if (!isCancelled()) {

                // get JSON string from HTTP response
                NetworkOps ops = new NetworkOps();
                BasicNameValuePair responsePair = ops.getRequest(getString(R.string.url_get_menu));

                // check ResponseCode to continue parsing response
                switch (Integer.parseInt(responsePair.getName())) {
                    case 200:
                        try {

                            String jsonString = responsePair.getValue();

                            Type listType = new TypeToken<List<Food>>() {}.getType();

                            Gson gson = new Gson();
                            listViewObjectList = gson.fromJson(jsonString, listType);

                        } catch (Exception e){

                            e.printStackTrace();
                            Log.e("JSON Exception", "No JSON string");
                            makeToast("No menu available");
                            return null;

                        }
                        return "Success";
                    default:
                        makeToast("Error fetching menu list");
                        Log.e("Server response error", String.format("Response code: %s, reason: %s", responsePair.getName(), responsePair.getValue()));
                }
            }
            return null;
        }

        // set Adapter to load ListView with List from network
        @Override
        protected void onPostExecute(String result) {
            listViewLayout.setAdapter(new MenuListAdapter(getApplicationContext(), listViewObjectList));
        }

        // if cancelled, report
        @Override
        protected void onCancelled(String result) {
            makeToast("Cancelled all operations due to error");
        }
    }

    private void makeToast(String toastMessage) {
        Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
    }

    // call TrolleyActivity, do nothing if trolley is empty
    private void showTrolleyActivity() {
        if(trolleyList.size()!= 0){
            Intent intent = new Intent(getApplicationContext(), TrolleyActivity.class);
            intent.putParcelableArrayListExtra("orderList", (ArrayList<? extends android.os.Parcelable>) trolleyList);
            startActivityForResult(intent, 1);
        }else{
            makeToast("No items in trolley!");
        }

    }

    private void clearTrolley() {
        trolleyList.clear();
    }

    private void showDialog(int callerID, int layoutID, String message) {

        newFragment = DialogCreator.newInstance(callerID, layoutID, message);
        newFragment.show(getFragmentManager(), "dialog");


    }
    // handle dialog button click
    public void doPositiveClick(int callerId, Integer quantity, String quantityText){

        switch(callerId){
            case R.id.clear_trolley:
                Log.i("pressed","clear trolley");
                clearTrolley();
                break;
            case android.R.id.list:

                Log.i("pressed", "list item " + chosenItemPosition + " " + quantity);

                if (quantity == 0 || quantityText == "0"){break;}

                // get chosenItem object
                Food food = listViewObjectList.get(chosenItemPosition);
                trolleyList.add(new Order(food.getFoodID(), food.getFoodName(), quantity));
                break;
            default:
        }
    }

    public void doNegativeClick(int callerId){

    }
}
