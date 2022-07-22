package com.sachuk.keu.rating;

import com.sachuk.keu.entities.Customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GarrisonGroup implements Serializable {
    private static final long serialVersionUID = 2172433874851280587L;
    private String garrison;
    private ArrayList<Customer> customers;
    //private ArrayList<QuotaGroup> quotaGroups;
    private int femaleAmount;
    //private final Count count;
    //private ArrayList<Enrollee> enteredEnrollees;


//    public SpecialtyGroup(Specialty specialty, Count count) {
//        this.specialty = specialty;
//        this.count = count;
//        enrollees = new ArrayList<>();
//    }

    public GarrisonGroup(String garrison, List<Customer> customers) {
        this.garrison = garrison;
        //this.count = count;
        this.customers = (ArrayList<Customer>) customers.stream().filter(a -> a.getWork().getGarrison().equals(garrison)).collect(Collectors.toList());
//        this.customers = (ArrayList<Customer>) customers.stream().filter(a -> {
//            String ab = a.getWork().stream()
//                    .filter(s -> s.getSpecialty().equals(garrison) && s.getPriority() == (byte) 1).findAny()
//                    .orElse(null);
//            return (ab == null) ? false : true;
//        }).collect(Collectors.toList());
        //sortEnrollees();
        //femaleAmount = count.getFemaleAmount(this.specialty);
        //job();
    }

//    private void job() {
//        int unnecessaryQuotas = 0;
//        quotaGroups = new ArrayList<QuotaGroup>();
//        enteredEnrollees = new ArrayList<Enrollee>();
//
//        if (count.getAmount(Quotas.SZR, this.specialty) > 0) {
//            QuotaGroup quotas = new QuotaGroup(specialty, Quotas.SZR, customers, femaleAmount, count);
//            quotaGroups.add(quotas);
//            enteredEnrollees.addAll(quotas.getEnteredEnrollees());
//            //femaleAmount -= quotas.getFemaleEnteredAmount();
//            enteredEnrollees.addAll(quotas.getQuotaPrivilegeGroups().stream()
//                    .flatMap(lg -> lg.getEnteredEnrollees().stream()).collect(Collectors.toList()));
//            unnecessaryQuotas += count.getAmount(Quotas.SZR, this.specialty) - quotas.getRealQuotaAmount();
//        }
//        if (count.getAmount(Quotas.DPSU, this.specialty) > 0) {
//            QuotaGroup quotas = new QuotaGroup(specialty, Quotas.DPSU,
//                    customers.stream().filter(a -> !enteredEnrollees.contains(a)).collect(Collectors.toList()),
//                    femaleAmount, count);
//            quotaGroups.add(quotas);
//            enteredEnrollees.addAll(quotas.getEnteredEnrollees());
//            //femaleAmount -= quotas.getFemaleEnteredAmount();
//            enteredEnrollees.addAll(quotas.getQuotaPrivilegeGroups().stream()
//                    .flatMap(lg -> lg.getEnteredEnrollees().stream()).collect(Collectors.toList()));
//            unnecessaryQuotas += count.getAmount(Quotas.DPSU, this.specialty) - quotas.getRealQuotaAmount();
//        }
//        if (count.getAmount(Quotas.DSST, this.specialty) > 0) {
//            QuotaGroup quotas = new QuotaGroup(specialty, Quotas.DSST,
//                    customers.stream().filter(a -> !enteredEnrollees.contains(a)).collect(Collectors.toList()),
//                    femaleAmount, count);
//            quotaGroups.add(quotas);
//            enteredEnrollees.addAll(quotas.getEnteredEnrollees());
//            //femaleAmount -= quotas.getFemaleEnteredAmount();
//            enteredEnrollees.addAll(quotas.getQuotaPrivilegeGroups().stream()
//                    .flatMap(lg -> lg.getEnteredEnrollees().stream()).collect(Collectors.toList()));
//            unnecessaryQuotas += count.getAmount(Quotas.DSST, this.specialty) - quotas.getRealQuotaAmount();
//        }
//        if (count.getAmount(Quotas.NGU, this.specialty) > 0) {
//            QuotaGroup quotas = new QuotaGroup(specialty, Quotas.NGU,
//                    customers.stream().filter(a -> !enteredEnrollees.contains(a)).collect(Collectors.toList()),
//                    femaleAmount, count);
//            quotaGroups.add(quotas);
//            enteredEnrollees.addAll(quotas.getEnteredEnrollees());
//            //femaleAmount -= quotas.getFemaleEnteredAmount();
//            enteredEnrollees.addAll(quotas.getQuotaPrivilegeGroups().stream()
//                    .flatMap(lg -> lg.getEnteredEnrollees().stream()).collect(Collectors.toList()));
//            unnecessaryQuotas += count.getAmount(Quotas.NGU, this.specialty) - quotas.getRealQuotaAmount();
//        }
//        if (count.getAmount(Quotas.SBU, this.specialty) > 0) {
//            QuotaGroup quotas = new QuotaGroup(specialty, Quotas.SBU,
//                    customers.stream().filter(a -> !enteredEnrollees.contains(a)).collect(Collectors.toList()),
//                    femaleAmount, count);
//            quotaGroups.add(quotas);
//            enteredEnrollees.addAll(quotas.getEnteredEnrollees());
//            //femaleAmount -= quotas.getFemaleEnteredAmount();
//            enteredEnrollees.addAll(quotas.getQuotaPrivilegeGroups().stream()
//                    .flatMap(lg -> lg.getEnteredEnrollees().stream()).collect(Collectors.toList()));
//            unnecessaryQuotas += count.getAmount(Quotas.SBU, this.specialty) - quotas.getRealQuotaAmount();
//        }
//        if (count.getAmount(Quotas.DKA, this.specialty) > 0) {
//            QuotaGroup quotas = new QuotaGroup(specialty, Quotas.DKA,
//                    customers.stream().filter(a -> !enteredEnrollees.contains(a)).collect(Collectors.toList()),
//                    femaleAmount, count);
//            quotaGroups.add(quotas);
//            enteredEnrollees.addAll(quotas.getEnteredEnrollees());
//            //femaleAmount -= quotas.getFemaleEnteredAmount();
//            enteredEnrollees.addAll(quotas.getQuotaPrivilegeGroups().stream()
//                    .flatMap(lg -> lg.getEnteredEnrollees().stream()).collect(Collectors.toList()));
//            unnecessaryQuotas += count.getAmount(Quotas.DKA, this.specialty) - quotas.getRealQuotaAmount();
//        }
//        if (count.getAmount(Quotas.ZSU,this.specialty) > 0) {
//            QuotaGroup quotas = new QuotaGroup(specialty, Quotas.ZSU,
//                    customers.stream().filter(a -> !enteredEnrollees.contains(a)).collect(Collectors.toList()),
//                    femaleAmount, count);
//            quotaGroups.add(quotas);
//            quotas.setExcessQuotas(unnecessaryQuotas);
//            enteredEnrollees.addAll(quotas.getEnteredEnrollees());
//            enteredEnrollees.addAll(quotas.getQuotaPrivilegeGroups().stream()
//                    .flatMap(lg -> lg.getEnteredEnrollees().stream()).collect(Collectors.toList()));
//        }
//    }

//    public Specialty getSpecialty() {
//        return specialty;
//    }
//
    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
        //sortEnrollees();
    }
