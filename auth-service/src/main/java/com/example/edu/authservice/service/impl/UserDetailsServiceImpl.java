package com.example.edu.authservice.service.impl;

import com.example.edu.authservice.dto.UserDetailsDto;
import com.example.edu.authservice.entity.UserEntity;
import com.example.edu.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetailsDto dto = new UserDetailsDto();
        UserEntity entity = userRepository.findByAccount(username).orElseThrow(() -> new UsernameNotFoundException(username));
        dto.setUserEntity(entity);
        return dto;
    }
}
