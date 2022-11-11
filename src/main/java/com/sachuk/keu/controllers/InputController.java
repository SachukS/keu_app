package com.sachuk.keu.controllers;

import com.sachuk.keu.database.service.*;
import com.sachuk.keu.entities.*;
import com.sachuk.keu.entities.enums.FamilyWar2022;
import com.sachuk.keu.entities.enums.Provided;
import com.sachuk.keu.entities.enums.QuotaType;
import com.sachuk.keu.entities.enums.Registrated;
import com.sachuk.keu.entities.security.Roles;
import com.sachuk.keu.entities.security.User;
import com.sachuk.keu.services.rating.RatingXLSWeb;
import com.sachuk.keu.services.rating.RatingXlsCreateService;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@Transactional
@AllArgsConstructor
public class InputController {

    private static final Logger log = Logger.getLogger(InputController.class);

    public QuotaService quotaService;
    public WorkService workService;
    public RankService rankService;
    public CustomerService customerService;
    private UserService databaseUserService;

    private RatingXlsCreateService ratingXlsCreateService;


    @ModelAttribute("ranks")
    public Collection<Rank> getAllRanks() {
        return rankService.findAll().stream()
                .sorted(Comparator.comparing(Rank::getNameRank)).collect(Collectors.toList());
    }

    @ModelAttribute("works")
    public Collection<Work> getAllWorks() {
        return workService.findAll().stream()
                .sorted(Comparator.comparing(Work::getWorkPlace)).collect(Collectors.toList());
    }

    @ModelAttribute("query")
    public SearchQuery createQuery() {
        return new SearchQuery();
    }


    @ModelAttribute("user")
    @Transactional
    public User getUser() {
        return databaseUserService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }


    @ModelAttribute("quotas")
    @Transactional
    public List<Quota> getQuota() {
        return quotaService.findAll().stream()
                .sorted(Comparator.comparing(Quota::getId)).collect(Collectors.toList());
    }


