package com.sachuk.keu.controllers.rest;

import com.sachuk.keu.configurations.jwt.JwtUtils;
import com.sachuk.keu.database.repositories.MilitaryManRepository;
import com.sachuk.keu.database.repositories.RoleRepository;
import com.sachuk.keu.database.repositories.UserRepository;
import com.sachuk.keu.entities.MilitaryMan;
import com.sachuk.keu.entities.Role;
import com.sachuk.keu.entities.User;
import com.sachuk.keu.entities.enums.RoleEnum;
import com.sachuk.keu.entities.enums.SexEnum;
import com.sachuk.keu.entities.security.JwtResponse;
import com.sachuk.keu.entities.security.LoginRequest;
import com.sachuk.keu.services.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    MilitaryManRepository militaryManRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        MultipartFile ecp = loginRequest.getEcp();
        String password = loginRequest.getPassword();

        try {
            KeyStore kStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream is = ecp.getInputStream();
            kStore.load(is, password.toCharArray());
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
            ResponseEntity
                    .badRequest()
                    .body("Пароль електронного підпису не вірний");
        }

        User user = null;
        try {
            String jksContent = encryptionJksFileToString(ecp);
            Pattern p = Pattern.compile("pb_sign_(?<IPN>\\d{10}).+?ФІЗИЧНА ОСОБА\\d.\\d.+?\\w.+?\\d(?=(?<FULLNAME>.*?)\\d)");
            Matcher m = p.matcher(jksContent);

            if (m.find()){
                //check if user in user table by ipn, if not - parse ecp, create and save user
                if (!userRepository.findByIpn(m.group("IPN")).isPresent()){
                    String[] splitFullName = m.group("FULLNAME").split(" ");

                    user.setIpn(m.group("IPN"));
                    user.setPassword(password);
                    user.setName(splitFullName[0]);
                    user.setSurname(splitFullName[1]);
                    user.setThirdname(splitFullName[2]);
                    decodeIPN(m.group("IPN"), user);

                    Set<Role> role = new HashSet<>();
                    //check if user in military_man table
                    if(militaryManRepository.findByIpn(m.group("IPN")).isPresent()){
                        user.setMilitaryMan(militaryManRepository.findByIpn(m.group("IPN")).get());
                        role.add(new Role(RoleEnum.ROLE_USER));
                        user.setRoles(role);
                    } else {
                        role.add(new Role(RoleEnum.ROLE_NONE));
                        user.setRoles(role);
                    }

                    userRepository.save(user);
                } else {
                    user = userRepository.findByIpn(m.group("IPN")).get();
                }
            }
        } catch (IOException e) {
            ResponseEntity
                    .badRequest()
                    .body("Помилка при обробці данних");
        }

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

    public static String encryptionJksFileToString(MultipartFile file) throws IOException {
        InputStream fis = file.getInputStream();
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        String encodeContent = new String(buffer, StandardCharsets.UTF_8);

        StringBuilder stringBuilder = new StringBuilder();
        Scanner scanner = new Scanner(encodeContent);
        String line;
        while (scanner.hasNext()) {
            line = scanner.nextLine();
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }

    public static void decodeIPN(String ipn, User user) {

        String birthDate = ipn.substring(0, 5);
        int maleNumber = Integer.parseInt(ipn.substring(8, 9));

        int days = Integer.parseInt(birthDate);
        int year = 1900;

        while (days > 365) {
            int daysInYear = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) ? 366 : 365;
            if (days >= daysInYear) {
                days -= daysInYear;
                year++;
            } else {
                break;
            }
        }

        int month = 1;
        int[] daysInMonth = {0, 31, (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) ? 29 : 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        while (days > daysInMonth[month]) {
            days -= daysInMonth[month];
            month++;
        }

        user.setBirth_date(new Date(year, month, days));
        user.setSex(maleNumber % 2 == 0 ? SexEnum.FEMALE : SexEnum.MALE);
    }
}