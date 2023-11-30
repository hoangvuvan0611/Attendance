package com.v1.attendance.services;

import com.v1.attendance.models.entities.QRCode;
import io.jsonwebtoken.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Data
@Slf4j
public class JwtTokenProvider {
    private final String JWT_SECRET = "vuvanhoang";
    private final Long JWT_EXPIRATION = 60000000000L;

    public String generateToken(QRCode qrCode) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(qrCode.toString())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + JWT_EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}
