package com.narayan.webservice.filter;

import com.narayan.webservice.security.AppConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private static final Logger log = Logger.getLogger("JwtAuthorizationFilter");

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        String header = request.getHeader("auth");
        if (header == null || Objects.equals(header, "") || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("auth");
        if (token == null || token.equals("")) {
            return null;
        }
        String JwtSecret = AppConstant.JWT_SECRET;
        Jws<Claims> parsedToken = Jwts.parser()
                .setSigningKey(JwtSecret.getBytes())
                .parseClaimsJws(token.replace("Bearer ", ""));

        String userName = parsedToken.getBody().getSubject();
        List<SimpleGrantedAuthority> authorities = ((List<?>) parsedToken.getBody()
                .get("role")).stream()
                .map(authority -> new SimpleGrantedAuthority((String) authority))
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(userName, null, authorities);
    }


}
