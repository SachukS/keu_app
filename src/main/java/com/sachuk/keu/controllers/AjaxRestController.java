package com.sachuk.keu.controllers;

import com.sachuk.keu.database.service.CustomerService;
import com.sachuk.keu.entities.enums.QuotaType;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class AjaxRestController {

    private final CustomerService customerService;

//    @GetMapping("/getIsLoaded")
//    public LoadedDto getIsLoaded(){
//        return RatingService.loadedDto;
//    }

//    @RequestMapping(method = RequestMethod.GET, value = "/getArrayBy/{var}", produces = "application/json")
//    public String getStats(@PathVariable("var") String var) {
//        ArrayList<Long> result = new ArrayList<>();
//        for (int i = 6; i >= 0; i--) {
//            switch (var) {
//                case "mil": {
//                    result.add(enrolleeService.countBetweenByStatus(LocalDate.now().minusDays(i).atStartOfDay(), LocalDate.now().minusDays(i).atTime(23, 59), SocialStatus.MILITARY));
//                    break;
//                }
//                case "civil": {
//                    result.add(enrolleeService.findBetweenDates(LocalDate.now().minusDays(i).atStartOfDay(), LocalDate.now().minusDays(i).atTime(23, 59)).stream().filter(s -> s.getForward().equals(Forward.INSTITUTE) && s.getSocialStatus().equals(SocialStatus.CIVIL)).count());
//                    break;
//                }
//                case "lyceum": {
//                    result.add(enrolleeService.findBetweenDates(LocalDate.now().minusDays(i).atStartOfDay(), LocalDate.now().minusDays(i).atTime(23, 59)).stream().filter(s -> s.getLyceum().equals(Lyceum.LYCEUM)).count());
//                    break;
//                }
//
//
//                case "colege": {
//                    result.add(enrolleeService.findAll().stream().filter(s -> s.getForward().equals(Forward.COLLEGE)).count());
//                    i = -1;
//                    break;
//                }
//                case "miti": {
//                    result.add(enrolleeService.findAll().stream().filter(s -> s.getForward().equals(Forward.INSTITUTE)).count());
//                    i = -1;
//                    break;
//                }
//                case "mitiMale": {
//                    result.add(enrolleeService.findAll().stream().filter(s -> s.getForward().equals(Forward.INSTITUTE) && s.getGender().equals(Gender.MALE)).count());
//                    i = -1;
//                    break;
//                }
//                case "mitiFemale": {
//                    result.add(enrolleeService.findAll().stream().filter(s -> s.getForward().equals(Forward.INSTITUTE) && s.getGender().equals(Gender.FEMALE)).count());
//                    i = -1;
//                    break;
//                }
//                case "vkssMale": {
//                    result.add(enrolleeService.findAll().stream().filter(s -> s.getForward().equals(Forward.COLLEGE) && s.getGender().equals(Gender.MALE)).count());
//                    i = -1;
//                    break;
//                }
//                case "vkssFemale": {
//                    result.add(enrolleeService.findAll().stream().filter(s -> s.getForward().equals(Forward.COLLEGE) && s.getGender().equals(Gender.FEMALE)).count());
//                    i = -1;
//                    break;
//                }
//                default:
//                    return "";
//            }
//
//        }
//        return new JSONObject().put("message", result).toString();
//    }

    @RequestMapping(method = RequestMethod.GET, value = "/getArrays", produces = "application/json")
    public String getAllStats() {
        ArrayList<Long> kyiv           = new ArrayList<>();
        ArrayList<Long> bori           = new ArrayList<>();
        ArrayList<Long> semi           = new ArrayList<>();
        ArrayList<Long> brov           = new ArrayList<>();
        ArrayList<Long> pere           = new ArrayList<>();
        ArrayList<Long> vasi           = new ArrayList<>();
        ArrayList<Long> gost           = new ArrayList<>();

        ArrayList<Long> none           = new ArrayList<>();
        ArrayList<Long> persho           = new ArrayList<>();
        ArrayList<Long> poza           = new ArrayList<>();
        ArrayList<Long> ato           = new ArrayList<>();

        kyiv.add((long) customerService.findByGarrison("KYIV").size());
        bori.add((long) customerService.findByGarrison("BORI").size());
        semi.add((long) customerService.findByGarrison("SEMI").size());
        brov.add((long) customerService.findByGarrison("BROV").size());
        pere.add((long) customerService.findByGarrison("PERE").size());
        vasi.add((long) customerService.findByGarrison("VASY").size());
        gost.add((long) customerService.findByGarrison("GOST").size());

        none.add( Math.round(((double) customerService.findAll().stream().filter(customer -> customer.getQuota().getId()==0 && customer.getWork().getGarrison().equals("м.Київ")).count() / kyiv.get(0)) * 100));
        none.add( Math.round(((double) customerService.findAll().stream().filter(customer -> customer.getQuota().getId()==0 && customer.getWork().getGarrison().equals("Бровари")).count() / brov.get(0)) * 100));
        none.add( Math.round(((double) customerService.findAll().stream().filter(customer -> customer.getQuota().getId()==0 && customer.getWork().getGarrison().equals("Бориспiль")).count() / bori.get(0)) * 100));
        none.add( Math.round(((double) customerService.findAll().stream().filter(customer -> customer.getQuota().getId()==0 && customer.getWork().getGarrison().equals("Переяславський")).count() / pere.get(0)) * 100));
        none.add( Math.round(((double) customerService.findAll().stream().filter(customer -> customer.getQuota().getId()==0 && customer.getWork().getGarrison().equals("Гостомель")).count() / gost.get(0)) * 100));
        none.add( Math.round(((double) customerService.findAll().stream().filter(customer -> customer.getQuota().getId()==0 && customer.getWork().getGarrison().equals("Василькiв")).count() / vasi.get(0)) * 100));
        none.add( Math.round(((double) customerService.findAll().stream().filter(customer -> customer.getQuota().getId()==0 && customer.getWork().getGarrison().equals("Семиполки")).count() / semi.get(0)) * 100));

        System.out.println(none.toString());

        persho.add( Math.round(((double) customerService.findAllByQuotaType(QuotaType.FIRSTINPRIORITY.getName()).stream().filter(customer -> customer.getWork().getGarrison().equals("м.Київ")).count() / kyiv.get(0)) * 100));
        persho.add( Math.round(((double) customerService.findAllByQuotaType(QuotaType.FIRSTINPRIORITY.getName()).stream().filter(customer -> customer.getWork().getGarrison().equals("Бровари")).count() / brov.get(0)) * 100));
        persho.add( Math.round(((double) customerService.findAllByQuotaType(QuotaType.FIRSTINPRIORITY.getName()).stream().filter(customer -> customer.getWork().getGarrison().equals("Бориспiль")).count() / bori.get(0)) * 100));
        persho.add( Math.round(((double) customerService.findAllByQuotaType(QuotaType.FIRSTINPRIORITY.getName()).stream().filter(customer -> customer.getWork().getGarrison().equals("Переяславський")).count() / pere.get(0)) * 100));
        persho.add( Math.round(((double) customerService.findAllByQuotaType(QuotaType.FIRSTINPRIORITY.getName()).stream().filter(customer -> customer.getWork().getGarrison().equals("Гостомель")).count() / gost.get(0)) * 100));
        persho.add( Math.round(((double) customerService.findAllByQuotaType(QuotaType.FIRSTINPRIORITY.getName()).stream().filter(customer -> customer.getWork().getGarrison().equals("Василькiв")).count() / vasi.get(0)) * 100));
        persho.add( Math.round(((double) customerService.findAllByQuotaType(QuotaType.FIRSTINPRIORITY.getName()).stream().filter(customer -> customer.getWork().getGarrison().equals("Семиполки")).count() / semi.get(0)) * 100));

        poza.add( Math.round(((double) customerService.findAllByQuotaType(QuotaType.OUTOFTURN.getName()).stream().filter(customer -> customer.getWork().getGarrison().equals("м.Київ")).count() / kyiv.get(0)) * 100));
        poza.add( Math.round(((double) customerService.findAllByQuotaType(QuotaType.OUTOFTURN.getName()).stream().filter(customer -> customer.getWork().getGarrison().equals("Бровари")).count() / brov.get(0)) * 100));
        poza.add( Math.round(((double) customerService.findAllByQuotaType(QuotaType.OUTOFTURN.getName()).stream().filter(customer -> customer.getWork().getGarrison().equals("Бориспiль")).count() / bori.get(0)) * 100));
        poza.add( Math.round(((double) customerService.findAllByQuotaType(QuotaType.OUTOFTURN.getName()).stream().filter(customer -> customer.getWork().getGarrison().equals("Переяславський")).count() / pere.get(0)) * 100));
        poza.add( Math.round(((double) customerService.findAllByQuotaType(QuotaType.OUTOFTURN.getName()).stream().filter(customer -> customer.getWork().getGarrison().equals("Гостомель")).count() / gost.get(0)) * 100));
        poza.add( Math.round(((double) customerService.findAllByQuotaType(QuotaType.OUTOFTURN.getName()).stream().filter(customer -> customer.getWork().getGarrison().equals("Василькiв")).count() / vasi.get(0)) * 100));
        poza.add( Math.round(((double) customerService.findAllByQuotaType(QuotaType.OUTOFTURN.getName()).stream().filter(customer -> customer.getWork().getGarrison().equals("Семиполки")).count() / semi.get(0)) * 100));

        ato.add( customerService.findByGarrison("KYIV").stream().filter(c->c.getQuota2() != null && c.getQuota2().getId()==25).count());
        ato.add( customerService.findByGarrison("BROV").stream().filter(c->c.getQuota2() != null && c.getQuota2().getId()==25).count());
        ato.add( customerService.findByGarrison("BORI").stream().filter(c->c.getQuota2() != null && c.getQuota2().getId()==25).count());
        ato.add( customerService.findByGarrison("PERE").stream().filter(c->c.getQuota2() != null && c.getQuota2().getId()==25).count());
        ato.add( customerService.findByGarrison("GOST").stream().filter(c->c.getQuota2() != null && c.getQuota2().getId()==25).count());
        ato.add( customerService.findByGarrison("VASY").stream().filter(c->c.getQuota2() != null && c.getQuota2().getId()==25).count());
        ato.add( customerService.findByGarrison("SEMI").stream().filter(c->c.getQuota2() != null && c.getQuota2().getId()==25).count());


        return new JSONObject()
                .put("kyiv", kyiv)
                .put("bori", bori)
                .put("semi", semi)
                .put("brov", brov)
                .put("pere", pere)
                .put("vasi", vasi)
                .put("gost", gost)
                .put("none", none)
                .put("persho", persho)
                .put("poza", poza)
                .put("ato", ato).toString();
    }


//    @RequestMapping(method = RequestMethod.GET, value = "/getValuesFor/{var}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<Long> getTodayStats(@PathVariable String var) {
//        int startHour = 9;
//        int finishHour = 21;
//        int weekDays = 7;
//        ArrayList<Long> values = new ArrayList<>();
//        if ("week".equals(var)) {
//            for (int i = 0; i < weekDays; i++)
//                values.add(
//                        (long) enrolleeService.findBetweenDates(LocalDateTime.now().minusDays(i).toLocalDate().atStartOfDay(),
//                                        LocalDateTime.now().minusDays(i).toLocalDate().atTime(23, 59))
//                                .size()
//                );
//            return values;
//        }
//        for (int i = startHour; i < finishHour; i++) {
//            switch (var) {
//                case "allReq":
//                    values.add(enrolleeService.countBetween(LocalDateTime.now().withHour(i).withMinute(0), LocalDateTime.now().withHour(i + 1).withMinute(0)));
//                    break;
//                case "militaries":
//                    values.add(enrolleeService.findBetweenDates(LocalDateTime.now().withHour(i).withMinute(0), LocalDateTime.now().withHour(i + 1).withMinute(0))
//                            .stream()
//                            .filter(enrollee -> enrollee.getSocialStatus() == SocialStatus.MILITARY)
//                            .count());
//                    break;
//            }
//        }
//        return values;
//    }


}
