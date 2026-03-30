package org.example.Services;

import java.security.Key;
import java.util.Base64.Decoder;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class JWTservice {

    public static final String SECRET = "47382R348989F848RU8R2389R28UGHGJF903R84R823HSDHDHF83R83";

    public String extractUsername(String token){

        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token , Function<Claims, T> claimResolver){
      final Claims claim = extractAllClaims(token);
      return claimResolver.apply(claim);
    }
    private Claims extractAllClaims(String token){

        return Jwts
               .parser()
               .verifyWith(getSignkey())

               
            //    .setSigningKey(getSignkey()) THIS IS OLD ,deprecated
                
               .build()
               .parseSignedClaims(token)
            //    .parseClaimsJws(token) SAME
               .getPayload();
            //    .getBody();       SAME

    }
    private SecretKey getSignkey(){
        byte[] keybuild = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keybuild);
    }
}
