package com.abhishek.sweet_kata_shop.security;

import com.abhishek.sweet_kata_shop.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository users;

    public UserDetailsServiceImpl(UserRepository users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        var user = users.findByUsernameIgnoreCase(usernameOrEmail)
                .or(() -> users.findByEmailIgnoreCase(usernameOrEmail))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .accountLocked(!user.getEnabled())
                .disabled(!user.getEnabled())
                .build();
    }
}