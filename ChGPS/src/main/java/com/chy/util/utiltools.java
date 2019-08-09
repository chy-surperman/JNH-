package com.chy.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class utiltools {

    public String  DatechangeTable(String time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Date parse = simpleDateFormat.parse(time);

        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("MM_dd");
        String format = simpleDateFormat1.format(parse);
        String table="fill_gps_rucheng"+format;

        return table;
    }
}
