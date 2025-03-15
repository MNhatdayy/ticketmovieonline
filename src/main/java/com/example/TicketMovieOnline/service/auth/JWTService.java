package com.example.TicketMovieOnline.service.auth;

import com.example.TicketMovieOnline.model.User;
import com.example.TicketMovieOnline.repository.Auth.TokenRepository;
import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

@Service
public class JWTService {
    Dotenv dotenv = Dotenv.configure()
            .filename("local.env") // Chỉ định tên file .env
            .load(); // Load file .env
    String secretKey = dotenv.get("SECRET_KEY");
    String accessTokenExpiration = dotenv.get("ACCESS_TOKEN_EXPIRATION");
    String refreshTokenExpiration = dotenv.get("REFRESH_TOKEN_EXPIRATION");
    public JWTService(TokenRepository tokenRepository){
        this.tokenRepository = tokenRepository;
    }
    private final TokenRepository tokenRepository;
    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }
    public boolean isValid (String token, UserDetails user) {
        String username = extractUsername(token);
        boolean validToken = tokenRepository
                .findByAccessToken(token)
                .map(t -> !t.isLoggedOut())
                .orElse(false);
        return (username.equals(user.getUsername())) && !isTokenExpired(token) && validToken;
    }
    public String generateAccessToken(User user) {
        return generateToken(user, Long.parseLong(accessTokenExpiration));
    }
    public String generateRefreshToken(User user) {
        return generateToken(user, Long.parseLong(refreshTokenExpiration));

    }
    public boolean isValidRefreshToken(String token, User user) {
        String username = extractUsername(token);
        boolean validRefreshToken = tokenRepository
                .findByRefreshToken(token)
                .map(t -> !t.isLoggedOut())
                .orElse(false);
        return (username.equals(user.getUsername())) && !isTokenExpired(token) && validRefreshToken;
    }
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }
    private Claims ExtractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigninkey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public <T> T extractClaims(String token, Function<Claims, T> resolver){
        Claims claims = ExtractAllClaims(token);
        return resolver.apply(claims);
    }
    private String generateToken(User user, long expireTime) {
        String token = Jwts
                .builder()
                .subject(user.getUsername())
                .claim("role", user.getRole())
                .claim("id", user.getId())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+ expireTime))
                .signWith(getSigninkey())
                .compact();
        return token;
    }
    private SecretKey getSigninkey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
