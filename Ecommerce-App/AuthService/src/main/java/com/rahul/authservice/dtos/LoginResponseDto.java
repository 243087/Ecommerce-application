package com.rahul.authservice.dtos;

import com.rahul.authservice.models.Token;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
    private String  tokenValue;
}
