package com.chy.controller;

import com.chy.service.speedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class speedController {

    @Autowired
    com.chy.service.speedService speedService;

    @RequestMapping("/speed")
    public void test(String time,String plateNum) {
        System.out.println(time+"===="+plateNum);
        speedService.selectSpeed();


    }
}
