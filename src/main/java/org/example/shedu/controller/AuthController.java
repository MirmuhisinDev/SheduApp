package org.example.shedu.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.shedu.payload.ApiResponse;
import org.example.shedu.payload.auth.RequestLogin;
import org.example.shedu.payload.auth.RequestUser;
import org.example.shedu.service.authService.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody @Valid RequestUser user) {
        ApiResponse register = authService.register(user);
        return ResponseEntity.status(register.getStatus()).body(register);
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody RequestLogin user) {
        ApiResponse login = authService.login(user);
        return ResponseEntity.ok(login);
    }

    @PostMapping("/code")
    public ResponseEntity<ApiResponse> activation(@RequestParam int code){
        ApiResponse apiResponse = authService.activationCode(code);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PostMapping("/save-master")
    public ResponseEntity<ApiResponse> saveMaster(@RequestBody RequestUser user) {
        ApiResponse apiResponse = authService.saveMaster(user);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }
}
