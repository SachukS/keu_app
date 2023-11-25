package com.sachuk.keu.controllers;

import com.sachuk.keu.database.service.MilitaryManService;
import com.sachuk.keu.database.service.UserService;
import com.sachuk.keu.entities.SearchQuery;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@Controller
@AllArgsConstructor
public class IndexController {
//    private static final Logger log = Logger.getLogger(IndexController.class);
//
//    public MilitaryManService militaryManService;
//    private final UserService databaseUserService;
//
//    @ModelAttribute("query")
//    public SearchQuery createQuery() {
//        return new SearchQuery();
//    }
//
//    @ModelAttribute("uri")
//    public String getUri(HttpServletRequest request) {
//        return request.getRequestURI().substring(request.getContextPath().length());
//    }
//
//    @RequestMapping(value = {"/", "/cabinet"}, method = {RequestMethod.GET})
//    public String show(@RequestParam(value = "error", required = false, defaultValue = "false") boolean error,
//                       @RequestParam(value = "success", required = false, defaultValue = "false") boolean success,
//                       Model modelAndView) {
//        User user = databaseUserService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
//        System.out.println(user);
//        long kyivCount = (long) militaryManService.findByGarrison("м.Київ").size();
//        modelAndView.addAttribute("success", success);
//        modelAndView.addAttribute("error", error);
//        modelAndView.addAttribute("user", user);
//        modelAndView.addAttribute("kyivCount", kyivCount);
//        modelAndView.addAttribute("todayCount", militaryManService.countAllAfterDate(LocalDate.now().atStartOfDay()));
//        modelAndView.addAttribute("oblastCount", militaryManService.count() - kyivCount);
//        modelAndView.addAttribute("allCount", militaryManService.count());
//        return "cabinet";
//
//    }
//
//    @RequestMapping(value = {"/cabinet/delete/{id}"}, method = {RequestMethod.GET})
//    public String delete(@PathVariable("id") String id,
//                         Model modelAndView) {
//        User user = databaseUserService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
//        log.info("deleting customer");
//        if (militaryManService.existsById(Long.valueOf(id))) {
//            militaryManService.deleteById(Long.valueOf(id));
//            log.info("customer above deleted");
//            return "redirect:/cabinet?success=true";
//        }
//        return "redirect:/cabinet?success=false";
//    }
//
//    @RequestMapping(value = "/redirect", method = {RequestMethod.GET, RequestMethod.POST})
//    public ModelAndView showMain(ModelAndView modelAndView) {
//
//        modelAndView = new ModelAndView();
//        modelAndView.setViewName("input");
//        return modelAndView;
//    }
}