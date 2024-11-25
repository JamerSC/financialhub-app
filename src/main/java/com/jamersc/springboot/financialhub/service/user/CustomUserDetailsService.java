package com.jamersc.springboot.financialhub.service.user;

import com.jamersc.springboot.financialhub.model.Role;
import com.jamersc.springboot.financialhub.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //logger.info("Attempting to load user by username: {}", username);

        User user = userService.getByUsername(username);
        if (user == null) {
            //logger.error("User not found with username: {}", username);
            throw new UsernameNotFoundException("Invalid username or password");
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        //logger.info("User roles: {}", grantedAuthorities);

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                grantedAuthorities
        );
    }
}
