package com.narayan.webservice.util;

import com.narayan.webservice.security.AppConstant;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    public String generateToken(Authentication authentication) {
        List<String> roles= authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        String token = Jwts.builder().signWith(SignatureAlgorithm.HS512, Keys.hmacShaKeyFor(AppConstant.JWT_SECRET.
                                getBytes()))
                .setHeaderParam("type", "JWT")
                .setIssuer("secure-api")
                .setAudience("secure-app")
                .setSubject(authentication.getName())
                //TODO: Narayan - Set expiration time according to remember me
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 60 * 24))
                .claim("role", roles).compact();

        return "Bearer " + token;
    }
}