/*
 * Copyright © 2014 Marko Filipović sifilis@kset.org
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2, as published by Sam Hocevar
 */

package hr.kodbiro.quickbyte.adapters;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import hr.kodbiro.quickbyte.activities.R;
import hr.kodbiro.quickbyte.models.Food;

/**
 * Created by marko on 25.8.2014.
 * Custom ListAdapter for populating MenuActivity
 */
public class MenuListAdapter extends BaseAdapter {

    private List<Food> itemsList;
    private Context context;

    private static LayoutInflater inflater = null;

    public MenuListAdapter(Context context, List<Food> list) {
        this.context = context;
        this.itemsList = list;
    }

    public static class ViewHolder {

        public TextView idTv;
        public TextView nameTv;
        public ImageView imageIv;

    }

    @Override
    public int getCount() {
        if (itemsList.size() <= 0)
            return 1;
        return itemsList.size();
    }

    @Override
    public Object getItem(int i) {
        return itemsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        inflater = (LayoutInflater) context.getSystemService(Application.LAYOUT_INFLATER_SERVICE);
        // ImageView view = (ImageView) view;
        if (view == null) {
            view = inflater.inflate(R.layout.layout_menu_list_item, null);
            holder = new ViewHolder();
            holder.idTv = (TextView) view.findViewById(R.id.id_view);
            holder.nameTv = (TextView) view.findViewById(R.id.name_view);
            holder.imageIv = (ImageView) view.findViewById(R.id.image_view);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Food food = itemsList.get(i);
        String id = food.getFoodID().toString();
        String name = food.getFoodName();
        String url = food.getFoodImageUrl();
        holder.idTv.setText(id);
        holder.nameTv.setText(name);
        // Log.i("Image text", url);

        Picasso.with(context).load(url).resize(100, 100).into(holder.imageIv, new Callback() {
            @Override
            public void onSuccess() {
                Log.i("Picasso", "success");
            }

            @Override
            public void onError() {
                Log.i("Picasso", "error");
            }
        });

        return view;
    }
}