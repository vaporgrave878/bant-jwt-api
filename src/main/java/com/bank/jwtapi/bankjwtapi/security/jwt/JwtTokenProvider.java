package com.bank.jwtapi.bankjwtapi.security.jwt;

import com.bank.jwtapi.bankjwtapi.models.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    @Value("${application.jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.token.expired}")
    private long tokenExpirationAfterDays;

//    @Value("${application.jwt.tokenPrefix}")
//    private String bearer;


    @Autowired
    private UserDetailsService userDetailsService;


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @PostConstruct
//    protected void init(){
//        secret = Base64.getEncoder().encodeToString(secret.getBytes());
//    }

    public String createToken(String username, List<Role> roles) {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", getRoleName(roles));

        Date now = new Date();
        Date validateDate = new Date(now.getTime() + tokenExpirationAfterDays);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validateDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer_"))
            return bearerToken.substring(7, bearerToken.length());//return bearerToken.substring(7, bearerToken.length());
        return null;
    }

    public boolean validateToken(String token) {
//        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);


            if (claims.getBody().getExpiration().before(new Date()))
                return false;

            return true;
//        } catch (JwtException | IllegalArgumentException e) {
//            throw new JwtAuthenticationException("JWT token is expired or invalid");
//        }
    }

    private List<String> getRoleName(List<Role> userRoles) {
        List<String> result = new ArrayList<>();

        userRoles.forEach(role -> result.add(role.toString()));

        return result;
    }


}
