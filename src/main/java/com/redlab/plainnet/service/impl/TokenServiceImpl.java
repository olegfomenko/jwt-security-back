package com.redlab.plainnet.service.impl;

import com.redlab.plainnet.entity.Role;
import com.redlab.plainnet.exception.TokenExpiredException;
import com.redlab.plainnet.security.JwtUserDetailsService;
import com.redlab.plainnet.service.RoleService;
import com.redlab.plainnet.service.TokenService;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class TokenServiceImpl implements TokenService {
    @Value("${jwt.token.secret}")
    private String secret;
    @Value("${jwt.token.expired}")
    private long expiredMillis;

    private final JwtUserDetailsService jwtUserDetailsService;
    private final RoleService roleService;

    @Autowired
    public TokenServiceImpl(JwtUserDetailsService jwtUserDetailsService, RoleService roleService) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.roleService = roleService;
    }

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    @Override
    public String createToken(String username, List<Role> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roleService.toStringList(roles));

        Date currentDate = new Date();
        Date validity = new Date(currentDate.getTime() + expiredMillis);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(currentDate)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    @Override
    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if(token != null && token.startsWith("Bearer ")) {
            return token.substring(7, token.length());
        } else {
            return null;
        }
    }

    @Override
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.error(e.getMessage());
            return false;
        }
    }
}
