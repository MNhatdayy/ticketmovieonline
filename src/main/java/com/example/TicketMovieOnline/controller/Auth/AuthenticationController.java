package com.example.TicketMovieOnline.controller.Auth;

import com.example.TicketMovieOnline.DTO.response.AuthenticationResponse;
import com.example.TicketMovieOnline.model.User;
import com.example.TicketMovieOnline.service.auth.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody User request){
        return ResponseEntity.ok(authenticationService.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody User request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
