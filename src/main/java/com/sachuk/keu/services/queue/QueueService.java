package com.sachuk.keu.services.queue;

import com.sachuk.keu.database.service.MilitaryManService;
import com.sachuk.keu.database.service.RegistryService;
import com.sachuk.keu.entities.MilitaryMan;
import com.sachuk.keu.entities.Registry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QueueService {
    private static MilitaryManService militaryManService;
    private static QueueXlsCreateService queueXlsCreateService;
    private static RegistryService registryService;

    @Autowired
    public void setRatingXlsCreateService(QueueXlsCreateService queueXlsCreateService) {
        this.queueXlsCreateService = queueXlsCreateService;
    }

    @Autowired
    public void setMilitaryManService(MilitaryManService militaryManService) {
        this.militaryManService = militaryManService;
    }
    @Autowired
    public void setRegistryService(RegistryService registryService) {
        this.registryService = registryService;
    }

    public static List<MilitaryMan> getQueue(String garrison, String queueType) {
        List<MilitaryMan> queueInGarrison = new ArrayList<>();
        switch (queueType) {
            case "general":
                queueInGarrison = militaryManService.findByGarrison(garrison)
                        .stream().filter(m -> m.getPreview_id()==-1).sorted(
                                Comparator.comparing(m -> m.getAccountingDate()))
                        .collect(Collectors.toList());
                Collections.reverse(queueInGarrison);
                break;
            case "firstinpriority":
            case "outofqueue":
                queueInGarrison = militaryManService.findQueueTypeByGarrison(garrison, queueType.toUpperCase())
                        .stream().sorted(
                                Comparator.comparing(m -> m.getQuotaQueue()))
                        .collect(Collectors.toList());
                break;
            case "compensation":
                queueInGarrison = militaryManService.findByGarrison(garrison)
                        .stream().filter(MilitaryMan::isWantCompensation).sorted(
                                Comparator.comparing(MilitaryMan::getAccountingDate).reversed()
                                        .thenComparing(MilitaryMan::getServiceFrom).reversed())
                        .collect(Collectors.toList());
                break;
        }
        return queueInGarrison;
    }

    public static List<Registry> getReceivedQueue(String garrison, String queueType) {
        List<Registry> registry = new ArrayList<>();
        switch (queueType) {
            case "all":
                registry = registryService.findAllByGarrison(garrison).stream().sorted(Comparator.comparing(Registry::getReceiveDate))
                        .collect(Collectors.toList());
                break;
            case "flat":
                registry = registryService.findByReceivedFlat(garrison).stream().sorted(Comparator.comparing(Registry::getReceiveDate))
                    .collect(Collectors.toList());
            break;
            case "money":
                registry = registryService.findByReceivedMoney(garrison).stream().sorted(Comparator.comparing(Registry::getReceiveDate))
                        .collect(Collectors.toList());
                break;
        }
        return registry;
    }

}
