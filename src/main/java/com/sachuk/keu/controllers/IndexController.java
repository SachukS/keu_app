package com.sachuk.keu.controllers;

import com.sachuk.keu.database.service.UserService;
import com.sachuk.keu.entities.SearchQuery;
import com.sachuk.keu.entities.security.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@Controller
@AllArgsConstructor
public class IndexController {

    //public EnrolleeService enrolleeService;
    private final UserService databaseUserService;

    @ModelAttribute("query")
    public SearchQuery createQuery() {
        return new SearchQuery();
    }
//    @ModelAttribute("allEnrolees")
//    public List<Enrollee> findAllEnrolee() { return enrolleeService.findAll(); }
    @ModelAttribute("uri")
    public String getUri(HttpServletRequest request){ return request.getRequestURI().substring(request.getContextPath().length()); }

    @RequestMapping(value = {"/", "/cabinet"}, method = {RequestMethod.GET})
    public String show(@RequestParam(value = "error", required = false, defaultValue = "false") boolean error,
                       @RequestParam(value = "success", required = false, defaultValue = "false") boolean success,
                       Model modelAndView) {
        User user = databaseUserService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        System.out.println(user);
        modelAndView.addAttribute("success", success);
        modelAndView.addAttribute("error", error);
        modelAndView.addAttribute("user", user);
        modelAndView.addAttribute("militaryCount", 1);
        modelAndView.addAttribute("todayCount", 1);
        modelAndView.addAttribute("lastWeekCount", 1);
        modelAndView.addAttribute("checkNeededCount", 1);
        return "cabinet";

    }

    @RequestMapping(value = "/redirect", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView showMain(ModelAndView modelAndView) {

        modelAndView = new ModelAndView();
        modelAndView.setViewName("input");
        return modelAndView;
    }
}