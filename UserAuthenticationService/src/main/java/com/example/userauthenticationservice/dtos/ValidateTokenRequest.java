package com.example.userauthenticationservice.dtos;

import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public class ValidateTokenRequest {
    private Long userId;
    private String token;
}
