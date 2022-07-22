package com.sachuk.keu.services.rating;

import com.sachuk.keu.database.service.CustomerService;
import com.sachuk.keu.entities.Customer;
import com.sachuk.keu.entities.Quota;
import com.sachuk.keu.entities.Work;
import com.sachuk.keu.entities.enums.QuotaType;
import com.sachuk.keu.entities.enums.Registrated;
import com.sachuk.keu.entities.thirdparty.CustomerShort;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingXLSWeb {
    private static CustomerService customerService;
    private static RatingXlsCreateService ratingXlsCreateService;
    @Autowired
    public void setRatingXlsCreateService(RatingXlsCreateService ratingXlsCreateService) {
        this.ratingXlsCreateService = ratingXlsCreateService;
    }
    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public static List<Customer> getCustomers(String nameSort, Customer customer) {

        List<Customer> customersInGarrison = customerService.findByGarrison(nameSort.substring(0,4));

        if(!customer.getWork().getAccountingPlace().equals("Всі")){
            customersInGarrison = customersInGarrison.stream()
                    .filter(c -> c.getWork().getAccountingPlace().equals(customer.getWork().getAccountingPlace()))
                    .collect(Collectors.toList());
        }
        if(!customer.getWork().getWorkPlace().equals("Всі")){
            customersInGarrison = customersInGarrison.stream()
                    .filter(c -> c.getWork().getWorkPlace().equals(customer.getWork().getWorkPlace()))
                    .collect(Collectors.toList());
        }
        if(!customer.getQuota().equals(new Quota("Всі","Всі", QuotaType.NONE))){
            customersInGarrison = customersInGarrison.stream()
                    .filter(c -> c.getQuota().equals(customer.getQuota()) || (c.getQuota2() != null && c.getQuota2().equals(customer.getQuota())))
                    .collect(Collectors.toList());
        }
        if(customer.getFamilyCount()!=0){
            customersInGarrison = customersInGarrison.stream()
                    .filter(c -> c.getFamilyCount() == customer.getFamilyCount())
                    .collect(Collectors.toList());
        }
        if(customer.getRoomCount()!=0){
            customersInGarrison = customersInGarrison.stream()
                    .filter(c -> c.getRoomCount() == customer.getRoomCount())
                    .collect(Collectors.toList());
        }
        if(!customer.getRegistrated().equals(Registrated.ALL)){
            customersInGarrison = customersInGarrison.stream()
                    .filter(c -> c.getRegistrated().equals(customer.getRegistrated()))
                    .collect(Collectors.toList());
        }

        String sortBy = nameSort.substring(4);
        System.out.println(sortBy);

        switch (sortBy){
            case "ZAGALNA":
                List<Customer> customersWithExp = customersInGarrison.stream().filter(c -> c.getExperience() != null && !c.getExperience().equals("0")).collect(Collectors.toList());
                List<Customer> customersWithoutExp = new ArrayList<>(customersInGarrison);
                customersWithoutExp.removeAll(customersWithExp);

                if (!customer.getExperience().equals("100")){
                    customersInGarrison = customersWithExp.stream()
                            .sorted(Comparator.comparing(Customer::getExpInt).reversed()
                                    .thenComparing(Customer::getAccountingDate))
                            .collect(Collectors.toList());

                    customersWithoutExp = customersWithoutExp.stream().sorted(Comparator.comparing(Customer::getAccountingDate).reversed()
                                    .thenComparing(Customer::getQuotaType).reversed())
                            .collect(Collectors.toList());

                    customersInGarrison.addAll(customersWithoutExp);
                }
                else {
                    customersInGarrison = customersInGarrison.stream().sorted(Comparator.comparing(Customer::getAccountingDate).reversed()
                                    .thenComparing(Customer::getQuotaType).reversed()
                                    .thenComparing(c->c.getQuotaDate() != null ? c.getQuotaDate() : c.getAccountingDate()))
                            .collect(Collectors.toList());
                }
            break;
            case "POZA":
                customer.setQuotaType("позачерговий");
                customersInGarrison = customersInGarrison.stream()
                        .filter(c->c.getQuotaType().equals("позачерговий") || (c.getQuotaType2() != null && c.getQuotaType2().equals("позачерговий")))
                        .collect(Collectors.toList());

                List<Customer> customersWithExp2 = customersInGarrison.stream().filter(c -> c.getExperience() != null && !c.getExperience().equals("0")).collect(Collectors.toList());
                List<Customer> customersWithoutExp2 = new ArrayList<>(customersInGarrison);
                customersWithoutExp2.removeAll(customersWithExp2);

                if (!customer.getExperience().equals("100")){
                    customersInGarrison = customersWithExp2.stream()
                            .sorted(Comparator.comparing(Customer::getExpInt).reversed()
                                    .thenComparing(Customer::getQuotaDate)
                                    .thenComparing(c -> c.getQuotaDate2()!=null ? c.getQuotaDate2() : c.getAccountingDate()))
                            .collect(Collectors.toList());

                    customersWithoutExp2 = customersWithoutExp2.stream().sorted(Comparator.comparing(Customer::getQuotaDate)
                                    .thenComparing(c -> c.getQuotaDate2()!=null ? c.getQuotaDate2() : c.getAccountingDate()))
                            .collect(Collectors.toList());

                    customersInGarrison.addAll(customersWithoutExp2);
                }
                else {
                    customersInGarrison = customersInGarrison.stream().sorted(Comparator.comparing(Customer::getQuotaDate)
                                    .thenComparing(c -> c.getQuotaDate2()!=null ? c.getQuotaDate2() : c.getAccountingDate()))
                            .collect(Collectors.toList());
                }

                break;

            case "ATO":
                customersInGarrison = customersInGarrison.stream().sorted(Comparator.comparing(Customer::getAccountingDate).reversed()
                                .thenComparing(Customer::getQuotaType).reversed())
                        .collect(Collectors.toList());
                break;
            case "PERSHO":
                customer.setQuotaType("першочерговий");
                customersInGarrison = customersInGarrison.stream()
                        .filter(c->c.getQuotaType().equals("першочерговий") || (c.getQuotaType2() != null && c.getQuotaType2().equals("першочерговий")))
                        .collect(Collectors.toList());

                List<Customer> customersWithExp3 = customersInGarrison.stream().filter(c -> c.getExperience() != null && !c.getExperience().equals("0")).collect(Collectors.toList());
                List<Customer> customersWithoutExp3 = new ArrayList<>(customersInGarrison);
                customersWithoutExp3.removeAll(customersWithExp3);

                if (!customer.getExperience().equals("100")){
                    customersInGarrison = customersWithExp3.stream()
                            .sorted(Comparator.comparing(Customer::getExpInt).reversed()
                                    .thenComparing(Customer::getQuotaDate)
                                    .thenComparing(c -> c.getQuotaDate2()!=null ? c.getQuotaDate2() : c.getAccountingDate()))
                            .collect(Collectors.toList());

                    customersWithoutExp3 = customersWithoutExp3.stream().sorted(Comparator.comparing(Customer::getQuotaDate)
                                    .thenComparing(c -> c.getQuotaDate2()!=null ? c.getQuotaDate2() : c.getAccountingDate()))
                            .collect(Collectors.toList());

                    customersInGarrison.addAll(customersWithoutExp3);
                }
                else {
                    customersInGarrison = customersInGarrison.stream().sorted(Comparator.comparing(Customer::getQuotaDate)
                                    .thenComparing(c -> c.getQuotaDate2()!=null ? c.getQuotaDate2() : c.getAccountingDate()))
                            .collect(Collectors.toList());
                }
//                        .sorted(Comparator.comparing(Customer::getQuotaDate)
//                                .thenComparing(c -> c.getQuotaDate2()!=null ? c.getQuotaDate2() : c.getAccountingDate()))
//                        .collect(Collectors.toList());
                break;
        }
        ratingXlsCreateService.createXls(System.getProperty("user.home") + File.separator + nameSort + ".xls", customersInGarrison, customer);
        //System.out.println(customersInGarrison.get(0).getSurname());
        return customersInGarrison;
    }
}
