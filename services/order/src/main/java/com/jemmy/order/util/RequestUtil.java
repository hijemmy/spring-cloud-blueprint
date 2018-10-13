package com.jemmy.order.util;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {

    public static String getRequestIp(HttpServletRequest request){
        assert null!=request;
        String result=request.getHeader("x-forwarded-for");
        final String[] ips=result.split(",");
        for (String ip:ips){
            if(!"unknown".equalsIgnoreCase(ip)){
                result=ip;
                break;
            }
        }
        return result;
    }

    /**
     * 请求类型
     * 0:未知
     * 1:普通
     * 2:ajax
     * @param request
     * @return
     */
    public static Integer getRequestType(HttpServletRequest request){
        if(null==request)
            return 0;
        return null==request.getHeader("X-Requested-With")?1:2;
    }


    private RequestUtil() {
    }
}
