package org.example.Services;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.example.Entities.RefreshToken;
import org.example.Entities.Userinfo;
import org.example.repository.ResfreshTokenRespository;
import org.example.repository.UserRepository;

import java.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.Entity;

@Service
public class RefreshTokenService {
    
    @Autowired ResfreshTokenRespository refreshtokenrepository;



    @Autowired UserRepository userrepository;

    public RefreshToken createRefreshToken(String username){

        Userinfo userInfoExtracted = userrepository.findByUsername(username);

        RefreshToken newRefreshtoken = RefreshToken.builder()
           .userInfo(userInfoExtracted)
           .token(UUID.randomUUID().toString())
           .expiryDate(Instant.now().plusMillis(600000))
           .build();
        
        return refreshtokenrepository.save(newRefreshtoken);
    }
}
