package com.K8lyN.config;

import com.K8lyN.service.CityCountService;
import com.K8lyN.service.CityCountServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author K8lyN
 * @Date 2021/04/09 9:50
 * @Version 1.0
 */

@Configuration
public class CityCountConfig {

    @Bean
    public CityCountService cityCountService() {
        return new CityCountServiceImpl();
    }

}
