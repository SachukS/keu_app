package com.sachuk.keu.services.security;

import com.sachuk.keu.database.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {


    public UserDetailServiceImpl() {
//        log.trace("UserDetailServiceImpl is created");
    }

//    @Autowired
//    private UserService userService;
//    private Logger log = Logger.getLogger(UserDetailServiceImpl.class);


    @Override
    public UserDetails loadUserByUsername(String login) {
//        log.trace("loadUserByUsername:" + login);
//        log.trace("*******************************");
//        User user = userService.getByEmail(login);
//        if (user == null) {
//            throw new UsernameNotFoundException(login);
//        }
//        log.trace("UserDetailService:" + user);
//        log.trace("Password:" + user.getPassword());
//
//        List<GrantedAuthority> roles = userService.getAuthorities(user.getRole());
//        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), roles);
//        log.trace("Pass:" + userDetails.getPassword());
//        log.trace(user.getRole());
//        log.trace(user.getVerification());
//
//        return userDetails;
        return null;
    }


}
