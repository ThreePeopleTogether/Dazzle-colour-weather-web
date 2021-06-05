package com.K8lyN.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @Author K8lyN
 * @Date 2021/04/13 12:28
 * @Version 1.0
 */
public class Weather {

    private static final char START = '0';

    public static JSONObject dealWithPubTime(JSONObject json) {
        return dealJson(json, "pubTime", 11);
    }

    public static JSONObject dealWithOBSTime(JSONObject json) {
        return dealJson(json, "obsTime", 11);
    }

    private static JSONObject dealJson(JSONObject json, String key, int start) {
        String date = json.getString(key);
        if (date.charAt(start) == START) {
            date = date.substring(start + 1, start + 5);
        } else {
            date = date.substring(start, start + 5);
        }
        json.remove(key);
        json.put(key, date);
        json.put(Status.CODE, Status.SUCCESS);
        return json;
    }

    public static JSONArray dealWithDate(JSONArray array) {
        JSONArray jsonArray = dealArray(array, "date", 5);
        List<JSONObject> list = new ArrayList<>();
        for(int i = 0;i < jsonArray.length();i++) {
            list.add(jsonArray.getJSONObject(i));
        }
        Collections.sort(list, Comparator.comparing(obj -> {
            try {
                Integer type = obj.getInt("type");
                return type;
            }catch(JSONException e) {
                return -1;
            }
        }));
        jsonArray.clear();
        for (JSONObject jsonObject : list) {
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }

    public static JSONArray dealWithfxDate(JSONArray array) {
        return dealArray(array, "fxDate", 5);
    }

    public static JSONArray dealWithfxTime(JSONArray array) {
        return dealArray(array, "fxTime", 11);
    }

    private static JSONArray dealArray(JSONArray array, String key,int start) {
        for(int i = 0;i < array.length();i++) {
            try {
                JSONObject json = array.getJSONObject(i);
                dealJson(json, key, start);
            }catch(JSONException e) {
                continue;
            }
        }
        JSONObject json = new JSONObject();
        json.put(Status.CODE, Status.SUCCESS);
        array.put(json);
        return array;
    }
}
