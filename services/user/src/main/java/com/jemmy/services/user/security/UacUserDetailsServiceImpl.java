package com.jemmy.services.user.security;

import com.jemmy.common.security.core.SecurityUser;
import com.jemmy.services.user.model.domain.UacUser;
import com.jemmy.services.user.service.UacUserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * The class Uac user details service.
 *
 * @author paascloud.net @gmail.com
 */
@Component
public class UacUserDetailsServiceImpl implements UserDetailsService {

	@Resource
	private UacUserService uacUserService;

	/**
	 * Load user by username user details.
	 *
	 * @param username the username
	 *
	 * @return the user details
	 */
	@Override
	public UserDetails loadUserByUsername(String username) {
		Collection<GrantedAuthority> grantedAuthorities;
		UacUser user = uacUserService.findByLoginName(username);
		if (user == null) {
			throw new UsernameNotFoundException("用户名不存在或者密码错误");
		}
		user = uacUserService.findUserInfoByUserId(user.getId());
		grantedAuthorities = uacUserService.loadUserAuthorities(user.getId());
		return new SecurityUser(user.getId(), user.getLoginName(), user.getLoginPwd(),
				user.getUserName(), user.getGroupId(), user.getGroupName(), user.getStatus(), grantedAuthorities);
	}
}
