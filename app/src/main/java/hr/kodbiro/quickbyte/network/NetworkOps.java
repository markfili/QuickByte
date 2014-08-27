/*
 * Copyright © 2014 Marko Filipović sifilis@kset.org
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2, as published by Sam Hocevar
 */

package hr.kodbiro.quickbyte.network;

import android.net.http.AndroidHttpClient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by marko on 25.8.2014.
 * Make network requests and manage responses using AndroidHttpClient
 */
public class NetworkOps {
    private AndroidHttpClient httpClient;
    private HttpResponse response;
    private BasicNameValuePair results;

    private AndroidHttpClient setUpHttpClient() {

        httpClient = AndroidHttpClient.newInstance(null);
        return httpClient;
    }

    private void closeHttpClient() {
        httpClient.close();
    }

    private BasicNameValuePair checkResponse(HttpResponse response) throws IOException {
        HttpEntity httpEntity = response.getEntity();
        BasicNameValuePair resultText;
        int statusCode = response.getStatusLine().getStatusCode();
        // if something's wrong, return responseCode and reason
        if (statusCode != HttpStatus.SC_OK) {
            resultText = new BasicNameValuePair(String.valueOf(statusCode), response.getStatusLine().getReasonPhrase());
        } else {
            // read response
            BufferedReader rd = new BufferedReader(new InputStreamReader(httpEntity.getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            resultText = new BasicNameValuePair(String.valueOf(response.getStatusLine().getStatusCode()), result.toString());
        }
        return resultText;
    }

    // GET request
    public BasicNameValuePair getRequest(String url) {
        HttpGet request = new HttpGet(url);
        try {

            response = setUpHttpClient().execute(request);
            closeHttpClient();
            results = checkResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return results;

    }
    // POST request
    public BasicNameValuePair postRequest(String url, String jsonData) {
        try {
            HttpPost request = new HttpPost(url);
            request.setHeader("Content-Type", "application/json;charset=UTF-8");
            request.setEntity(new StringEntity(jsonData));
            response = setUpHttpClient().execute(request);
            closeHttpClient();
            results = checkResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return results;
    }
}
    




