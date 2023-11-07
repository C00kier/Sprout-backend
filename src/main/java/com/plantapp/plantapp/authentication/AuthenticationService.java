package com.plantapp.plantapp.authentication;

import com.plantapp.plantapp.configuration.JwtService;
import com.plantapp.plantapp.user.model.User;
import com.plantapp.plantapp.user.repository.UserRepository;
import com.plantapp.plantapp.user_type.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {

        boolean userExists = userRepository.existsByUserNameOrUserEmail(request.getUsername(), request.getEmail());

        if (userExists) {
            return null;
        }

        User user = new User();
        user.setUserName(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUserType(UserType.USER);
        userRepository.save(user);

        String jwt = jwtService.generateToken((UserDetails) user);

        return AuthenticationResponse.builder()
                .token(jwt)
                .userId(user.getUserId())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepository.findByUserName(request.getUsername()).orElseThrow();

        String jwt = jwtService.generateToken((UserDetails) user);

        return AuthenticationResponse.builder()
                .token(jwt)
                .userId(user.getUserId())
                .build();
    }
}