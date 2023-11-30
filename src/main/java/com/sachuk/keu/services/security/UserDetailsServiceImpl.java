package com.sachuk.keu.services.security;

import com.sachuk.keu.database.repositories.UserRepository;
import com.sachuk.keu.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String ipn) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(ipn)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with ipn: " + ipn));

        return UserDetailsImpl.build(user);
    }


}
