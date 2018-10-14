package com.jemmy.common.io.user.user;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class RegisterIO{
    @NotEmpty
    private String username;
    @NotEmpty
    private String password="123456";
}
