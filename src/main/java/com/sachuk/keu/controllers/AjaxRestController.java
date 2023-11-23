package com.sachuk.keu.controllers;

import com.sachuk.keu.database.service.MilitaryManService;
import com.sachuk.keu.entities.enums.QuotaType;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;


@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class AjaxRestController {

    private final MilitaryManService militaryManService;

    @RequestMapping(method = RequestMethod.GET, value = "/getArrays", produces = "application/json")
    public String getAllStats() {
        ArrayList<Long> kyiv = new ArrayList<>();
        ArrayList<Long> bori = new ArrayList<>();
        ArrayList<Long> semi = new ArrayList<>();
        ArrayList<Long> brov = new ArrayList<>();
        ArrayList<Long> pere = new ArrayList<>();
        ArrayList<Long> vasi = new ArrayList<>();
        ArrayList<Long> gost = new ArrayList<>();

        ArrayList<Long> none = new ArrayList<>();
        ArrayList<Long> persho = new ArrayList<>();
        ArrayList<Long> poza = new ArrayList<>();
        ArrayList<Long> ato = new ArrayList<>();

        kyiv.add((long) militaryManService.findByGarrison("KYIV").size());
        bori.add((long) militaryManService.findByGarrison("BORI").size());
        semi.add((long) militaryManService.findByGarrison("SEMI").size());
        brov.add((long) militaryManService.findByGarrison("BROV").size());
        pere.add((long) militaryManService.findByGarrison("PERE").size());
        vasi.add((long) militaryManService.findByGarrison("VASY").size());
        gost.add((long) militaryManService.findByGarrison("GOST").size());

        none.add(Math.round(((double) militaryManService.findAll().stream().filter(customer -> customer.getQuota().getId() == 0 && customer.getWork().getGarrison().equals("м.Київ")).count() / kyiv.get(0)) * 100));
        none.add(Math.round(((double) militaryManService.findAll().stream().filter(customer -> customer.getQuota().getId() == 0 && customer.getWork().getGarrison().equals("Бровари")).count() / brov.get(0)) * 100));
        none.add(Math.round(((double) militaryManService.findAll().stream().filter(customer -> customer.getQuota().getId() == 0 && customer.getWork().getGarrison().equals("Бориспiль")).count() / bori.get(0)) * 100));
        none.add(Math.round(((double) militaryManService.findAll().stream().filter(customer -> customer.getQuota().getId() == 0 && customer.getWork().getGarrison().equals("Переяславський")).count() / pere.get(0)) * 100));
        none.add(Math.round(((double) militaryManService.findAll().stream().filter(customer -> customer.getQuota().getId() == 0 && customer.getWork().getGarrison().equals("Гостомель")).count() / gost.get(0)) * 100));
        none.add(Math.round(((double) militaryManService.findAll().stream().filter(customer -> customer.getQuota().getId() == 0 && customer.getWork().getGarrison().equals("Василькiв")).count() / vasi.get(0)) * 100));
        none.add(Math.round(((double) militaryManService.findAll().stream().filter(customer -> customer.getQuota().getId() == 0 && customer.getWork().getGarrison().equals("Семиполки")).count() / semi.get(0)) * 100));

        persho.add(Math.round(((double) militaryManService.findAllByQuotaType(QuotaType.FIRSTINPRIORITY.getName()).stream().filter(customer -> customer.getWork().getGarrison().equals("м.Київ")).count() / kyiv.get(0)) * 100));
        persho.add(Math.round(((double) militaryManService.findAllByQuotaType(QuotaType.FIRSTINPRIORITY.getName()).stream().filter(customer -> customer.getWork().getGarrison().equals("Бровари")).count() / brov.get(0)) * 100));
        persho.add(Math.round(((double) militaryManService.findAllByQuotaType(QuotaType.FIRSTINPRIORITY.getName()).stream().filter(customer -> customer.getWork().getGarrison().equals("Бориспiль")).count() / bori.get(0)) * 100));
        persho.add(Math.round(((double) militaryManService.findAllByQuotaType(QuotaType.FIRSTINPRIORITY.getName()).stream().filter(customer -> customer.getWork().getGarrison().equals("Переяславський")).count() / pere.get(0)) * 100));
        persho.add(Math.round(((double) militaryManService.findAllByQuotaType(QuotaType.FIRSTINPRIORITY.getName()).stream().filter(customer -> customer.getWork().getGarrison().equals("Гостомель")).count() / gost.get(0)) * 100));
        persho.add(Math.round(((double) militaryManService.findAllByQuotaType(QuotaType.FIRSTINPRIORITY.getName()).stream().filter(customer -> customer.getWork().getGarrison().equals("Василькiв")).count() / vasi.get(0)) * 100));
        persho.add(Math.round(((double) militaryManService.findAllByQuotaType(QuotaType.FIRSTINPRIORITY.getName()).stream().filter(customer -> customer.getWork().getGarrison().equals("Семиполки")).count() / semi.get(0)) * 100));

        poza.add(Math.round(((double) militaryManService.findAllByQuotaType(QuotaType.OUTOFTURN.getName()).stream().filter(customer -> customer.getWork().getGarrison().equals("м.Київ")).count() / kyiv.get(0)) * 100));
        poza.add(Math.round(((double) militaryManService.findAllByQuotaType(QuotaType.OUTOFTURN.getName()).stream().filter(customer -> customer.getWork().getGarrison().equals("Бровари")).count() / brov.get(0)) * 100));
        poza.add(Math.round(((double) militaryManService.findAllByQuotaType(QuotaType.OUTOFTURN.getName()).stream().filter(customer -> customer.getWork().getGarrison().equals("Бориспiль")).count() / bori.get(0)) * 100));
        poza.add(Math.round(((double) militaryManService.findAllByQuotaType(QuotaType.OUTOFTURN.getName()).stream().filter(customer -> customer.getWork().getGarrison().equals("Переяславський")).count() / pere.get(0)) * 100));
        poza.add(Math.round(((double) militaryManService.findAllByQuotaType(QuotaType.OUTOFTURN.getName()).stream().filter(customer -> customer.getWork().getGarrison().equals("Гостомель")).count() / gost.get(0)) * 100));
        poza.add(Math.round(((double) militaryManService.findAllByQuotaType(QuotaType.OUTOFTURN.getName()).stream().filter(customer -> customer.getWork().getGarrison().equals("Василькiв")).count() / vasi.get(0)) * 100));
        poza.add(Math.round(((double) militaryManService.findAllByQuotaType(QuotaType.OUTOFTURN.getName()).stream().filter(customer -> customer.getWork().getGarrison().equals("Семиполки")).count() / semi.get(0)) * 100));

        ato.add(militaryManService.findByGarrison("KYIV").stream().filter(c -> c.getQuota().getId() == 25).count());
        ato.add(militaryManService.findByGarrison("BROV").stream().filter(c -> c.getQuota().getId() == 25).count());
        ato.add(militaryManService.findByGarrison("BORI").stream().filter(c -> c.getQuota().getId() == 25).count());
        ato.add(militaryManService.findByGarrison("PERE").stream().filter(c -> c.getQuota().getId() == 25).count());
        ato.add(militaryManService.findByGarrison("GOST").stream().filter(c -> c.getQuota().getId() == 25).count());
        ato.add(militaryManService.findByGarrison("VASY").stream().filter(c -> c.getQuota().getId() == 25).count());
        ato.add(militaryManService.findByGarrison("SEMI").stream().filter(c -> c.getQuota().getId() == 25).count());


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


}
