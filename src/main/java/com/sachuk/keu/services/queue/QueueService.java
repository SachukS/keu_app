package com.sachuk.keu.services.queue;

import com.sachuk.keu.database.service.MilitaryManService;
import com.sachuk.keu.entities.MilitaryMan;
import com.sachuk.keu.entities.Quota;
import com.sachuk.keu.entities.Registry;
import com.sachuk.keu.entities.enums.QuotaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QueueService {
    private static MilitaryManService militaryManService;
    private static QueueXlsCreateService queueXlsCreateService;

    @Autowired
    public void setRatingXlsCreateService(QueueXlsCreateService queueXlsCreateService) {
        this.queueXlsCreateService = queueXlsCreateService;
    }

    @Autowired
    public void setCustomerService(MilitaryManService militaryManService) {
        this.militaryManService = militaryManService;
    }

    public static List<MilitaryMan> getQueue(String garrison, String queueType) {
        List<MilitaryMan> queueInGarrison = new ArrayList<>();
        switch (queueType) {
            case "general":
                queueInGarrison = militaryManService.findByGarrison(garrison)
                        .stream().sorted(
                                Comparator.comparing(MilitaryMan::getAccountingDate).reversed()
                                        .thenComparing(militaryMan -> militaryMan.getQuota().getQuotaDate()).reversed())
                        .collect(Collectors.toList());
                break;
            case "firstinpriority":
            case "outofqueue":
                queueInGarrison = militaryManService.findQueueTypeByGarrison(garrison, queueType.toUpperCase())
                        .stream().sorted(
                                Comparator.comparing(MilitaryMan::getQuotaQueue))
                        .collect(Collectors.toList());
            case "compensation":
                queueInGarrison = militaryManService.findByGarrison(garrison)
                        .stream().filter(MilitaryMan::isCompensation).sorted(
                                Comparator.comparing(MilitaryMan::getAccountingDate).reversed()
                                        .thenComparing(militaryMan -> militaryMan.getQuota().getQuotaDate()).reversed())
                        .collect(Collectors.toList());
                break;
        }
        return queueInGarrison;
    }
    public static List<Registry> getReceivedQueue(String garrison, String queueType) {
        return new ArrayList<>();
    }
//        if (!militaryMan.getWork().getAccountingPlace().equals("Всі")) {
//            customersInGarrison = customersInGarrison.stream()
//                    .filter(c -> c.getWork().getAccountingPlace().equals(militaryMan.getWork().getAccountingPlace()))
//                    .collect(Collectors.toList());
//        }
//        if (!militaryMan.getWork().getWorkPlace().equals("Всі")) {
//            customersInGarrison = customersInGarrison.stream()
//                    .filter(c -> c.getWork().getWorkPlace().equals(militaryMan.getWork().getWorkPlace()))
//                    .collect(Collectors.toList());
//        }
//        if (!militaryMan.getQuota().equals(new Quota("Всі", "Всі", QuotaType.NONE))) {
//            customersInGarrison = customersInGarrison.stream()
//                    .filter(c -> c.getQuota().equals(militaryMan.getQuota()))
//                    .collect(Collectors.toList());
//        }
//        if (militaryMan.getFamilyCount() != 0) {
//            customersInGarrison = customersInGarrison.stream()
//                    .filter(c -> c.getFamilyCount() == militaryMan.getFamilyCount())
//                    .collect(Collectors.toList());
//        }
//        if (militaryMan.getRoomCount() != 0) {
//            customersInGarrison = customersInGarrison.stream()
//                    .filter(c -> c.getRoomCount() == militaryMan.getRoomCount())
//                    .collect(Collectors.toList());
//        }
//        if (!militaryMan.getRegistrated().equals(Registrated.ALL)) {
//            customersInGarrison = customersInGarrison.stream()
//                    .filter(c -> c.getRegistrated().equals(militaryMan.getRegistrated()))
//                    .collect(Collectors.toList());
//        }

        //String sortBy = nameSort.substring(4);

//        switch (queueType) {
//            case "ZAGALNA":
//                List<MilitaryMan> customersWithExp = customersInGarrison.stream().filter(c -> c.getExperience() != null && !c.getExperience().equals("0")).collect(Collectors.toList());
//                List<MilitaryMan> customersWithoutExp = new ArrayList<>(customersInGarrison);
//                customersWithoutExp.removeAll(customersWithExp);
//
//                if (!militaryMan.getExperience().equals("100")) {
//                    customersInGarrison = customersWithExp.stream()
//                            .sorted(Comparator.comparing(MilitaryMan::getExpInt).reversed()
//                                    .thenComparing(MilitaryMan::getAccountingDate))
//                            .collect(Collectors.toList());
//
//                    customersWithoutExp = customersWithoutExp.stream().sorted(Comparator.comparing(MilitaryMan::getAccountingDate).reversed()
//                                    .thenComparing(MilitaryMan::getQuotaType).reversed())
//                            .collect(Collectors.toList());
//
//                    customersInGarrison.addAll(customersWithoutExp);
//                } else {
//                    customersInGarrison = customersInGarrison.stream().sorted(Comparator.comparing(MilitaryMan::getAccountingDate).reversed()
//                                    .thenComparing(MilitaryMan::getQuotaType).reversed()
//                                    .thenComparing(c -> c.getQuotaDate() != null ? c.getQuotaDate() : c.getAccountingDate()))
//                            .collect(Collectors.toList());
//                }
//                break;
//            case "POZA":
//                militaryMan.setQuotaType("позачерговий");
//                customersInGarrison = customersInGarrison.stream()
//                        .filter(c -> c.getQuotaType().equals("позачерговий"))
//                        .collect(Collectors.toList());
//
//                List<MilitaryMan> customersWithExp2 = customersInGarrison.stream().filter(c -> c.getExperience() != null && !c.getExperience().equals("0")).collect(Collectors.toList());
//                List<MilitaryMan> customersWithoutExp2 = new ArrayList<>(customersInGarrison);
//                customersWithoutExp2.removeAll(customersWithExp2);
//
//                if (!militaryMan.getExperience().equals("100")) {
//                    customersInGarrison = customersWithExp2.stream()
//                            .sorted(Comparator.comparing(MilitaryMan::getExpInt).reversed()
//                                    .thenComparing(MilitaryMan::getQuotaDate)
//                                    .thenComparing(MilitaryMan::getAccountingDate))
//                            .collect(Collectors.toList());
//
//                    customersWithoutExp2 = customersWithoutExp2.stream().sorted(Comparator.comparing(MilitaryMan::getQuotaDate)
//                                    .thenComparing(MilitaryMan::getAccountingDate))
//                            .collect(Collectors.toList());
//
//                    customersInGarrison.addAll(customersWithoutExp2);
//                } else {
//                    customersInGarrison = customersInGarrison.stream().sorted(Comparator.comparing(MilitaryMan::getQuotaDate)
//                                    .thenComparing(MilitaryMan::getAccountingDate))
//                            .collect(Collectors.toList());
//                }
//
//                break;
//
//            case "ATO":
//                ///TODO find by special quota id`s
////                customersInGarrison = customersInGarrison.stream().filter(c -> c.getQuota2() != null && c.getQuota2().getId() == 25).collect(Collectors.toList());
////                customersInGarrison = customersInGarrison.stream().sorted(Comparator.comparing(MilitaryMan::getQuotaDate2)
////                                .thenComparing(MilitaryMan::getAccountingDate))
////                        .collect(Collectors.toList());
//                break;
//            case "PERSHO":
//                militaryMan.setQuotaType("першочерговий");
//                customersInGarrison = customersInGarrison.stream()
//                        .filter(c -> c.getQuotaType().equals("першочерговий") || (c.getQuotaType2() != null && c.getQuotaType2().equals("першочерговий")))
//                        .collect(Collectors.toList());
//
//                List<MilitaryMan> customersWithExp3 = customersInGarrison.stream().filter(c -> c.getExperience() != null && !c.getExperience().equals("0")).collect(Collectors.toList());
//                List<MilitaryMan> customersWithoutExp3 = new ArrayList<>(customersInGarrison);
//                customersWithoutExp3.removeAll(customersWithExp3);
//
//                if (!militaryMan.getExperience().equals("100")) {
//                    customersInGarrison = customersWithExp3.stream()
//                            .sorted(Comparator.comparing(MilitaryMan::getExpInt).reversed()
//                                    .thenComparing(MilitaryMan::getQuotaDate)
//                                    .thenComparing(MilitaryMan::getAccountingDate))
//                            .collect(Collectors.toList());
//
//                    customersWithoutExp3 = customersWithoutExp3.stream().sorted(Comparator.comparing(MilitaryMan::getQuotaDate)
//                                    .thenComparing(MilitaryMan::getAccountingDate))
//                            .collect(Collectors.toList());
//
//                    customersInGarrison.addAll(customersWithoutExp3);
//                } else {
//                    customersInGarrison = customersInGarrison.stream().sorted(Comparator.comparing(MilitaryMan::getQuotaDate)
//                                    .thenComparing(MilitaryMan::getAccountingDate))
//                            .collect(Collectors.toList());
//                }
////                        .sorted(Comparator.comparing(Customer::getQuotaDate)
////                                .thenComparing(c -> c.getQuotaDate2()!=null ? c.getQuotaDate2() : c.getAccountingDate()))
////                        .collect(Collectors.toList());
//                break;
//        }
//        //ratingXlsCreateService.createXls(System.getProperty("user.home") + File.separator + nameSort + ".xls", customersInGarrison, customer);
//        //System.out.println(customersInGarrison.get(0).getSurname());
//        return customersInGarrison;
  //  }
}
