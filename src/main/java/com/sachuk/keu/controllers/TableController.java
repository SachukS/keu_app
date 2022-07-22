package com.sachuk.keu.controllers;

import com.sachuk.keu.database.service.CustomerService;
import com.sachuk.keu.database.service.UserService;
import com.sachuk.keu.entities.Customer;
import com.sachuk.keu.entities.SearchQuery;
import com.sachuk.keu.entities.security.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;

@Transactional
@Controller
@AllArgsConstructor
public class TableController {

//    private RatingGenerator ratingGenerator;
    private CustomerService customerService;
    private UserService databaseUserService;
//    private SpecialtyService specialtyService;

    @ModelAttribute("query")
    public SearchQuery createQuery() {
        return new SearchQuery();
    }

    @RequestMapping(value = {"/table"}, method = {RequestMethod.GET})
    public String showTable(Model modelAndView, @ModelAttribute("query") SearchQuery query,
                            @RequestParam(required = false, defaultValue = "0") int currentPage,
                            @RequestParam(required = false, defaultValue = "10") int pageSize) {

        User user = databaseUserService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        System.out.println(user);

        Page<Customer> customers = customerService.findAll(PageRequest.of(currentPage, pageSize));
        customers.stream().sorted(Comparator.comparing(Customer::getAccountingDate));

        modelAndView.addAttribute("user", user);
        modelAndView.addAttribute("customers", customers);
        modelAndView.addAttribute("currentPage", currentPage + 1);
        modelAndView.addAttribute("pageSize", pageSize);
        modelAndView.addAttribute("search",false);
        return "table";

    }

    @RequestMapping(value = {"/find"}, method = {RequestMethod.POST})
    public String find (Model modelAndView, @ModelAttribute("query") SearchQuery query) {
        System.out.println(query.getQuery());
        User user = databaseUserService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        System.out.println(user);
        modelAndView.addAttribute("user", user);
        List<Customer> customerList = customerService.freeFind(query.getQuery());
        modelAndView.addAttribute("customers", customerList);
        modelAndView.addAttribute("search",true);
        return "table";

    }

}
