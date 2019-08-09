package com.chy.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chy.bean.fill_gps_rucheng;
import com.chy.dao.fill_gps_ruchengMapper;
import com.chy.util.GPSUtil;
import com.chy.util.utiltools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public class LongiandlatiService {

    @Autowired
    com.chy.dao.fill_gps_ruchengMapper fill_gps_ruchengMapper;
    public Object  selectLongiandlatiService(String timedate,String platenumdate){
        utiltools utiltools = new utiltools();
        String time = null;
        try {
            time = utiltools.DatechangeTable(timedate);
        } catch (ParseException e) {
            System.out.println("时间转换错误");
            e.printStackTrace();
        }

        List<fill_gps_rucheng>  fill_gps_rucheng =fill_gps_ruchengMapper.selectlongandlati(time, "'"+platenumdate+"'");
        JSONArray jsonArr = new JSONArray();
        JSONObject jsonObj = new JSONObject();
        for (fill_gps_rucheng  i:fill_gps_rucheng) {
            JSONObject tjo = new JSONObject();
            double[] doubles = GPSUtil.gps84_To_Gcj02(i.getLatitude(), i.getLongitude());
            tjo.put("lat",doubles[0]);
            tjo.put("lon",doubles[1]);
            jsonArr.add(tjo);
        }
        jsonObj.put("list",jsonArr);

        return jsonObj;
    }
}
