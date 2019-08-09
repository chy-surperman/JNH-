package com.chy.test;


import com.chy.bean.fill_gps_rucheng;
import com.chy.service.LongiandlatiService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContent.xml"})
public class UniteTest {
    @Autowired
    com.chy.service.plateNumService plateNumService;
    @Autowired
    LongiandlatiService LongiandlatiService;


    @Test
    public  void  test()  {
        List<fill_gps_rucheng> fill_gps_ruchengs = plateNumService.plateNumService("2019年04月01日");
        for (fill_gps_rucheng i:fill_gps_ruchengs) {
            System.out.println(i.getPlatenum());
        }
    }


    @Test
    public void longandti(){
        Object o = LongiandlatiService.selectLongiandlatiService("2019年04月01日", "'湘LC8898'");
        System.out.println(o);
    }

}
