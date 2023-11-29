package com.sachuk.keu.controllers.rest;

import com.google.gson.Gson;
import com.iit.certificateAuthority.endUser.libraries.signJava.*;
import com.sachuk.keu.configurations.jwt.JwtUtils;
import com.sachuk.keu.controllers.rest.dto.dia.DataDTO;
import com.sachuk.keu.database.service.UserService;
import com.sachuk.keu.entities.User;
import com.sachuk.keu.entities.security.JwtResponse;
import com.sachuk.keu.services.security.UserDetailsImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", maxAge = 3600)
public class IndexRestController {

    AuthenticationManager authenticationManager;
    UserService userService;

    JwtUtils jwtUtils;

    EndUser endUser = new EndUser();

    public IndexRestController(UserService userService, JwtUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/")
    public ResponseEntity<?> hello(){
        class Test{
            public boolean success = true;
        }
        return ResponseEntity.ok(new Test());
    }

    @PostMapping(value = "/", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> fromDiiaSign(@RequestPart("encodeData") String encodeData) throws Exception {
        init();
        System.out.println(encodeData);
        String jObject = new String(endUser.BASE64Decode(encodeData), StandardCharsets.UTF_8);
        Gson gson = new Gson();
        DataDTO data = gson.fromJson(jObject, DataDTO.class);
        System.out.println(data.getRequestId() + " REQ ID");
        System.out.println(data.getSignature() + " SIGN");
        // Sign decryption and login will be provided in future on prod.
        class Test{
            public boolean success = true;
        }
        return ResponseEntity.ok(new Test());
    }

    @PostMapping(value = "/dia/login")
    public ResponseEntity<?> login(){
        User user = new User();
        String IPN = "1234567021";
        if(userService.findByIpn(IPN).isPresent()){
            user = userService.findByIpn(IPN).get();
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken("ipn", 0));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(), // це ipn
                    user.getName(),
                    user.getSurname(),
                    user.getThirdname(),
                    roles));
        }
        else {
            System.out.println("nobody found");
            return ResponseEntity.unprocessableEntity().body("Nothing");
        }
    }

    @GetMapping("/diia/documents")
    public ResponseEntity<?> docs(){
        class Test{
            public boolean success = true;
        }
        return ResponseEntity.ok(new Test());
    }

    @PostMapping("/diia/documents")
    public ResponseEntity<?> fromDiiaDocs() {
        init();
        class Test{
            public boolean success = true;
        }
        return ResponseEntity.ok(new Test());
    }

    private String generateRequestId(String uuid4) throws Exception {
        init();
        byte[] array = uuid4.getBytes(); // mb 44 due to documentation
        String hash = endUser.Hash(array);
        return hash; // TODO RABOTAI

    }

    public void init(){
        endUser.SetUIMode(false);
        endUser.SetLanguage(EndUser.EU_RU_LANG);
        endUser.SetCharset("UTF-8");
        try {
            endUser.Initialize();
            EndUserModeSettings modeSettings = new EndUserModeSettings(false);
            modeSettings.SetOfflineMode(false);
            endUser.SetModeSettings(modeSettings);
            EndUserProxySettings proxySettings = endUser.CreateProxySettings();
            proxySettings.SetUseProxy(false);
            proxySettings.SetAnonymous(true);
            proxySettings.SetAddress("");
            proxySettings.SetPort("");
            proxySettings.SetUser("");
            proxySettings.SetPassword("");
            proxySettings.SetSavePassword(false);
            endUser.SetProxySettings(proxySettings);
            EndUserFileStoreSettings fileStoreSettings = endUser.CreateFileStoreSettings();
            fileStoreSettings.SetPath("/static/enduserstorage");
            fileStoreSettings.SetSaveLoadedCerts(true);
            endUser.SetFileStoreSettings(fileStoreSettings);
            EndUserCMPSettings cmpSettings = endUser.CreateCMPSettings();
            cmpSettings.SetUseCMP(true);
            cmpSettings.SetAddress("http://acsk.privatbank.ua/services/cmp/");
            cmpSettings.SetPort("80");
            cmpSettings.SetCommonName("");
            endUser.SetCMPSettings(cmpSettings);
            EndUserTSPSettings tspSettings = endUser.CreateTSPSettings();
            tspSettings.SetAddress("http://acsk.privatbank.ua/services/tsp/");
            tspSettings.SetPort("80");
            tspSettings.SetGetStamps(true);
            endUser.SetTSPSettings(tspSettings);
            EndUserOCSPSettings ocspSettings = endUser.CreateOCSPSettings();
            ocspSettings.SetAddress("http://acsk.privatbank.ua/services/ocsp/");
            ocspSettings.SetPort("80");
            ocspSettings.SetUseOCSP(true);
            endUser.SetOCSPSettings(ocspSettings);
            EndUserLDAPSettings lDAPSettings = endUser.CreateLDAPSettings();
            endUser.SetLDAPSettings(lDAPSettings);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
