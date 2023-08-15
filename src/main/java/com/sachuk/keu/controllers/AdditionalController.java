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
public class AdditionalController {

    private static final Logger log = Logger.getLogger(AdditionalController.class);

    public WorkService workService;
    private UserService databaseUserService;

    @ModelAttribute("works")
    public Collection<Work> getAllWorks() {
        return workService.findAll().stream()
                .sorted(Comparator.comparing(Work::getWorkPlace)).collect(Collectors.toList());
    }
    @ModelAttribute("garrisons")
    public Collection<String> getAllGarrisons() {
        return getAllWorks().stream().map(Work::getGarrison).distinct().collect(Collectors.toList());
    }
    @ModelAttribute("accountings")
    public Collection<String> getAllAccountings() {
        return getAllWorks().stream().map(Work::getAccountingPlace).distinct().collect(Collectors.toList());
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


    @RequestMapping(value = {"/additional"}, method = {RequestMethod.GET})
    public String putNewInputModel(Model modelAndView) {
        if (getUser().getRole().equals(Roles.ROLE_ADMIN)) {
            Work work = new Work();
            modelAndView.addAttribute("workToAdd", work);
            modelAndView.addAttribute("workToRename", work);
            modelAndView.addAttribute("workToChange", work);
            modelAndView.addAttribute("workToDelete", work);

            return "additional";
        }
        return "";
    }

    @Transactional
    @RequestMapping(value = "/additional/addBase", method = {RequestMethod.POST})
    public String saveBase(@ModelAttribute("workToAdd") Work work, Model model) throws IOException {
        if (getUser().getRole().equals(Roles.ROLE_ADMIN)) {

            workService.save(work);
            log.info("adding military base");
            log.info(work);
            return "redirect:/additional";
        }
        return "";
    }

    @Transactional
    @RequestMapping(value = "/additional/renameZHK", method = {RequestMethod.POST})
    public String rename(@ModelAttribute("workToRename") Work work, @ModelAttribute("works") List<Work> works, Model model) throws IOException {
        if (getUser().getRole().equals(Roles.ROLE_ADMIN)) {

            List<Work> zhkToRename = works.stream().filter(w -> w.getAccountingPlace().equals(work.getAccountingPlace())).collect(Collectors.toList());
            zhkToRename.stream().forEach(work1 -> work1.setAccountingPlace(work.getWorkPlace()));
            workService.saveAll(zhkToRename);
            log.info("renamed accounting place: " + zhkToRename);
            return "redirect:/additional";
        }
        return "";
    }

    @Transactional
    @RequestMapping(value = "/additional/changeZHK", method = {RequestMethod.POST})
    public String change(@ModelAttribute("workToChange") Work work, @ModelAttribute("works") List<Work> works, Model model) throws IOException {
        if (getUser().getRole().equals(Roles.ROLE_ADMIN)) {

            Work workToChange = works.stream().filter(w->w.getWorkPlace().equals(work.getWorkPlace())).findFirst().get();
            workToChange.setAccountingPlace(work.getAccountingPlace());
            workService.save(workToChange);

            log.info("accounting place for: "+workToChange.getWorkPlace()+" changed to: "+work.getAccountingPlace());
            return "redirect:/additional";
        }
        return "";
    }

    @Transactional
    @RequestMapping(value = "/additional/delete/{id}", method = {RequestMethod.POST})
    public String delete(@ModelAttribute("workToDelete") Work work, @ModelAttribute("works") List<Work> works, @PathVariable("id") String id, Model model) throws IOException {
        if (getUser().getRole().equals(Roles.ROLE_ADMIN)) {

            System.out.println(work);

            Work workDelete = works.stream().filter(w->w.getWorkPlace().equals(work.getWorkPlace())).findFirst().get();

            workService.delete(workDelete);
            log.info("deleted military base: "+workDelete);
            return "redirect:/additional";
        }
        return "";
    }
}