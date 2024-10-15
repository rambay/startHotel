package com.springboot.app.util;

import java.sql.Date;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class Token {
	private final static String TOKEN_SECRETO = "aLg3eqbV2S4pZd9AFiMh4mAcRAt1Y0Jb";
    private final static Long TOKEN_DURACION = 1_000_000L;

    public static String crearToken(String username, Long id, String rolName) {
        long expiracionTiempo = TOKEN_DURACION * 1_000;
        Date expiracionFecha = new Date(System.currentTimeMillis() + expiracionTiempo);

        Map<String, Object> map = new HashMap<>();
        map.put("nombre", username);
        map.put("id", id);
        map.put("rolName", rolName);

        return Jwts.builder()
                .setSubject(username)
                .setExpiration(expiracionFecha)
                .addClaims(map)
                .signWith(Keys.hmacShaKeyFor(TOKEN_SECRETO.getBytes()))
                .compact();

    }
    
    public static UsernamePasswordAuthenticationToken getAuth(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(TOKEN_SECRETO.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();

            return new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
        } catch (JwtException e) {
            return null;
        }
    }
}
