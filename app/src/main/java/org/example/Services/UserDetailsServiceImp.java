package org.example.Services;

import java.util.Objects;
import java.util.UUID;
import java.util.HashSet;
import org.example.Entities.Userinfo;
import org.example.models.UserInfoDTO;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Component
@Data

public class UserDetailsServiceImp implements UserDetailsService{




    @Autowired
    private UserRepository userrepository;

    @Autowired 
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

      Userinfo user = userrepository.findByUsername(username);

      if(user==null){
        throw new UsernameNotFoundException("User not found!!!..");
      }
     return new CustomUserDetails(user);
    
    }

     
   public Userinfo checkIfUserAlreadyExist(UserInfoDTO userinfodto){

    return userrepository.findByUsername(userinfodto.getUsername());
   }

   public boolean signupUser(UserInfoDTO userinfodto){

    userinfodto.setPassword(passwordEncoder.encode(userinfodto.getPassword()));
    if(Objects.nonNull(checkIfUserAlreadyExist(userinfodto))){
        return false;
    }
    Userinfo userinfo = new Userinfo();
    userinfo.setUsername(userinfodto.getUsername());
    userinfo.setPassword(userinfodto.getPassword());
    userinfo.setRoles(new HashSet<>());
    userrepository.save(userinfo);
    return true;

   }
    
}
