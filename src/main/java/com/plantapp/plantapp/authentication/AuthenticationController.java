package com.plantapp.plantapp.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        AuthenticationResponse authenticationResponse = authenticationService.register(request);
        if (authenticationResponse == null) {
            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(null);
        }
        return ResponseEntity.ok(authenticationResponse);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/authenticate/google")
    public ResponseEntity<AuthenticationResponse> authenticateGoogle(@RequestBody GoogleAuthenticationRequest request) {
        AuthenticationResponse authenticationResponse = authenticationService.authenticateGoogle(request);
            if (authenticationResponse == null) {
            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(null);
        }
        return ResponseEntity.ok(authenticationResponse);
    }
}
