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
//    @ModelAttribute("balCounter")
//    public RatingCounter reitCounter() {
//        return new RatingCounter();
//    }
//    @ModelAttribute("allCustomers")
//    public List<Customer> findAllCustomers() { return customerService.findAll(); }

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
//    @RequestMapping(value = {"/rating"}, method = {RequestMethod.GET})
//    public String showRatingTable(Model modelAndView) {
//        User user = databaseUserService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
//        System.out.println(user);
//        modelAndView.addAttribute("user", user);
//        List<Enrollee> enrolleeList = enrolleeService.findAll();
//
//        enrolleeList.sort((a,b) -> (a.getForward().equals(b.getForward()))
//                ?(int)(b.getCertificate().getAverage200()*10)-(int)(a.getCertificate().getAverage200()*10)
//                :a.getForward().compareTo(b.getForward()));
//
//        modelAndView.addAttribute("enrollee", enrolleeList);
//        modelAndView.addAttribute("search",false);
//        return "table";
//
//    }
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


//    @Scheduled(fixedRate = 60000, initialDelay = 2000)
//    @Bean
//    public ArrayList<ListGroup> cachedReiting(){
//        try {
//            return reitingGenerator.takeGroups();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return new ArrayList<>();
//    }


}
