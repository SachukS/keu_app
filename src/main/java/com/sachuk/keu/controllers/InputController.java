package com.sachuk.keu.controllers;

import com.sachuk.keu.database.service.*;
import com.sachuk.keu.entities.*;
import com.sachuk.keu.entities.security.User;
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

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@Transactional
@AllArgsConstructor
public class InputController {

    private static final Logger log = Logger.getLogger(InputController.class);

//    public ZNODisciplineService znoDisciplineService;
    public QuotaService quotaService;
    public WorkService workService;
    public RankService rankService;
//    private SpecialtyService specialtyService;
//
//    private ZNOCertificateService znoCertificateService;
    public CustomerService customerService;
    private UserService databaseUserService;


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

//    @ModelAttribute("allEnrolees")
//    public List<Customer> findAllEnrolee() { return customerService.findAll(); }

    @ModelAttribute("user")
    @Transactional
    public User getUser(){ return databaseUserService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName()); }


    @ModelAttribute("quotas")
    @Transactional
    public List<Quota> getQuota(){
        return quotaService.findAll().stream()
                .sorted(Comparator.comparing(Quota::getNameQuota)).collect(Collectors.toList());
    }


    @RequestMapping(value = {"/input"}, method = {RequestMethod.GET})
    public String putNewInputModel(Model modelAndView) {

        modelAndView.addAttribute("customers", customerService.getTop());
        Customer customer = new Customer();
//        if (customer.getMilitaryDivider()==null)
//            customer.setMilitaryDivider("");
        modelAndView.addAttribute("customer", customer);

        return "input";

    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String editModel(Model modelAndView, @PathVariable("id") String id) {
        Customer customer = customerService.findById(Long.parseLong(id));
        if(Objects.equals(customer.getFlatFileNumber(), "0"))
            customer.setFlatFileNumber(null);
        System.out.println(customer);
        System.out.println("edit");
        modelAndView.addAttribute("customers", customerService.getTop());
        modelAndView.addAttribute("customer", customer);

        return "input";

    }
    @Transactional
    @RequestMapping(value = "/input", method = {RequestMethod.POST})
    public String saveInputModel(@ModelAttribute("enrollee") Customer customer, Model model) throws IOException {
        System.out.println("THIS IS HERE MOFUCKA");

        if (customer.getRankType()==null)
            customer.setRankType(customer.getRank().getRankType().getName());
        if (customer.getQuotaType()==null)
            customer.setQuotaType(customer.getQuota().getQuotaType().getName());
        if (customer.getAddress()=="")
            customer.setAddress(null);
        if (customer.getFamily()=="")
            customer.setFamily(null);
        if (customer.getInfo()=="")
            customer.setInfo(null);
        if (customer.getFlatFileNumber()=="")
            customer.setFlatFileNumber(null);
        if (customer.getServiceFrom()!=null){
            if (customer.getServiceUntill()!=null){
                customer.setExperience(String.valueOf(customer.getServiceUntill().getYear()-customer.getServiceFrom().getYear()));
            }
            else
                customer.setExperience(String.valueOf(LocalDateTime.now().getYear()-customer.getServiceFrom().getYear()));
        }

        customer.setUpdateDate(LocalDateTime.now());

        //System.out.println(customer);
//        if(customer.getLicey()==null){
//            customer.setLicey(Lyceum.SCHOOL);
//        }
//        if(customer.getForward()==null){
//            customer.setForward(Forward.INSTITUTE);
//        }
//        if (customer.getTakenAwayDocs()==null)
//            customer.setTakenAwayDocs(TakenAwayDocs.NONE);
//
//        if (customer.getEntered()==null)
//            customer.setEntered(Entered.NONE);
//
//        if (customer.getChecked()==null)
//            customer.setChecked(Checked.NONE);
//
//        if (customer.getMilitaryDivider()==null)
//            customer.setMilitaryDivider("Не визначено");
//
//        //this code added evaluation for Motivation letter as subject zno
////        ZNOEvaluation znoEval = new ZNOEvaluation();
////        znoEval.setZnoDiscipline(getDiscipline().get(getDiscipline().size()-1));
////        znoEval.setEvaluation((double)enrollee.getMotivationMark());
////        znoEval.setZnoCertificate(enrollee.getZnoCertificates().get(0));
////        enrollee.getZnoCertificates().get(0).getZnoEvaluations().add(znoEval);
//
//        Customer saved = customerService.setAllFieldsEnrollee(customer);
//        try { saved.setCreateDateTime(customerService.findById(customer.getId()).getCreateDateTime()); }
//        catch (Exception e){ e.printStackTrace(); }
//
        //customerService.save(customer);
        System.out.println(customer);

        return "redirect:/cabinet?success=true";
    }

//    @RequestMapping(value = "/input", params = {"removePrivilege"}, method = {RequestMethod.POST})
//    public String removePrivilege(
//            final @ModelAttribute("enrollee") Enrollee enrollee, final BindingResult bindingResult,
//            final HttpServletRequest req) {
//        final Integer rowId = Integer.valueOf(req.getParameter("removePrivilege"));
//        enrollee.getPrivileges().remove(rowId.intValue());
//        return "input";
//    }
//
//    @RequestMapping(value = "/input", params = {"addPrivilege"}, method = {RequestMethod.POST})
//    public String addPrivilege(@ModelAttribute("enrollee") Enrollee enrollee, final BindingResult bindingResult, Model model) throws IOException {
//
//        Privilege privilege = new Privilege();
//        privilege.setEnrollee(enrollee);
//        enrollee.getPrivileges().add(privilege);
//
//        return "input";
//    }
//
//    @RequestMapping(value = "/input", params = {"addZNO"}, method = {RequestMethod.POST})
//    public String addZNO(@ModelAttribute("enrollee") Enrollee enrollee, final BindingResult bindingResult, Model model) throws IOException {
//
//        ZNOCertificate certificate = znoCertificateService.createZnoCertificate(enrollee);
//        System.out.println(enrollee);
//        System.out.println("********");
//        certificate.setEnrollee(enrollee);
//        enrollee.getZnoCertificates().add(certificate);
//
//        return "input";
//    }
//
//    @RequestMapping(value = "/input", params = {"removeZNO"}, method = {RequestMethod.POST})
//    public String removeZNO(
//            final @ModelAttribute("enrollee") Enrollee enrollee, final BindingResult bindingResult,
//            final HttpServletRequest req) {
//        final Integer rowId = Integer.valueOf(req.getParameter("removeZNO"));
//        ZNOCertificate removeCertificate = enrollee.getZnoCertificates().get(rowId);
//        enrollee.getZnoCertificates().remove(removeCertificate);
//        return "input";
//    }
//
//    @RequestMapping(value = "/input", params = {"addSubject"}, method = {RequestMethod.POST})
//    public String addSubject(
//            final @ModelAttribute("enrollee") Enrollee enrollee, final BindingResult bindingResult,
//            final HttpServletRequest req) {
//        final Integer rowId = Integer.valueOf(req.getParameter("addSubject"));
//        System.out.println(enrollee);
//        ZNOEvaluation znoEval = new ZNOEvaluation();
//        znoEval.setZnoCertificate(enrollee.getZnoCertificates().get(rowId));
//        enrollee.getZnoCertificates().get(rowId).getZnoEvaluations().add(znoEval);
//
//        return "input";
//    }
//
//    @RequestMapping(value = "/input", params = {"removeSubject"}, method = {RequestMethod.POST})
//    public String removeSubject(
//            final @ModelAttribute("enrollee") Enrollee enrollee, final BindingResult bindingResult,
//            final HttpServletRequest req) {
//
//        final Integer certificateNum = Integer.valueOf(req.getParameter("removeSubject").split(" ")[0]);
//        final Integer subjectNum = Integer.valueOf(req.getParameter("removeSubject").split(" ")[1]);
//        enrollee.getZnoCertificates().get(certificateNum).getZnoEvaluations().remove(subjectNum.intValue());
//
//        return "input";
//    }
//
//    @RequestMapping(value = "/input", params = {"addPriority"}, method = {RequestMethod.POST})
//    public String addPriority(@ModelAttribute("enrollee") Enrollee enrollee, final BindingResult bindingResult, Model model) throws IOException {
//        if(enrollee.getSpecialties().size()==0){
//            for(int i=0;i<5;i++){
//                EnrolleeSpecialty specialty = new EnrolleeSpecialty(enrollee);
//                specialty.setPriority((byte)(enrollee.getSpecialties().size() + 1));
//                enrollee.getSpecialties().add(specialty);
//            }
//            System.out.println(enrollee);
//            System.out.println("********");
//        }else {
//            EnrolleeSpecialty specialty = new EnrolleeSpecialty(enrollee);
//            specialty.setPriority((byte)(enrollee.getSpecialties().size() + 1));
//            System.out.println(enrollee);
//            System.out.println("********");
//            enrollee.getSpecialties().add(specialty);
//        }
//
//        return "input";
//    }
//    @RequestMapping(value = "/input", params = {"removePriority"}, method = {RequestMethod.POST})
//    public String removePriority(
//            final @ModelAttribute("enrollee") Enrollee enrollee, final BindingResult bindingResult,
//            final HttpServletRequest req) {
//        final Integer rowId = Integer.valueOf(req.getParameter("removePriority"));
//        enrollee.getSpecialties().remove(rowId.intValue());
//        return "input";
//    }

}