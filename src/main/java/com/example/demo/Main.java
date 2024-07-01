package com.example.demo;

import com.example.demo.configuration.MyConfigurationClass;
import com.example.demo.service.ShopServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(MyConfigurationClass.class);
        ShopServiceImpl shopService = context.getBean("shopService", ShopServiceImpl.class);
        shopService.menu();
    }
}
