package com.chy.controller;

import com.chy.bean.Message;
import com.chy.bean.fill_gps_rucheng;
import com.chy.service.LongiandlatiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class LongiandlatiController {

    @Autowired
    com.chy.service.LongiandlatiService LongiandlatiService;

    @ResponseBody
    @RequestMapping("/Longiandlati")
    public Message selectLongiandlatiController(String time, String plateNum) {
        Object longiandlati = LongiandlatiService.selectLongiandlatiService(time, plateNum);
        return  Message.success().add("list",longiandlati);
    }


}
