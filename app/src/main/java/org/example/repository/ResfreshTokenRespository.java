package org.example.repository;

import org.springframework.stereotype.Repository;
import org.example.Entities.RefreshToken;
import org.springframework.data.repository.CrudRepository;

@Repository
public class ResfreshTokenRespository implements CrudRepository<RefreshToken, Integer>{
	
}
