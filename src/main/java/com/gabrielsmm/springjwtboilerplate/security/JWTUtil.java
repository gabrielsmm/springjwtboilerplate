package com.gabrielsmm.springjwtboilerplate.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String gerarToken(String nomeUsuario) {
        return Jwts.builder()
                .subject(nomeUsuario)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    public boolean tokenValido(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {
            String nomeUsuario = claims.getSubject();
            Date dataExpiracao = claims.getExpiration();
            Date dataAgora = new Date(System.currentTimeMillis());
            return nomeUsuario != null && dataExpiracao != null && dataAgora.before(dataExpiracao);
        }
        return false;
    }

    public String getNomeUsuario(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {
            return claims.getSubject();
        }
        return null;
    }

    private Claims getClaims(String token) {
        JwtParser jwtParser = Jwts.parser()
                .verifyWith(getSigningKey())
                .build();
        try {
            return jwtParser.parseSignedClaims(token).getPayload();
        } catch (Exception e) {
            return null;
        }
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
