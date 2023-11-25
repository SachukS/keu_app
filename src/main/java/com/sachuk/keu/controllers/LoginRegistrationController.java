package com.sachuk.keu.controllers;

import com.sachuk.keu.database.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@Controller
@AllArgsConstructor
public class LoginRegistrationController {
//
//
//    private ApplicationEventPublisher eventPublisher;
//    private UserService userService;
//
//
//    @RequestMapping(value = "/register", method = RequestMethod.POST)
//    public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid User accountDto,
//                                            BindingResult result,
//                                            WebRequest request,
//                                            Errors errors) {
//
//        User registered = new User();
//        if (!result.hasErrors()) {
//            registered = userService.createUserAccount(accountDto, result);
//        }
//        if (registered == null) {
//            result.rejectValue("login", "message.regError");
//        }
//        if (result.hasErrors()) {
//            return new ModelAndView("register", "user", accountDto);
//        } else {
//            return new ModelAndView("login", "register", true);
//        }
//    }
//
//
//    @RequestMapping(value = "/register", method = RequestMethod.GET)
//    public String registerPage(Model model) {
//
//        model.addAttribute("user", new User());
//        return "register";
//    }
//
//    // Login form with error
//    @RequestMapping("/login-error")
//    public String loginError(Model model) {
//        model.addAttribute("loginError", true);
//        return "login";
//    }
//
////    public BCryptPasswordEncoder passwordEncoder() {
////        return new BCryptPasswordEncoder();
////    }
//
//    @RequestMapping("/login")
//    public String login(Model model) {
////        String pass = passwordEncoder().encode("silko2021");
////        System.out.println(pass);
//        return "login";
//    }
//
//    @ExceptionHandler(Throwable.class)
//    public String exception(final Throwable throwable, final Model model) {
//        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
//        model.addAttribute("errorMessage", errorMessage);
//        return "error";
//    }
//
//    @RequestMapping("/settings")
//    public String getSettingsPage(Model model) {
//        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated())
//            model.addAttribute("user", userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
//        return "";
//    }
//
//    @RequestMapping(value = "/logout", method = RequestMethod.GET)
//    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
//            new SecurityContextLogoutHandler().logout(request, response, auth);
//        }
//        return "redirect:/";
//    }
}