//
//    public List<Enrollee> getEnteredEnrollees() {
//        return customers.stream().filter(a -> enteredEnrollees.contains(a)).collect(Collectors.toList());
//    }
//
//    public List<Enrollee> getNotEnteredEnrollees() {
//        return customers.stream().filter(a -> !enteredEnrollees.contains(a)).collect(Collectors.toList());
//    }
//
////    private void sortEnrollees() {
////        this.customers.sort((a2, a1) -> ((int) (100
////                * (RatingCounter.getEvaluation(a1) - RatingCounter.getEvaluation(a2))) != 0)
////                ? (int) (100 * (RatingCounter.getEvaluation(a1)
////                - RatingCounter.getEvaluation(a2)))
////                : (int) (100
////                * (RatingCounter.getMathEvaluation(a1) - RatingCounter.getMathEvaluation(a2))));
////    }
//
//    public void addEnrollee(Enrollee enrollee) {
//        if (customers.contains(enrollee))
//            return;
//        customers.add(enrollee);
//    }
//
//    public void addEnrollees(List<Enrollee> enrollees, int priority) {
//        ArrayList<Enrollee> enrollees1 = (ArrayList<Enrollee>) enrollees.stream().filter(a -> {
//            EnrolleeSpecialty ab = a.getSpecialties().stream()
//                    .filter(s -> s.getSpecialty().equals(specialty) && s.getPriority() == (byte) priority).findAny()
//                    .orElse(null);
//            return (ab == null) ? false : true;
//        }).collect(Collectors.toList());
//        enrollees1.stream().filter(a -> !this.customers.contains(a)).forEach(a -> this.customers.add(a));
//        sortEnrollees();
//        //femaleAmount = count.getFemaleAmount(this.specialty);
//        job();
//    }
//
//    public ArrayList<QuotaGroup> getQuotaGroups() {
//        return quotaGroups;
//    }
}
