package com.blog.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

public class CryptographyUtil {
//    md5加密
    public static String md5(String str,String salt){
        return new Md5Hash(str, salt).toString();
    }

    public static void main(String[] args) {
        System.out.println(CryptographyUtil.md5("1", "ych"));
    }
}
