package com.blog.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    /**得到当前时间为秒的字符串*/
    public static String getCurrentDateStr(){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        return dateFormat.format(date);

    }
    public static String formatDate(Date date,String format){
        String result = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        if (result!=null){
            result= dateFormat.format(date);
        }
        return result;
    }
}
