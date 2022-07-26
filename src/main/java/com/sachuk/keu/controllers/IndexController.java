package com.sachuk.keu.controllers;

import com.sachuk.keu.database.service.CustomerService;
import com.sachuk.keu.database.service.UserService;
import com.sachuk.keu.entities.SearchQuery;
import com.sachuk.keu.entities.security.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@Controller
@AllArgsConstructor
public class IndexController {

    //public EnrolleeService enrolleeService;
    public CustomerService customerService;
    private final UserService databaseUserService;

    @ModelAttribute("query")
    public SearchQuery createQuery() {
        return new SearchQuery();
    }

    @ModelAttribute("uri")
    public String getUri(HttpServletRequest request){ return request.getRequestURI().substring(request.getContextPath().length()); }

    @RequestMapping(value = {"/", "/cabinet"}, method = {RequestMethod.GET})
    public String show(@RequestParam(value = "error", required = false, defaultValue = "false") boolean error,
                       @RequestParam(value = "success", required = false, defaultValue = "false") boolean success,
                       Model modelAndView) {
        User user = databaseUserService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        System.out.println(user);
        long kyivCount = (long) customerService.findByGarrison("м.Київ").size();
        modelAndView.addAttribute("success", success);
        modelAndView.addAttribute("error", error);
        modelAndView.addAttribute("user", user);
        modelAndView.addAttribute("kyivCount", kyivCount);
        modelAndView.addAttribute("todayCount", customerService.countAllAfterDate(LocalDate.now().atStartOfDay()));
        modelAndView.addAttribute("oblastCount", customerService.count() - kyivCount);
        modelAndView.addAttribute("allCount", customerService.count());
        return "cabinet";

    }
    @RequestMapping(value = {"/cabinet/delete/{id}"}, method = {RequestMethod.GET})
    public String delete(@PathVariable("id") String id,
                       Model modelAndView) {
        User user = databaseUserService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (customerService.existsById(Long.valueOf(id))) {
            customerService.deleteById(Long.valueOf(id));
            return "redirect:/cabinet?success=true";
        }
        return "redirect:/cabinet?success=false";
    }

    @RequestMapping(value = "/redirect", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView showMain(ModelAndView modelAndView) {

        modelAndView = new ModelAndView();
        modelAndView.setViewName("input");
        return modelAndView;
    }
}