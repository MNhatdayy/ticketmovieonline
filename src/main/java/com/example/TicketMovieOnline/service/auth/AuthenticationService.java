package com.example.TicketMovieOnline.service.auth;

import com.example.TicketMovieOnline.DTO.response.AuthenticationResponse;
import com.example.TicketMovieOnline.model.Role;
import com.example.TicketMovieOnline.model.Token;
import com.example.TicketMovieOnline.model.User;
import com.example.TicketMovieOnline.repository.Auth.TokenRepository;
import com.example.TicketMovieOnline.repository.UserRepository;
import com.nimbusds.openid.connect.sdk.AuthenticationRequest;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    public AuthenticationService(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 JWTService jwtService,
                                 AuthenticationManager authenticationManager,
                                 TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.tokenRepository = tokenRepository;
    }
    public AuthenticationResponse register(User request) {
        if(userRepository.findByUsername(request.getUsername()).isPresent()) {
            return new AuthenticationResponse(null,null,"User already exist");
       }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setRole(Role.USER);
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(accessToken,refreshToken,user);
        return new AuthenticationResponse(accessToken, refreshToken, "User registration successful");
    }
    public AuthenticationResponse authenticate(User request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllTokensByUser(user);
        return new AuthenticationResponse(accessToken, refreshToken, "User login was successful");
    }
    private void revokeAllTokensByUser(User user) {
        List<Token> validTokens = tokenRepository.findAllAccessTokensByUser(user.getId());
        if(validTokens.isEmpty()){
            return;
        }
        validTokens.forEach(token -> {
            token.setLoggedOut(true);
        });
        tokenRepository.saveAll(validTokens);
    }
    private void saveUserToken(String accessToken, String refreshToken, User user){
        if (user.getId() == null) { // User chưa được lưu
            userRepository.save(user); // Lưu User trước
        }
        Token token = new Token();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setUser(user);
        token.setLoggedOut(false);
        tokenRepository.save(token);
    }
    public ResponseEntity refreshToken(HttpServletRequest request,
                                       HttpServletResponse response){
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        if(jwtService.isValidRefreshToken(token,user)){
            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);
            revokeAllTokensByUser(user);
            saveUserToken(accessToken,refreshToken,user);
            return new ResponseEntity(new AuthenticationResponse(accessToken,refreshToken,"New token generated"), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
}
