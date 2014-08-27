/*
 * Copyright © 2014 Marko Filipović sifilis@kset.org
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2, as published by Sam Hocevar
 */

package hr.kodbiro.quickbyte.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by marko on 26.8.2014..
 * Chosen items model, for TrolleyActivity listing
 */
public class Order implements Parcelable{

    @Expose
    @SerializedName("id")
    private Integer orderID;
    private String orderName;
    private Integer orderQuantity;

    public Order(Parcel source) {
        orderID = source.readInt();
        orderName = source.readString();
        orderQuantity = source.readInt();
    }

    public Order(Integer orderID, String orderName, Integer orderQuantity) {
        this.orderID = orderID;
        this.orderName = orderName;
        this.orderQuantity = orderQuantity;
    }


    public Integer getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(Integer orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(orderID);
        dest.writeString(orderName);
        dest.writeInt(orderQuantity);
    }

    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        public Order[] newArray(int size) {
            return new Order[size];
        }
    };


}
