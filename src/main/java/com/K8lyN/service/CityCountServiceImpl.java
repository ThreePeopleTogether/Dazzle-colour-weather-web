package com.K8lyN.service;

import com.K8lyN.config.CityCountConfig;
import com.K8lyN.dao.CityCountMapper;
import com.K8lyN.model.CityCount;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author K8lyN
 * @Date 2021/04/09 9:47
 * @Version 1.0
 */
@Component
public class CityCountServiceImpl implements CityCountService{

    private ApplicationContext context = new ClassPathXmlApplicationContext("application-config.xml");
    private CityCountMapper mapper = context.getBean("cityCountMapper", CityCountMapper.class);

    @Override
    public boolean insert(CityCount cc) {
        if(mapper.insertCity(cc) == 1) {
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean delete(String cityName) {
        if(mapper.deleteCity(cityName) == 1) {
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean update(CityCount cc) {
        if(mapper.updateCity(cc) == 1) {
            return true;
        }else {
            return false;
        }
    }

    @Override
    public CityCount select(String cityName) {
        return mapper.selectCity(cityName);
    }

    @Override
    public List<CityCount> getCities() {
        return mapper.getCities();
    }

    @Override
    public int getNum() {
        return mapper.getNum();
    }

    @Override
    public List<CityCount> getMax(int num) {
        return mapper.getMax(num);
    }


}
