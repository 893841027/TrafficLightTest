package com.example.zzy4f5da2.trafficjsondatatest1118;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataManager implements TrafficData{

    @Override
    public JSONObject getData(final String url) throws JSONException, IOException {
        final StringBuffer sb = new StringBuffer();
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setConnectTimeout(3000);
        InputStream is = connection.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader buf = new BufferedReader(isr);
        String line = "";
        while( (line = buf.readLine()) != null ){
               sb.append(line);
        }
        return new JSONObject(sb.toString());
    }

}
