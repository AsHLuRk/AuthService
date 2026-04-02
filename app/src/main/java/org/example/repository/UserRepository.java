package org.example.repository;

import org.example.Entities.Userinfo;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Userinfo, Long> {

    //jpa
    
    public Userinfo findByUsername(String username);

}
