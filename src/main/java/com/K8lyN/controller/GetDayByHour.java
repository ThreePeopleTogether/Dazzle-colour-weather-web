package com.K8lyN.controller;
/**
 * @Author K8lyN
 * @Date 2021/04/10 15:46
 * @Version 1.0
 */

import com.K8lyN.utils.HttpClientUtil;
import com.K8lyN.utils.Status;
import com.K8lyN.utils.Weather;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "GetDayByHour", value = "/GetDayByHour")
public class GetDayByHour extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpClientUtil.initResponse(response);

        PrintWriter out = response.getWriter();
        String url = "https://devapi.qweather.com/v7/weather/24h?";
        String cityID = request.getParameter("cityID");
        JSONObject json = HttpClientUtil.getJSON(url, cityID);
        if(json.getString(Status.CODE).equals(Status.SUCCESS)) {
            JSONArray array = Weather.dealWithfxTime(json.getJSONArray("hourly"));
            out.print(array);
        }else {
            out.print(json.getString("code"));
        }
    }
}
