package com.example.edu.authservice.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsService {
    UserDetails loadUserByAccount(String account);
}
