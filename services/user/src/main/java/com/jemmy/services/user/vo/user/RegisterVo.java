package com.jemmy.services.user.vo.user;

import lombok.Data;

import java.util.Date;

@Data
public class RegisterVo {
    private Long id;
    private String yibao_userid="";
    private String active="1";
    private String user_nature="1";
    private String package_pool="0.000";
    private String username;
    private String phone;
    private Integer usertype;
    private Integer total_can_shua_num=0;
    private Integer source;
    private String register_province;
    private String register_city;
    private String salt;
    private String pass;
    private Date updatedAt;
    private Date createdAt;
}
