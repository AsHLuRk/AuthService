package org.example.repository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.example.Entities.RefreshToken;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface ResfreshTokenRespository extends  CrudRepository<RefreshToken, Integer>{
    
    //USING JPA
    Optional<RefreshToken> findByToken(String token);



}
