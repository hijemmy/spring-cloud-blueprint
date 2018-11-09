package com.jemmy.apis.user.vo.user;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Jemmy
 */
@Data
public class UserInfoVo {

    private Long id;
    private String username;
    private String phone;
    private String nickname;
    private String salt_pay;
    private String pass_pay;
    private BigDecimal packagePool;
}
