///*
// * Copyright © 2014 Marko Filipović sifilis@kset.org
// * This work is free. You can redistribute it and/or modify it under the
// * terms of the Do What The Fuck You Want To Public License, Version 2, as published by Sam Hocevar
// */
//
//package hr.kodbiro.quickbyte.service;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.lang.reflect.Constructor;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import hr.kodbiro.quickbyte.models.Food;
//
///**
// * Created by marko on 25.8.2014..
// * JSONParser klasa, namjera je da bude Generic kolko god može, ali se Androidu to nije svidjelo.
// * Nije potpuna, nedostaje još toga. Ostavio sam ju ovdje kao idejno rješenje, za kasniju doradu.
// * Inače se koristi Gson za parsanje Jsona.
// *
// */
//public class JSONParser<T> {
//
//
//        public List parseJSONString(Class cls, String[] parametersList, String input) throws Exception, JSONException {
//
//            List<Class> jsonObjectsList = new ArrayList<Class>();
//            JSONArray inputJSON = new JSONArray(input);
//
//            for (int i = 0; i < inputJSON.length(); i++) {
//
//                JSONObject jsonObject = inputJSON.getJSONObject(i);
//                String[] modelData = {};
//
//                for (int j = 0; j < parametersList.length; j++) {
//                    modelData[j] += jsonObject.getString(parametersList[j]);
//                }
//                Class cls = new Class(modelData);
//                jsonObjectsList.add(type);
//            }
//
//            return jsonObjectsList;
//        }
//    }
