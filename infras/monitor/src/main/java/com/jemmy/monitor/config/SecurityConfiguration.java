package com.jemmy.monitor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${spring.boot.admin.context-path}")
    private String adminPrefix;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage(adminPrefix+"/login.html").defaultSuccessUrl(adminPrefix+"/",true).loginProcessingUrl(adminPrefix+"/login").permitAll();
        http.logout().logoutUrl(adminPrefix+"/logout").clearAuthentication(true).invalidateHttpSession(true).logoutSuccessHandler(new SimpleUrlLogoutSuccessHandler() {
            @Override
            public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                response.setHeader(HttpHeaders.WWW_AUTHENTICATE,"Basic realm='输入用户名和密码'");
                super.onLogoutSuccess(request, response, authentication);
            }
        });
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, adminPrefix+"/api/applications",adminPrefix+"/login")
                .permitAll()
                .antMatchers(HttpMethod.GET, adminPrefix+"/login.html", adminPrefix+"/**/*.css", adminPrefix+"/img/**", adminPrefix+"/third-party/**")
                .permitAll()
                .antMatchers("/health").permitAll()
                .anyRequest().authenticated()
                .and().csrf().ignoringAntMatchers(adminPrefix+"/api/**",adminPrefix+"/logout",adminPrefix+"/login")
                .csrfTokenRepository(csrfTokenRepository()).and()
                .addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);
        http.httpBasic();
    }

    private Filter csrfHeaderFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain filterChain)
                    throws ServletException, IOException {
                CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
                if (csrf != null) {
                    Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
                    String token = csrf.getToken();
                    if (cookie == null || token != null && !token.equals(cookie.getValue())) {
                        cookie = new Cookie("XSRF-TOKEN", token);
                        cookie.setPath("/");
                        response.addCookie(cookie);
                    }
                }
                filterChain.doFilter(request, response);
            }
        };
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }
}
