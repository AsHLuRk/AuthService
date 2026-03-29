package org.example.Services;

import java.util.Collection;
import java.util.Collections;
import java.util.*;

import org.example.Entities.UserRole;
import org.example.Entities.Userinfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthoritiesContainer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails extends Userinfo implements UserDetails{


    
    private String username;

    private String password;



    public CustomUserDetails(Userinfo byUserinfo){

      this.username = byUserinfo.getUsername();
      this.password = byUserinfo.getPassword();
      List<GrantedAuthority> auths = new ArrayList<>();
      for(UserRole role: byUserinfo.getRoles()){
         auths.add(new SimpleGrantedAuthority(role.getName().toUpperCase()));
      }
      this.authorities = auths;
    }


    Collection<?extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return authorities;
    }
    @Override
    public String getPassword(){
        return password;
    }

	@Override
	public String getUsername(){
        return username;
    }

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
    
    @Override
    public boolean isAccountNonLocked() {
		return true;
	}

    @Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
    @Override
	public boolean isEnabled() {
		return true;
	}

    
    
}
