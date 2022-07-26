package com.sachuk.keu.controllers;


import com.sachuk.keu.database.service.CustomerService;
import com.sachuk.keu.database.service.QuotaService;
import com.sachuk.keu.database.service.UserService;
import com.sachuk.keu.database.service.WorkService;
import com.sachuk.keu.entities.Customer;
import com.sachuk.keu.entities.Quota;
import com.sachuk.keu.entities.SearchQuery;
import com.sachuk.keu.entities.Work;
import com.sachuk.keu.entities.enums.QuotaType;
import com.sachuk.keu.entities.enums.Registrated;
import com.sachuk.keu.entities.security.User;
import com.sachuk.keu.services.rating.RatingXLSWeb;
import com.sachuk.keu.services.rating.RatingXlsCreateService;
import lombok.AllArgsConstructor;

import org.springframework.core.io.InputStreamResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.awt.print.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class RaitingController {
    private UserService databaseUserService;
    private RatingXlsCreateService ratingXlsCreateService;
    public QuotaService quotaService;
    public WorkService workService;
    public CustomerService customerService;

    @ModelAttribute("quotas")
    @Transactional
    public List<Quota> getQuota(){
        return quotaService.findAll().stream()
                .sorted(Comparator.comparing(Quota::getNameQuota)).collect(Collectors.toList());
    }

    public Collection<String> getAllWorkPlaces(String garrison) {
        switch (garrison) {
            case "KYIV":
                garrison = "м.Київ";
                break;
            case "BORI":
                garrison = "Бориспiль";
                break;
            case "SEMI":
                garrison = "Семиполки";
                break;
            case "PERE":
                garrison = "Переяславський";
                break;
            case "BROV":
                garrison = "Бровари";
                break;
            case "GOST":
                garrison = "Гостомель";
                break;
            case "VASY":
                garrison = "Василькiв";
                break;
        }
        String garr = garrison;
        return workService.findAll().stream().filter(w -> w.getGarrison().equals(garr))
                .sorted(Comparator.comparing(Work::getWorkPlace)).map(Work::getWorkPlace).distinct().collect(Collectors.toList());
    }

    public Collection<String> getAllAccountings(String garrison) {
        switch (garrison) {
            case "KYIV":
                garrison = "м.Київ";
                break;
            case "BORI":
                garrison = "Бориспiль";
                break;
            case "SEMI":
                garrison = "Семиполки";
                break;
            case "PERE":
                garrison = "Переяславський";
                break;
            case "BROV":
                garrison = "Бровари";
                break;
            case "GOST":
                garrison = "Гостомель";
                break;
            case "VASY":
                garrison = "Василькiв";
                break;
        }
        String garr = garrison;
        return workService.findAll().stream().filter(w -> w.getGarrison().equals(garr))
                .sorted(Comparator.comparing(Work::getWorkPlace)).map(Work::getAccountingPlace).distinct().collect(Collectors.toList());
    }
    private static List<Customer> staticCustomers = new ArrayList<>();
    private static List<Customer> staticSortedCustomersZag;

    @PostMapping("/getRating/{garrison}")
    public String getRating(@PathVariable String garrison,
                            @ModelAttribute("query") SearchQuery query,
                            @ModelAttribute("customer") Customer customer,
                            Model model) {

        User user = databaseUserService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        System.out.println(customer);

        staticCustomers = RatingXLSWeb.getCustomers(garrison, customer);

        model.addAttribute("customer", customer);
        model.addAttribute("workPlaces", getAllWorkPlaces(garrison.substring(0,4)));
        model.addAttribute("accountings", getAllAccountings(garrison.substring(0,4)));
        model.addAttribute("user", user);
        model.addAttribute("sort", garrison.substring(4));
        model.addAttribute("search", false);
        model.addAttribute("garrison", garrison.substring(0,4));
        model.addAttribute("customers", staticCustomers);
        return "rating";
    }


    @PostMapping("/getRating/{garrison}/print")
    public void printToFile(@PathVariable String garrison,
                              @ModelAttribute("query") SearchQuery query,
                              @ModelAttribute("customer") Customer customer,
                              HttpServletResponse response,
                              Model model) {

        Path file = Paths.get(System.getProperty("user.home") + File.separator + garrison + ".xls");

        response.setHeader("Content-disposition", "attachment; filename=" + garrison + ".xls");
        try {
            response.getOutputStream().write(Files.readAllBytes(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



    @GetMapping("/generateAndShowRating/{garrison}")
    public String generateAndShowRating(@PathVariable String garrison,
                                        @ModelAttribute("query") SearchQuery query,
                                        Model model) {
        User user = databaseUserService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        Customer customer = new Customer();
        customer.setWork(new Work("Всі","Всі", ""));
        customer.setQuota(new Quota("Всі","Всі", QuotaType.NONE));
        customer.setRoomCount(0);
        customer.setFamilyCount(0);
        customer.setRegistrated(Registrated.YES);
        customer.setExperience("100");

        staticCustomers = RatingXLSWeb.getCustomers(garrison + "ZAGALNA", customer);

        model.addAttribute("customer", customer);
        model.addAttribute("workPlaces", getAllWorkPlaces(garrison));
        model.addAttribute("accountings", getAllAccountings(garrison));
        model.addAttribute("user", user);
        model.addAttribute("sort",   "ZAGALNA");
        model.addAttribute("garrison", garrison);
        model.addAttribute("search", false);
        model.addAttribute("customers", staticCustomers);

        return "rating";
    }
}