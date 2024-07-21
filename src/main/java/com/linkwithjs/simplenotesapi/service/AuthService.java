package com.linkwithjs.simplenotesapi.service;

import com.linkwithjs.simplenotesapi.dto.ReqRes;
import com.linkwithjs.simplenotesapi.entity.UserEntity;
import com.linkwithjs.simplenotesapi.repository.UserRepository;
import com.linkwithjs.simplenotesapi.utility.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Slf4j
@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    public ReqRes signUp(ReqRes registrationRequest) {
        ReqRes resp = new ReqRes();
        try {
            UserEntity users = new UserEntity();
            if (userRepository.findByEmail(registrationRequest.getEmail()).isEmpty()) {
                users.setEmail(registrationRequest.getEmail());
                users.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
                users.setRole(registrationRequest.getRole());
                UserEntity savedUser = userRepository.save(users);
                if (savedUser.getId() > 0) {
                    resp.setData(savedUser);
                    log.info("Created new user:  {}", users.getUsername());
                    resp.setMessage("User registered successfully.");
                }
            } else {
                log.warn("User already exists.");
                resp.setMessage("User already exists.");
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            log.error(e.getMessage());
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqRes login(ReqRes loginRequest) {
        ReqRes resp = new ReqRes();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            resp.setStatusCode(200);
            resp.setToken(jwt);
            resp.setRefreshToken(refreshToken);
            resp.setExpirationTime("24Hr");
            log.info("User logged in :  {}", user.getUsername());
            resp.setMessage("User logged in successfully.");

        } catch (Exception e) {
            resp.setStatusCode(500);
            log.error("Error: {}",e.getMessage());
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqRes refreshToken(ReqRes refreshTokenRequest) {
        ReqRes resp = new ReqRes();
        System.out.println("Refresh Token..."+refreshTokenRequest.getToken());
        String email = jwtUtils.extractUsername(refreshTokenRequest.getToken());
        UserEntity user = userRepository.findByEmail(email).orElseThrow();
        if (jwtUtils.isTokenValid(refreshTokenRequest.getToken(), user)) {
            var jwt = jwtUtils.generateToken(user);
            resp.setStatusCode(200);
            resp.setToken(jwt);
            resp.setRefreshToken(refreshTokenRequest.getToken());
            resp.setExpirationTime("24Hr");
            resp.setMessage("User refreshed successfully.");

        }
        resp.setStatusCode(200);
        return resp;
    }


}
