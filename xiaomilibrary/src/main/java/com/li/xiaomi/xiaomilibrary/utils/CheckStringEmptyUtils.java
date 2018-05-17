package com.li.xiaomi.xiaomilibrary.utils;

/**
 * 类描述：检查字符串工具类
 * 作  者：Admin or 李小米
 * 时  间：2017/10/10
 * 修改备注：
 */
public class CheckStringEmptyUtils {

    /**
     * 检查字符串是不是空的
     *
     * @param str
     * @return
     */
    public static boolean IsEmpty(String str) {
        return str == null || str.equals("") ? true : false;
    }

    /**
     * 检查两个字符串是否相同
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean IsEqual(String str1, String str2) {
        if (str1 == null || str2 == null) {
            return false;
        } else {
            return str1.equals(str2) ? true : false;
        }
    }
}