    @RequestMapping(value = {"/input"}, method = {RequestMethod.GET})
    public String putNewInputModel(Model modelAndView) {
        if (getUser().getRole().equals(Roles.ROLE_ADMIN)) {
            modelAndView.addAttribute("customers", customerService.getTop());
            Customer customer = new Customer();
            modelAndView.addAttribute("customer", customer);

            return "input";
        }
        return "";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String editModel(Model modelAndView, @PathVariable("id") String id) {
        Customer customer = customerService.findById(Long.parseLong(id));
        if (Objects.equals(customer.getFlatFileNumber(), "0"))
            customer.setFlatFileNumber(null);
        if (Objects.equals(customer.getFamilyWar2022(), null))
            customer.setFamilyWar2022(FamilyWar2022.NO);
        if (Objects.equals(customer.getProvided(), null))
            customer.setProvided(Provided.NO);
        System.out.println(customer);
        System.out.println("edit");
        modelAndView.addAttribute("customers", customerService.getTop());
        modelAndView.addAttribute("customer", customer);

        return "input";
    }

    @Transactional
    @RequestMapping(value = "/input", method = {RequestMethod.POST})
    public String saveInputModel(@ModelAttribute("customer") Customer customer, Model model) throws IOException {
        if (getUser().getRole().equals(Roles.ROLE_ADMIN)) {
            System.out.println("THIS IS HERE MOFUCKA");

            if (customer.getRank().getId() == 0L)
                customer.setRank(getAllRanks().stream().filter(r -> Objects.equals(r.getNameRank(), customer.getRank().getNameRank())).findFirst().get());
            if (customer.getWork().getId() == 0L)
                customer.setWork(getAllWorks().stream().filter(w -> Objects.equals(w.getWorkPlace(), customer.getWork().getWorkPlace())).findFirst().get());
            if (customer.getRankType() == null)
                customer.setRankType(customer.getRank().getRankType().getName());
            if (customer.getQuotaType() == null)
                customer.setQuotaType(customer.getQuota().getQuotaType().getName());
            if (customer.getQuotaType2() == null)
                customer.setQuotaType2(customer.getQuota2().getQuotaType().getName());
            if (customer.getAddress() == "")
                customer.setAddress(null);
            if (customer.getFamily() == "")
                customer.setFamily(null);
            if (customer.getInfo() == "")
                customer.setInfo(null);
            if (customer.getFlatFileNumber() == "")
                customer.setFlatFileNumber(null);
            if (customer.getServiceFrom() != null) {
                if (customer.getServiceUntill() != null) {
                    customer.setExperience(String.valueOf(customer.getServiceUntill().getYear() - customer.getServiceFrom().getYear()));
                } else
                    customer.setExperience(String.valueOf(LocalDateTime.now().getYear() - customer.getServiceFrom().getYear() - 1900));
            }

            customer.setUpdateDate(LocalDateTime.now());
            customerService.save(customer);
            System.out.println(customer);

            return "redirect:/cabinet?success=true";
        }
        return "";
    }

    @RequestMapping(value = "/input/print", method = {RequestMethod.POST})
    public void print(@ModelAttribute("customer") Customer customer, HttpServletResponse response, Model model) throws IOException {
        System.out.println("print Dovidka");
        System.out.println(customer);
        if (customer.getRank().getId() == 0L)
            customer.setRank(getAllRanks().stream().filter(r -> Objects.equals(r.getNameRank(), customer.getRank().getNameRank())).findFirst().get());
        if (customer.getWork().getId() == 0L)
            customer.setWork(getAllWorks().stream().filter(w -> Objects.equals(w.getWorkPlace(), customer.getWork().getWorkPlace())).findFirst().get());
        if (customer.getRankType() == null)
            customer.setRankType(customer.getRank().getRankType().getName());
        if (customer.getQuotaType() == null)
            customer.setQuotaType(customer.getQuota().getQuotaType().getName());
        if (customer.getQuotaType2() == null)
            customer.setQuotaType2(customer.getQuota2().getQuotaType().getName());
        if (customer.getAddress() == "")
            customer.setAddress(null);
        if (customer.getFamily() == "")
            customer.setFamily(null);
        if (customer.getInfo() == "")
            customer.setInfo(null);
        if (customer.getFlatFileNumber() == "")
            customer.setFlatFileNumber(null);
        if (customer.getServiceFrom() != null) {
            if (customer.getServiceUntill() != null) {
                customer.setExperience(String.valueOf(customer.getServiceUntill().getYear() - customer.getServiceFrom().getYear()));
            } else
                customer.setExperience(String.valueOf(LocalDateTime.now().getYear() - customer.getServiceFrom().getYear() - 1900));
        }

        String garrison = customer.getWork().getGarrison();
        switch (garrison) {
            case "м.Київ":
                garrison = "KYIV";
                break;
            case "Бориспiль":
                garrison = "BORI";
                break;
            case "Семиполки":
                garrison = "SEMI";
                break;
            case "Переяславський":
                garrison = "PERE";
                break;
            case "Бровари":
                garrison = "BROV";
                break;
            case "Гостомель":
                garrison = "GOST";
                break;
            case "Василькiв":
                garrison = "VASY";
                break;
        }
        String sort = "";
        switch (customer.getQuotaType()) {
            case "без пiльг":
                sort = "ZAGALNA";
                break;
            case "позачерговий":
                sort = "POZA";
                break;
            case "першочерговий":
                sort = "PERSHO";
                break;
        }

        Customer customer1 = new Customer();
        customer1.setWork(new Work("Всі", "Всі", "BORI"));
        customer1.setQuota(new Quota("Всі", "Всі", QuotaType.NONE));
        customer1.setRoomCount(0);
        customer1.setFamilyCount(0);
        customer1.setRegistrated(Registrated.YES);
        customer1.setExperience("100");

        ratingXlsCreateService.createXls(System.getProperty("user.home") + File.separator + "DOVIDKA.xls", RatingXLSWeb.getCustomers(garrison + sort, customer1), customer);

        Path file = Paths.get(System.getProperty("user.home") + File.separator + "DOVIDKA.xls");

        response.setHeader("Content-disposition", "attachment; filename=DOVIDKA.xls");
        try {
            response.getOutputStream().write(Files.readAllBytes(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}