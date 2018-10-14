package com.jemmy.user.model;

import org.springframework.security.core.authority.AuthorityUtils;

public class UserPrincipal extends org.springframework.security.core.userdetails.User {
    private Long id;
    private String username;
    private String salt;

    public UserPrincipal(String username, String password, boolean enabled,Long id, String salt) {
        super(id.toString(), password,enabled,true,true,true,AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER") );
        this.id = id;
        this.username = username;
        this.salt=salt;
    }

    public String getSalt(){
        return salt;
    }

    public Long getId() {
        return id;
    }

    public String getMobile(){
        return username;
    }

}
