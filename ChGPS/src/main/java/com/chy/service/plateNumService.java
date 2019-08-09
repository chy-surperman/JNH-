package com.chy.service;

import com.chy.bean.fill_gps_rucheng;
import com.chy.dao.fill_gps_ruchengMapper;
import com.chy.util.utiltools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;


@Service
public class plateNumService {

    @Autowired
    com.chy.dao.fill_gps_ruchengMapper fill_gps_ruchengMapper;

    public  List<fill_gps_rucheng>  plateNumService(String time){
        utiltools utiltools = new utiltools();
        String table = null;
        try {
            table = utiltools.DatechangeTable(time);
        } catch (ParseException e) {
            System.out.println("时间转换异常！！！");
            e.printStackTrace();
        }
        List<fill_gps_rucheng> fill_gps_ruchengs = fill_gps_ruchengMapper.selectplateNum(table);
        return fill_gps_ruchengs;
    }


}
