package com.github.laziestcoder.handsontry.train_ticket.controller;

import com.github.laziestcoder.handsontry.train_ticket.dto.LoginRequest;
import com.github.laziestcoder.handsontry.train_ticket.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.github.laziestcoder.handsontry.train_ticket.constant.RailwayValues.TOKEN_KEY;

/**
 * @author TOWFIQUL ISLAM
 * @since 26/11/24
 */

@RequiredArgsConstructor
@RequestMapping("/api/login")
@RestController
public class LoginController {
    private final LoginService loginService;

    @GetMapping("/token")
    public ResponseEntity<Map<String, Object>> login() {
        String token = loginService.login();
        return new ResponseEntity<>(
                Map.of("success", true, TOKEN_KEY, token),
                HttpStatus.OK);
    }

    @PostMapping("/token")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {
        String token = loginService.login(loginRequest);
        return new ResponseEntity<>(
                Map.of("success", true, TOKEN_KEY, token),
                HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<Map<String, Object>> invalidateLogin() {
        loginService.invalidateLogin();
        return new ResponseEntity<>(
                Map.of("success", true, "message", "token cleared"),
                HttpStatus.OK
        );
    }
}
