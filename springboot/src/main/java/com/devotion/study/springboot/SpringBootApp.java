package com.devotion.study.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wugy on 2017/6/20 20:29
 */
@Controller
@EnableAutoConfiguration
public class SpringBootApp {

    @RequestMapping("/")
    @ResponseBody
    String index() {
        return "Hello world!";
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApp.class, args);
    }
}
