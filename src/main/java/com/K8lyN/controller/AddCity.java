package com.K8lyN.controller; /**
 * @Author K8lyN
 * @Date 2021/04/10 9:43
 * @Version 1.0
 */

import com.K8lyN.config.CityCountConfig;
import com.K8lyN.model.CityCount;
import com.K8lyN.service.CityCountService;
import com.K8lyN.utils.HttpClientUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AddCity", value = "/AddCity")
public class AddCity extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpClientUtil.initResponse(response);

        //PrintWriter out = response.getWriter();
        String cityName = request.getParameter("cityName");
        ApplicationContext context = new AnnotationConfigApplicationContext(CityCountConfig.class);
        CityCountService cityCountService = context.getBean("cityCountService", CityCountService.class);
        CityCount cc = cityCountService.select(cityName);
        if(cc == null) {
            cc = new CityCount();
            cc.setCityName(cityName);
            cc.setCount(1);
            cityCountService.insert(cc);
        }else {
            cc.setCount(cc.getCount() + 1);
            cityCountService.update(cc);
        }
    }
}
