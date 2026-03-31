package org.example.Services;

import org.example.Entities.Userinfo;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

      Userinfo user = userrepository.findByUsername(username);

      if(user==null){
        throw new UsernameNotFoundException("User not found!!!..");
      }
     return new CustomUserDetails(user);
    
    }
    
}
