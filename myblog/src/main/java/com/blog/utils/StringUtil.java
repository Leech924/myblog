package com.blog.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 操作字符串的工具类
 *
 */
public class StringUtil {
    /**在字符串前后加%用于模糊查询*/
    public static String formatLike(String str){
        if (isNotEmpty(str)){
            return "%"+str+"%";
        }else {
            return null;
        }
    }

    public static Boolean isNotEmpty(String str){
        if (str!=null&&  !"".equals(str.trim())){
            return true;
        }else {
            return false;
        }

    }
    /**判断字符串是否为空*/
    public static Boolean isEmpty(String str){
        if(str==null || "".equals(str.trim())){
            return true;
        }else {
            return false;
        }
    }
    /**
     *
     */
    public static List<String> filterWhite(List<String> list){
        ArrayList<String> resultList = new ArrayList<>();
        for (String str:list){
            if (!isEmpty(str)){
                resultList.add(str);
            }
        }
        return resultList;
    }
}
