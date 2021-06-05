package com.K8lyN.controller; /**
 * @Author K8lyN
 * @Date 2021/04/10 9:53
 * @Version 1.0
 */

import com.K8lyN.config.CityCountConfig;
import com.K8lyN.model.CityCount;
import com.K8lyN.service.CityCountService;
import com.K8lyN.utils.HttpClientUtil;
import com.google.gson.Gson;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "GetMax", value = "/GetMax")
public class GetMax extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpClientUtil.initResponse(response);

        PrintWriter out = response.getWriter();
        String num = request.getParameter("cityNum");
        ApplicationContext context = new AnnotationConfigApplicationContext(CityCountConfig.class);
        CityCountService cityCountService = context.getBean("cityCountService", CityCountService.class);
        List<CityCount> list = cityCountService.getMax(Integer.parseInt(num));
        Gson gson = new Gson();
        // 返回json数组
        out.print(gson.toJson(list));
    }
}
