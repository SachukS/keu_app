package com.sachuk.keu.controllers.rest;

import com.sachuk.keu.controllers.rest.dto.FinanceSourceResponseDTO;
import com.sachuk.keu.database.service.UserService;
import com.sachuk.keu.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserRestController {
    public UserService userService;
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.findById(id).orElseThrow(
                () -> new IllegalArgumentException("User with id: " + id + " is not found"));
    }
}
