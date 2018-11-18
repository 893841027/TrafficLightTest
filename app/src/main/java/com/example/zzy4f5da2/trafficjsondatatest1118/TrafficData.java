package com.example.zzy4f5da2.trafficjsondatatest1118;

import org.json.JSONObject;

public interface TrafficData {
    abstract JSONObject getData(String url) throws Exception;
}
