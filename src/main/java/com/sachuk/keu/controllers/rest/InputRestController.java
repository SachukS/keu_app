package com.sachuk.keu.controllers.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import com.sachuk.keu.database.service.*;
import com.sachuk.keu.entities.*;
import com.sachuk.keu.entities.enums.CurrencyType;
import com.sachuk.keu.entities.enums.Provided;
import com.sachuk.keu.entities.enums.SexEnum;
import com.sachuk.keu.entities.payload.InputPayload;
import com.sachuk.keu.services.notification.MessengerNotificationService;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// for admin //
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/input")
@CrossOrigin(origins = "*", maxAge = 3600)
public class InputRestController {
    public MilitaryManService militaryManService;
    public UserService userService;
    public RankService rankService;
    public WorkService workService;
    public FamilyMemberService familyMemberService;
    public RegistryService registryService;
    public MessengerNotificationService messengerNotificationService;
    public QuotaService quotaService;

    @GetMapping("/{id}")
    public MilitaryMan edit(@PathVariable("id") Long id) {
        return militaryManService.findById(id).orElseThrow(
                () -> new IllegalArgumentException("military man with id: " + id + " is not found"));
    }

    public LocalDateTime todt (String t) {
        try {
            if (t != null && !t.equals("")) {
                ZonedDateTime zonedDateTime = ZonedDateTime.parse(t);
                return zonedDateTime.toLocalDateTime();
            }
        } catch (DateTimeParseException e) {
            System.err.println("Error parsing date: " + e.getMessage());
        }
        return null;
    }
    public long getid(Object str) {
        if (str instanceof Integer) {
            // Convert Integer to Long directly
            return ((Integer) str).longValue();
        }
        return -1;
    }
    public double getdouble(Object str) {
        if (str instanceof Double) {
            // Convert Integer to Long directly
            return ((Double) str).doubleValue();
        }
        return -1;
    }
    @PostMapping("/")
    public MilitaryMan saveMilitaryMan(@RequestBody Map<String, Object> inputPayload) {
//        System.out.println(inputPayload.get("inForces"));
//        for (Map.Entry<String, Object> entry : inputPayload.entrySet()) {
//            String key = entry.getKey();
//            Object value = entry.getValue();
//            System.out.println("KEY: "+key+"    Value: "+value);
//
//        }
        List<FamilyMember> familyMembers = new ArrayList<>();
        Object familyObj = inputPayload.get("family");
        if (familyObj instanceof List) {
            List<?> familyList = (List<?>) familyObj;

            for (Object memberObj : familyList) {
                if (memberObj instanceof Map) {
                    Map<?, ?> memberMap = (Map<?, ?>) memberObj;

                    String memberName = (String) memberMap.get("memberName");
                    String memberSurname = (String) memberMap.get("memberSurname");
                    String memberThirdName = (String) memberMap.get("memberThirdName");
                    String memberSex = (String) memberMap.get("memberSex");
                    String memberBirthDate = (String) memberMap.get("memberBirthDate");


                    FamilyMember fm = new FamilyMember(memberSurname,memberName, memberThirdName, todt(memberBirthDate), SexEnum.MALE);
                    FamilyMember fmp = familyMemberService.save(fm);
                    familyMembers.add(fmp);


                }
            }
        }


        Rank rank = rankService.findById(getid(inputPayload.get("rankId")));
        Work work = workService.findById(getid(inputPayload.get("serviceId")));
        Quota quota = quotaService.findById(getid(inputPayload.get("benefitsId")));


        MilitaryMan militaryMan = new MilitaryMan();
        militaryMan.setSurname((String) inputPayload.get("surname"));
        militaryMan.setName((String) inputPayload.get("name"));
        militaryMan.setThirdName((String) inputPayload.get("thirdName"));
        militaryMan.setPhoneNumber("+38"+inputPayload.get("phoneNumber"));
        militaryMan.setIpn((String) inputPayload.get("IPN"));
        militaryMan.setApartmentFileNumber((String) inputPayload.get("apartmentFileNumber"));
        militaryMan.setApartmentFileDate(todt((String) inputPayload.get("apartmentFileDate")));
        militaryMan.setServiceFrom(todt((String) inputPayload.get("inForces")));
        militaryMan.setServiceUntil(todt((String) inputPayload.get("leaveFromForces")));
        militaryMan.setRegistrated(true);
        militaryMan.setProvided(Provided.valueOf((String) inputPayload.get("housingProvision")));
        militaryMan.setRoomCount(Integer.parseInt((String) inputPayload.get("countRooms")));
        militaryMan.setRozshirennya(inputPayload.get("requiresAdditionalHousing").equals("true"));
        militaryMan.setFamilyWar2022(inputPayload.get("familyWar").equals("true"));
        militaryMan.setAccountingDate(todt((String) inputPayload.get("housingRegistrationDate")));
        militaryMan.setFamilyMembers(familyMembers);
        militaryMan.setRank(rank);
        militaryMan.setWork(work);
        militaryMan.setQuota(quota);
        if (quota.getId()!=1) {
            militaryMan.setQuotaDate(todt((String) inputPayload.get("benefitsRegistrationDate")));
            militaryMan.setQuotaQueue(15);
        }

        System.out.println(militaryMan);
        MilitaryMan savedMM = militaryManService.save(militaryMan);

        if(savedMM.getProvided().equals(Provided.POST) || savedMM.getProvided().equals(Provided.COMP)){
            Registry registry = new Registry();
            if (savedMM.getProvided().equals(Provided.POST)) {
                ProvidedFlat flat = new ProvidedFlat();
                flat.setUnservicedApartment(false);
                flat.setCost(2000000);
                flat.setSquare(34);
                flat.setRoomCount(2);
                registry.setProvidedFlat(flat);
                registry.setReceivedMoney(0);
            }
            if (savedMM.getProvided().equals(Provided.COMP)) {
                registry.setReceivedMoney(20000);
            }
            registry.setFlatFileNumber(savedMM.getApartmentFileNumber());
            registry.setMilitaryMan(savedMM);
            registry.setReceiveDate(LocalDateTime.now());
            registry.setFinanceSource(new FinanceSource("Державний бюджет",1000000000, CurrencyType.UAH));
            registryService.save(registry);
        }

//        Gson json = new Gson();
//        MilitaryMan mm = json.fromJson(inputPayload, MilitaryMan.class);
//        System.out.println(mm.toString());
        //MilitaryMan mm = militaryManService.save(inputPayload.getMilitaryMan());
//        if (inputPayload.getRegistry() != null) {
//            Registry registry = inputPayload.getRegistry();
//            registry.setMilitaryMan(mm);
//            registry.setFlatFileNumber(mm.getApartmentFileNumber());
//            registryService.save(registry);
//            messengerNotificationService.sendNotification("+380932072704", "Вітаємо, раді повідомити вам");
//        }
        return null;
    }

    public void extractData(Map<String, Object> formData) {
        for (Map.Entry<String, Object> entry : formData.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            System.out.println("KEY: " + key + "    Value: " + value);

            // Check if the value is a Map or List and handle accordingly
            if (value instanceof Map) {
                // Recursive call for nested Map
                extractData((Map<String, Object>) value);
            } else if (value instanceof List) {
                // Handle List
                extractListData((List<?>) value);
            }
        }
    }

    private void extractListData(List<?> list) {
        for (Object item : list) {
            if (item instanceof Map) {
                // Recursive call for nested Map within a List
                extractData((Map<String, Object>) item);
            } else {
                System.out.println("List item: " + item);
            }
        }
    }
}
