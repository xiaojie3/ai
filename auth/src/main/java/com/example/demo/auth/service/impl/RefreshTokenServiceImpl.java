package com.example.demo.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.auth.mapper.RefreshTokenMapper;
import com.example.demo.auth.model.entity.RefreshToken;
import com.example.demo.auth.service.RefreshTokenService;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenServiceImpl extends ServiceImpl<RefreshTokenMapper, RefreshToken> implements RefreshTokenService {
}
