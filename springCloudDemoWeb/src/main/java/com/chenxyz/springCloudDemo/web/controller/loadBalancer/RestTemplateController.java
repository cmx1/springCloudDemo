package com.chenxyz.springCloudDemo.web.controller.loadBalancer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by chenxyz on 2018/6/10.
 */
@RestController
@RequestMapping("/loadBalancer/restTemplate")
@Configuration
public class RestTemplateController {


    @Bean
    @Qualifier("lbRestTemplate")
    @LoadBalanced
    RestTemplate RestTemplate() {
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setReadTimeout(2000);
        simpleClientHttpRequestFactory.setConnectTimeout(2000);
        return new RestTemplate(simpleClientHttpRequestFactory);
    }

    @Autowired
    @Qualifier("lbRestTemplate")
    RestTemplate lbRestTemplate;

    @RequestMapping("/eureka/querySMS")
    public String eurekas() {
        return lbRestTemplate.getForObject("http://sms-service/sms", String.class);
    }

    @RequestMapping("/annotation/querySMS")
    public String annotation() {
        return lbRestTemplate.getForObject("http://annotation-sms-service/sms", String.class);
    }

    @RequestMapping("/properties/querySMS")
    public String properties() {
        return lbRestTemplate.getForObject("http://properties-sms-service/sms", String.class);
    }

}
