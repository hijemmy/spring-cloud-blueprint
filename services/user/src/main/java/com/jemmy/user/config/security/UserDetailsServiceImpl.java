package com.jemmy.user.config.security;

import com.jemmy.user.model.User;
import com.jemmy.user.model.UserPrincipal;
import com.jemmy.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userService.findUserByUserame(username);
        if(null==user){
            throw new UsernameNotFoundException("用户不存在");
        }

        return new UserPrincipal(username,user.getPass(),user.getActive(),user.getId(),user.getSalt());
    }
}
