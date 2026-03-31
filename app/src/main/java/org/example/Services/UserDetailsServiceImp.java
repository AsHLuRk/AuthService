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

import com.netflix.discovery.converters.Auto;

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
    String userid = UUID.randomUUID().toString();
    Userinfo userinfo = new Userinfo(userid, userinfodto.getUsername(), userinfodto.getPassword(), new HashSet<>());
    userrepository.save(userinfo);
    return true;

   }
    
}
