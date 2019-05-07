package com.financeiro.security.jwt;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.financeiro.model.security.Usuario;
import com.financeiro.security.UserDetailsServiceImpl;
import com.financeiro.service.exception.InvalidTokenRequestException;

import java.time.Instant;
import java.util.Date;

//@Component
public class JwtTokenProvider {

	private static String SECRET = "Sy$t3m@";
	private static int    EXPIRATION = 900000;

  
    public String generateToken(Usuario customUserDetails) {
        Instant expiryDate = Instant.now().plusMillis(EXPIRATION);
        return Jwts.builder()
                .setSubject(Integer.toString(customUserDetails.getId()))
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(expiryDate))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    
    public String generateTokenFromUserId(Integer userId) {
        Instant expiryDate = Instant.now().plusMillis(EXPIRATION);
        return Jwts.builder()
                .setSubject(Integer.toString(userId))
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(expiryDate))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }


    public Integer getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();

        return Integer.parseInt(claims.getSubject());
    }

    
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            throw new InvalidTokenRequestException("JWT", authToken, "Incorrect signature");
        } catch (MalformedJwtException ex) {
            throw new InvalidTokenRequestException("JWT", authToken, "Malformed jwt token");
        } catch (ExpiredJwtException ex) {
            throw new InvalidTokenRequestException("JWT", authToken, "Token expired. Refresh required.");
        } catch (UnsupportedJwtException ex) {
            throw new InvalidTokenRequestException("JWT", authToken, "Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new InvalidTokenRequestException("JWT", authToken, "Illegal argument token");
        }
    }

   
}