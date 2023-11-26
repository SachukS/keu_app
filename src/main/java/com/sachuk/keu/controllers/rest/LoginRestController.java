package com.sachuk.keu.controllers.rest;

import com.sachuk.keu.configurations.jwt.JwtUtils;
import com.sachuk.keu.database.repositories.RoleRepository;
import com.sachuk.keu.database.repositories.UserRepository;
import com.sachuk.keu.entities.User;
import com.sachuk.keu.entities.security.JwtResponse;
import com.sachuk.keu.entities.security.LoginRequest;
import com.sachuk.keu.services.security.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1")
public class LoginRestController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        MultipartFile ecp = loginRequest.getEcp();
        String password = loginRequest.getPassword();

        //code for checking ecp
        try {
            FileInputStream key = new FileInputStream("C:\\work\\keu_app\\pb_3746509431.jks");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(key, "pass".toCharArray());
        } catch (IOException | NoSuchAlgorithmException | CertificateException | KeyStoreException e) {
            return ResponseEntity
                    .badRequest()
                    .body("Пароль електронного підпису невірний");
        }


        //check if user in user table by ipn, if not - parse ecp, create and save user
        //check if user in militaryman table
        User user = new User();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken("ipn", password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                user.getName(),
                user.getSurname(),
                user.getThirdname(),
                roles));
    }
}