/*
 * Copyright © 2014 Marko Filipović sifilis@kset.org
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2, as published by Sam Hocevar
 */

package hr.kodbiro.quickbyte.adapters;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import hr.kodbiro.quickbyte.activities.R;
import hr.kodbiro.quickbyte.models.Order;

/**
 * Created by marko on 27.8.2014..
 */
public class TrolleyListAdapter extends BaseAdapter{

    private List<Order> orderList;
    private Context context;

    private LayoutInflater layoutInflater = null;

    public TrolleyListAdapter(Context context, List<Order> orderList){
        this.context = context;
        this.orderList = orderList;
    }
    public static class ViewHolder {
        public TextView orderID;
        public TextView orderName;
        public TextView orderQuantity;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;
        layoutInflater = (LayoutInflater) context.getSystemService(Application.LAYOUT_INFLATER_SERVICE);

        if (view == null){
            view = layoutInflater.inflate(R.layout.layout_order_list_item, null);
            holder = new ViewHolder();
            holder.orderID = (TextView) view.findViewById(R.id.orderIDTextView);
            holder.orderName = (TextView) view.findViewById(R.id.orderNameTextView);
            holder.orderQuantity = (TextView) view.findViewById(R.id.orderQuantityTextView);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        Order order = orderList.get(i);
        String sOrderID = order.getOrderID().toString();
        String sOrderName = order.getOrderName();
        String sOrderQuantity = order.getOrderQuantity().toString();
        holder.orderID.setText(sOrderID);
        holder.orderName.setText(sOrderName);
        holder.orderQuantity.setText(sOrderQuantity);

        return view;
    }

    @Override
    public int getCount() {
        if (orderList.size() <= 0)
            return 1;
        return orderList.size();
    }

    @Override
    public Object getItem(int i) {
        return orderList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


}
