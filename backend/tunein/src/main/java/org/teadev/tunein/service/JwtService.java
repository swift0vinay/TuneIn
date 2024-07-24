package org.teadev.tunein.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    
    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;
    
    @Value("${security.jwt.expiration-time}")
    private Long EXPIRATION_TIME;
    
    @Autowired
    private TokenBlacklistService tokenBlacklistService;
    
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    
    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    public String getUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }
    
    public long getExpirationTime() {
        return EXPIRATION_TIME;
    }
    
    private String buildToken(Map<String, Object> extraClaims,
                              UserDetails userDetails,
                              long expiration) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, EXPIRATION_TIME);
    }
    
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }
    
    public Date getExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }
    
    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }
    
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsername(token);
        return !tokenBlacklistService.isTokenBlackListed(token)
                && username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }
    
}
