/*
 * Copyright © 2014 Marko Filipović sifilis@kset.org
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2, as published by Sam Hocevar
 */

package hr.kodbiro.quickbyte.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by marko on 26.8.2014..
 *
 * List item model, annotated with Gson annotations for parsing Json gotten from network query response
 *
 */
public class Food {

    @Expose
    @SerializedName("id")
    private Integer foodID;

    @Expose
    @SerializedName("name")
    private String foodName;

    @Expose
    @SerializedName("image")
    private String foodImageUrl;



    //Constructors
    public Food(String foodID, String foodName, String foodImageUrl) {
        this.foodID = Integer.parseInt(foodID);
        this.foodName = foodName;
        this.foodImageUrl = foodImageUrl;
    }

    public Food(String[] foodData){
        this.foodID = Integer.parseInt(foodData[0]);
        this.foodName = foodData[1];
        this.foodImageUrl = foodData[2];
    }


    public Food(){

    }

    //Getters and setters
    public Integer getFoodID() {
        return foodID;
    }

    public void setFoodID(Integer foodID) {
        this.foodID = foodID;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodImageUrl() {
        return foodImageUrl;
    }

    public void setFoodImageUrl(String foodImageUrl) {
        this.foodImageUrl = foodImageUrl;
    }

}
