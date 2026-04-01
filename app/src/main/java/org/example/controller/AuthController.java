package org.example.controller;

import java.util.Collections;

import org.example.Entities.RefreshToken;
import org.example.models.UserInfoDTO;
import org.example.response.JWTResponseDTO;
import org.example.Services.JWTservice;
import org.example.Services.RefreshTokenService;
import org.example.Services.UserDetailsServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class AuthController {
    @Autowired
    private JWTservice jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private UserDetailsServiceImp userDetailsService;

    @PostMapping("auth/v1/signup")
    public ResponseEntity<Object> SignUp(@RequestBody UserInfoDTO userInfoDto){
        try{
            Boolean isSignUped = userDetailsService.signupUser(userInfoDto);
            if(Boolean.FALSE.equals(isSignUped)){
                return new ResponseEntity<>("Already Exist", HttpStatus.BAD_REQUEST);
            }
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userInfoDto.getUsername());
            String jwtToken = jwtService.createToken(Collections.emptyMap(), userInfoDto.getUsername());
            return new ResponseEntity<>(JWTResponseDTO.builder()
                    .accessToken(jwtToken)
                    .token(refreshToken.getToken())
                    .build(), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>("Exception in User Service", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
}
