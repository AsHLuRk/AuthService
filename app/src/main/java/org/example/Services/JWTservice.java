package org.example.Services;

import java.security.Key;
import java.util.Date;
import java.util.Base64.Decoder;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class JWTservice {

    public static final String SECRET = "47382R348989F848RU8R2389R28UGHGJF903R84R823HSDHDHF83R83";

    public String extractUsername(String token){

        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiryDate(String token){

        return extractClaim(token , Claims::getExpiration);
    }
    public boolean istokenexpired(String token){
        return extractExpiryDate(token).before(new Date());
    }

    public boolean ValidateToken(String token, UserDetails userdetails){
        String user = extractUsername(token);
        return (user.equals(userdetails.getUsername())) && !istokenexpired(token);
    }

    public String createToken(Map<String, Object> claims, String username){
      return Jwts.builder()
           .setClaims(claims)
           .setSubject(username)
           .setIssuedAt(new Date(System.currentTimeMillis()))
           .setExpiration(new Date(System.currentTimeMillis()+1000*60*1))
           .signWith(getSignkey(), SignatureAlgorithm.HS256).compact();
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
