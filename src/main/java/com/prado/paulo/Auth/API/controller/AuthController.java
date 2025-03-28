package com.prado.paulo.Auth.API.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prado.paulo.Auth.API.domain.user.AuthenticationDTO;
import com.prado.paulo.Auth.API.domain.user.LoginResponseDTO;
import com.prado.paulo.Auth.API.domain.user.RegisterDTO;
import com.prado.paulo.Auth.API.domain.user.User;
import com.prado.paulo.Auth.API.repositories.UserRepository;
import com.prado.paulo.Auth.API.services.TokenService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody AuthenticationDTO authenticationDTO) {
        var usernamePassowrd = new UsernamePasswordAuthenticationToken(authenticationDTO.login(), authenticationDTO.password());
        @SuppressWarnings("unused")
        var auth = this.authenticationManager.authenticate(usernamePassowrd);


        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Validated RegisterDTO registerDTO) {
        
        if(this.repository.findByLogin(registerDTO.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        User user = new User(registerDTO.login(), encryptedPassword, registerDTO.role());

        this.repository.save(user);

        return ResponseEntity.ok().build();
    }
}
