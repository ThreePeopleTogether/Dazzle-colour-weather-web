package com.K8lyN.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author K8lyN
 * Http 发送请求
 */

public class HttpClientUtil {

    private static final String KEY = "PrivateKey";

    /**
     *
     *
     *
     * */
    public static JSONObject requestByPostMethod(String url, JSONObject param) {
        JSONObject result = new JSONObject();
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        URI uri = null;
        try {
            // 将参数放入键值对类NameValuePair中,再放入集合中
            List<NameValuePair> params = new ArrayList<>();
            Iterator iterator = param.keys();
            while(iterator.hasNext()) {
                String key = (String) iterator.next();
                params.add(new BasicNameValuePair(key, param.getString(key)));
            }
            uri = new URIBuilder(url).setParameters(params).build();
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }
        System.out.println(uri);
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setHeader("Content-Type", "application/json;charset=utf8");
        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            result.put("status", "" + response.getStatusLine().getStatusCode());
            if (responseEntity != null) {
                String relResponse = EntityUtils.toString(responseEntity);
                try{
                    result.put("result", new JSONObject(relResponse));
                }catch(JSONException e) {
                    result.put("result", relResponse);
                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }






    /**
     * 模拟发送url Get 请求
     * @param url
     * @return
     */
    public static String requestByGetMethod(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        StringBuilder entityStringBuilder = null;
        try {
            HttpGet get = new HttpGet(url);
            CloseableHttpResponse httpResponse = null;
            httpResponse = httpClient.execute(get);
            try {
                HttpEntity entity = httpResponse.getEntity();
                entityStringBuilder = new StringBuilder();
                if (null != entity) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"), 8 * 1024);
                    String line = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        entityStringBuilder.append(line + "/n");
                    }
                }
            } finally {
                httpResponse.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return entityStringBuilder.toString();
    }

    /**
     * 获取JSONObject
     * @param URL 请求的链接
     * @param cityID 城市ID
     * */
    public static JSONObject getJSON(String URL, String cityID, String ...args) {
        HttpClientUtil httpClientUtil = new HttpClientUtil();
        String url = URL + "key=" + KEY + "&location=" + cityID;
        for (String arg : args) {
            url = url + "&" + arg;
        }
        String res = null;
        JSONObject json = null;
        try {
            res = httpClientUtil.requestByGetMethod(url).split("/n")[0];
            json = new JSONObject(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    public static boolean initResponse(HttpServletResponse response) {

        try {
            // 中文乱码
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-type","text/html;charset=UTF-8");

            // 跨域访问
            // 允许访问的网站
            response.setHeader("Access-Control-Allow-Origin", "*");
            // 允许访问的方法
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, HEAD, DELETE, PUT");
            // cookie存在时间 正数可固化（关掉浏览器仍在） 负数关掉浏览器消失
            response.setHeader("Access-Control-Max-Age", "3600");
            // 允许访问的设备类型
            response.setHeader("Access-Control-Allow-Headers",
                    "X-Requested-With, Content-Type, Authorization, Accept, Origin, User-Agent, Content-Range, Content-Disposition, Content-Description");
            return true;
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
