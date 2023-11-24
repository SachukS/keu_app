package com.sachuk.keu.configurations;

import com.sachuk.keu.services.security.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public UserDetailServiceImpl userDetailService() {
        return new UserDetailServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    private DaoAuthenticationProvider authProvider() {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(userDetailService());
        return authProvider;

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        //TODO : REPLACE AJAX
        httpSecurity.authorizeRequests().antMatchers("/js/**", "/images/**", "/data/**", "/less/**", "/vendor/**", "/fonts/**", "/index/**", "/css/**", "/assets/**").permitAll();

        httpSecurity
                .authorizeRequests()
                .antMatchers("/index*", "/login*", "/register*", "/api/v1/*")
                .anonymous()
//                .antMatchers("/input", "/edit/**").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated().and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/cabinet")
                .failureUrl("/login-error")
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/login");

//        httpSecurity
//                .authorizeRequests()
//                .antMatchers("/logout*").authenticated().and().logout().permitAll().and().csrf().disable();


    }

}