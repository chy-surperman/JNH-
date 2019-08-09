package com.chy.controller;


import com.chy.bean.Message;
import com.chy.bean.fill_gps_rucheng;
import com.chy.service.plateNumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class plateNumController {

     @Autowired
     com.chy.service.plateNumService plateNumService;

    @ResponseBody
    @RequestMapping("/plateNum")
    public Message plateNum(String value){
        List<fill_gps_rucheng> fill_gps_ruchengs =plateNumService.plateNumService(value);
        return Message.success().add("list",fill_gps_ruchengs);
    }

}
