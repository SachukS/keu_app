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
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class RaitingController {
    private UserService databaseUserService;
    private RatingXlsCreateService ratingXlsCreateService;
    public QuotaService quotaService;
    public WorkService workService;
    public CustomerService customerService;

    public static List<String> garrisons = Arrays.asList("м.Київ", "Бориспiль", "Семиполки", "Переяславський", "Бровари", "Гостомель", "Василькiв");

    @ModelAttribute("quotas")
    @Transactional
    public List<Quota> getQuota() {
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

    @PostMapping("/getRating/{garrison}")
    public String getRating(@PathVariable String garrison,
                            @ModelAttribute("query") SearchQuery query,
                            @ModelAttribute("customer") Customer customer,
                            @RequestParam(required = false, defaultValue = "0") int currentPage,
                            @RequestParam(required = false, defaultValue = "50") int pageSize,
                            Model model) {

        User user = databaseUserService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        System.out.println(customer);

        staticCustomers = RatingXLSWeb.getCustomers(garrison, customer);

        PagedListHolder<Customer> pagination = new PagedListHolder<>(staticCustomers);
        pagination.setPage(currentPage);
        pagination.setPageSize(pageSize);

        model.addAttribute("currentPage", currentPage + 1);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("customer", customer);
        model.addAttribute("workPlaces", getAllWorkPlaces(garrison.substring(0, 4)));
        model.addAttribute("accountings", getAllAccountings(garrison.substring(0, 4)));
        model.addAttribute("user", user);
        model.addAttribute("sort", garrison.substring(4));
        model.addAttribute("search", false);
        model.addAttribute("firstopen", false);
        model.addAttribute("garrison", garrison.substring(0, 4));
        model.addAttribute("customers", pagination);
        return "rating";
    }


    @PostMapping("/getRating/{garrison}/print")
    public void printToFile(@PathVariable String garrison,
                            @ModelAttribute("query") SearchQuery query,
                            @ModelAttribute("customer") Customer customer,
                            HttpServletResponse response,
                            Model model) {
        System.out.println(customer);
        ratingXlsCreateService.createXls(System.getProperty("user.home") + File.separator + garrison + ".xls", staticCustomers, customer);

        Path file = Paths.get(System.getProperty("user.home") + File.separator + garrison + ".xls");

        response.setHeader("Content-disposition", "attachment; filename=" + garrison + ".xls");
        try {
            response.getOutputStream().write(Files.readAllBytes(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/calculate")
    public String calculateQueue(Model model) {
        List<Customer> customers;
        List<Customer> customersPersho;
        List<Customer> customersPoza;

        Customer customer = new Customer();
        for (String gar : garrisons) {
            customers = customerService.findByGarrison(gar);
            //customers = customers.stream().filter(c -> c.getRegistrated().equals(Registrated.YES)).sorted(Comparator.comparing(Customer::getAccountingDate)).collect(Collectors.toList());
            customers = customers.stream().filter(c -> c.getRegistrated().equals(Registrated.YES)).sorted(Comparator.comparing(Customer::getAccountingDate).reversed()
                            .thenComparing(Customer::getQuotaType).reversed()
                            .thenComparing(c -> c.getQuotaDate() != null ? c.getQuotaDate() : c.getAccountingDate()))
                    .collect(Collectors.toList());
            for (int i = 0; i < customers.size(); i++) {
                customers.get(i).setZagalna(String.valueOf(i + 1));
            }
            customerService.saveAll(customers);
            customersPersho = new ArrayList<>(customers);

            customersPoza = customers.stream()
                    .filter(c -> c.getQuotaType().equals("позачерговий"))
                    .sorted(Comparator.comparing(Customer::getQuotaDate)
                            .thenComparing(c -> c.getAccountingDate()))
                    .collect(Collectors.toList());
            for (int i = 0; i < customersPoza.size(); i++) {
                customersPoza.get(i).setPilgova(String.valueOf(i + 1));
            }
            customerService.saveAll(customersPoza);

            customersPersho = customersPersho.stream()
                    .filter(c -> c.getQuotaType().equals("першочерговий") || (c.getQuotaType2() != null && c.getQuotaType2().equals("першочерговий")))
                    .sorted(Comparator.comparing(Customer::getQuotaDate)
                            .thenComparing(c -> c.getQuotaDate2() != null ? c.getQuotaDate2() : c.getAccountingDate()))
                    .collect(Collectors.toList());
            for (int i = 0; i < customersPersho.size(); i++) {
                customersPersho.get(i).setPilgova(String.valueOf(i + 1));
            }
            customerService.saveAll(customersPersho);
        }

        return "redirect:/cabinet?success=true";
    }

    @GetMapping("/report")
    public String generateReport() {


        return "redirect:/cabinet?success=true";
    }

    @GetMapping("/generateAndShowRating/{garrison}")
    public String generateAndShowRating(@PathVariable String garrison,
                                        @ModelAttribute("query") SearchQuery query,
                                        Model model,
                                        @RequestParam(required = false, defaultValue = "0") int currentPage,
                                        @RequestParam(required = false, defaultValue = "50") int pageSize) {
        User user = databaseUserService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        Customer customer = new Customer();
        customer.setWork(new Work("Всі", "Всі", ""));
        customer.setQuota(new Quota("Всі", "Всі", QuotaType.NONE));
        customer.setRoomCount(0);
        customer.setFamilyCount(0);
        customer.setRegistrated(Registrated.YES);
        customer.setExperience("100");


        staticCustomers = RatingXLSWeb.getCustomers(garrison + "ZAGALNA", customer);
        //staticCustomers = customerService.findByGarrison(garrison);
        PagedListHolder<Customer> pagination = new PagedListHolder<>(staticCustomers);
        pagination.setPage(currentPage);
        pagination.setPageSize(pageSize);


        model.addAttribute("currentPage", currentPage + 1);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("customer", customer);
        model.addAttribute("workPlaces", getAllWorkPlaces(garrison));
        model.addAttribute("accountings", getAllAccountings(garrison));
        model.addAttribute("user", user);
        model.addAttribute("sort", "ZAGALNA");
        model.addAttribute("garrison", garrison);
        model.addAttribute("search", false);
        model.addAttribute("firstopen", true);
        model.addAttribute("customers", pagination);

        return "rating";
    }
}