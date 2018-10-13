package com.jemmy.common.web.util;


/**
 * Created by Jemmy on 2017-09-13.
 */
public class RegexUtil {

    /**
     * 手机匹配正则表达式
     */
    public static final String REG_MOBILE="^(\\+86)?((13[0-9])|(14[5,7,9])|(15[^4,\\D])|(17[0,1,3,5-8])|(18[0-9]))\\d{8}$";
    //Email地址
    public static final String REG_EMAIL="^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    //域名
    public static final String REG_DOMAIN="[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(/.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+/.?";

    //html标签
    public static final String REG_HTML_TAG="<(.*)>.*<\\/\\1>|<(.*) \\/>";
}
