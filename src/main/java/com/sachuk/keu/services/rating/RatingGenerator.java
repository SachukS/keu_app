package com.sachuk.keu.services.rating;

import com.sachuk.keu.database.service.CustomerService;
import com.sachuk.keu.entities.Customer;
import com.sachuk.keu.rating.GarrisonGroup;
import com.sachuk.keu.rating.Lists;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class RatingGenerator {
    private static final Logger log = Logger.getLogger(RatingGenerator.class);


    //private Count count;
    private CustomerService customerService;
    //private SpecialtyService specialtyService;

//    @Autowired
//    public void setCount(Count count) {
//        this.count = count;
//    }
    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }
//    @Autowired
//    public void setSpecialtyService(SpecialtyService specialtyService) {
//        this.specialtyService = specialtyService;
//    }

    private static final String DATE_FORMAT = "MM/dd/yy hh:mm:ss";
    private static final String OUTFILE = "output";
    private static final String ALLFILE = "outall";
    private static String outFile;
    private static StringBuilder builder;
    private ExecutorService singleThreadExecutor;
    private SimpleDateFormat format;
    private int deltaTime;
    private Customer customer;
    private boolean filterBase = false;
    private boolean checkedFilter = false;
    private boolean filterCollege = false;
    private boolean filterExtramural = false;

//    long last_time = System.nanoTime();

    @PostConstruct
    public void initExecuterService() {
        singleThreadExecutor = Executors.newSingleThreadExecutor();
    }

    public RatingGenerator() {

        //setOutFile();
        format = new SimpleDateFormat(DATE_FORMAT);
        deltaTime = 0 * 1000 * 60 * 60;
//        long time = System.nanoTime();
//        deltaTime = (int) ((time - last_time) / 1000000);
    }


    @SuppressWarnings("unchecked")
    public List<Customer> takeAll() throws ParseException, IOException {

        List<Customer> listAll = null;
        if (customer != null) {
            Date dateN = customer.getUpdateDateTime();
            if (Files.exists(Paths.get(ALLFILE))) {
                Date parse = format.parse(format.format(Files.getLastModifiedTime(Paths.get(ALLFILE)).toMillis()));
                if (parse.getTime() - dateN.getTime() + deltaTime > 0) {
                    try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(ALLFILE))) {
                        listAll = (List<Customer>) inputStream.readObject();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        if (listAll == null) {
            listAll = customerService.findAll();
            final List<Customer> listFinal = listAll;
            singleThreadExecutor.submit(() -> {
                try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(ALLFILE))) {
                    outputStream.writeObject(listFinal);
                } catch (Exception e) {

                }
            });

        }

        return listAll;
    }

    public ArrayList<GarrisonGroup> takeGroups() throws ParseException, IOException {

        Lists lists = null;
        final ArrayList<GarrisonGroup> groups = new ArrayList<>();
        if (customer != null) {
            Date dateN = customer.getUpdateDateTime();
            log.error("customer:" + format.format(dateN));
            if (Files.exists(Paths.get(outFile))) {
                Date parse = format.parse(format.format(Files.getLastModifiedTime(Paths.get(outFile)).toMillis()));
                //log.error("File:" + format.format(parse));

                if (parse.getTime() - dateN.getTime() + deltaTime > 0) {
                    try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(outFile))) {
                        lists = (Lists) inputStream.readObject();
                    } catch (Exception e) {
                        log.warn(e);
                    }
                }
            }
        }
        if (lists == null) {
            List<Customer> listAll = takeAll();

            // FILTER
//            List<Customer> list = listAll.parallelStream().filter(a -> a.getTakenAwayDocs().equals(TakenAwayDocs.NONE))
//                    .collect(Collectors.toList());
//
//            if (isFilterBase()) {
//                list = list.parallelStream()
//                        .filter(a -> a.getMedicalInspection().equals(MedicalInspection.PASSED)
//                                && a.getPhysicalTraining().equals(PhysicalTraining.PASSED)
//                                && a.getProfessionalSelection().equals(ProfessionalSelection.PASSED)
//                                && a.getChecked().equals(Checked.CHECKED))
//                        .collect(Collectors.toList());
//            }
//
//            if(isTemporaryCheckFilter()){
//                list = list.parallelStream()
//                        .filter(a -> !a.getMedicalInspection().equals(MedicalInspection.FAILED)
//                                && !a.getPhysicalTraining().equals(PhysicalTraining.FAILED)
//                                && !a.getProfessionalSelection().equals(ProfessionalSelection.FAILED))
//                        .collect(Collectors.toList());
//            }
//
//
//
//            if (!isFilterExtramural() && !isFilterCollege())
//                list = list
//                        .parallelStream()
//                        .filter(a -> a.getForward().equals(Forward.INSTITUTE))
//                        .filter(a -> a.getStudyForm().equals(StudyForm.NONE))
//                        .collect(Collectors.toList());
//
//            else if (isFilterCollege() && !isFilterExtramural())
//                list = list
//                        .parallelStream()
//                        .filter(a -> a.getForward().equals(Forward.COLLEGE))
//                        .filter(a -> a.getStudyForm().equals(StudyForm.NONE))
//                        .collect(Collectors.toList());
//
//            else
//                list = list.parallelStream().filter(a -> a.getStudyForm().equals(StudyForm.EXTRAMURAL))
//                        .collect(Collectors.toList());


            final List<Customer> customers = listAll;

            customerService.findAll().stream()
                    .forEach(s -> groups.add(new GarrisonGroup(s.getWork().getGarrison(), customers)));
            // ДЛЯ 4х СПЕЦИАЛЬНОСТЕЙ 4 ПРОХОДА (old)
            // ВОЗМОЖНОСТЬ ПОДАТЬ 5 ЗАЯВ ДЛЯ ОДНОГО АБИТУРИЕНТА (new)
            // начиная с 2 приоритета заканчивая 4м (old)
            // начиная с 2 приоритета заканчивая 5м (new)
            // (1й и 0й - приоритеты не учитываем)
            // ЕСЛИ ПРИОРИТЕТОВ ПО СПЕЦИАЛЬНОСТЯМ БОЛЬШЕ ИЛИ МЕНЬШЕ
            // ИЗМЕНИТЬ ГРАНИЦУ
            // 6 - (6/2 == 3 -- {2,3,4 - приоритеты} - на втором прогоне снимаем льготы с не прошедших - идут на общих основаниях) (old)
            // 8 - (8/2 == 4 -- {2,3,4,5 - приоритеты} - на втором прогоне снимаем льготы с не прошедших - идут на общих основаниях) (new)

//            Enumerable.range(2, 8).forEach(i -> {
//                //для устранения вытеснения при проходе --- надо остаться на старом приоритете по кол-ву специальностей -1
//                for (int j = 0; j < 2; j++) {
//                    //ToDO
//                    //!!!надо лишние итерации убрать за счет подсчета сумі хеша листа не поступивших
//                    List<Enrollee> collectNotIn = groups.stream().flatMap(g -> g.getNotEnteredEnrollees().stream())
//                            .collect(Collectors.toList());
//                    //очищаем поступивших по 2му (3,4,5) приоритету из не поступиших по основному
//                    List<Enrollee> collectNotInChecked = collectNotIn.stream().filter(ab -> !groups.stream().anyMatch(g -> g.getEnteredEnrollees().contains(ab))).collect(Collectors.toList());
//
//                    //очистка льгот
//                    List<Privilege> pilgs = new ArrayList<>();
//                    if (i > 5) collectNotInChecked.stream().forEach(ab -> {
//                        ab.setPrivileges(pilgs);
//                        ab.setQuota(Quotas.ZSU);
//                        ab.setLicey(Lyceum.SCHOOL);
//                    });
//                    //добавляем не прошедших по приоретету на следующий приоритет
//                    groups.stream().forEach(g -> g.addEnrollees(collectNotInChecked, i > 5 ? i - 4 : i));
//                }
//            });

            lists = new Lists();
            lists.setGroups(groups);
            Lists listsToSave = lists;
            singleThreadExecutor.submit(() -> {
                try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(outFile))) {
                    outputStream.writeObject(listsToSave);
                } catch (Exception e) {

                }
            });
        } else {
            //groups.clear();
            lists.getGroups().forEach(g -> groups.add(g));
        }

        return groups;
    }

//    private int GetHash(List<Enrollee> collectNotInChecked) {
//        return collectNotInChecked.stream().collect(Collectors.summingInt(ab -> ab.hashCode()));
//    }

//    private void setOutFile() {
//        builder = new StringBuilder(OUTFILE);
//        if (isFilterBase())
//            builder.append("base");
//        if (isFilterCollege())
//            builder.append("college");
//        if (!isFilterCollege())
//            builder.append("institute");
//        outFile = builder.toString();
//
//    }
//
//    public void setFilterBase(boolean filterBase) {
//        //setOutFile();
//        this.filterBase = filterBase;
//    }
//
//    public boolean isFilterBase() {
//        return filterBase;
//    }
//
//    public void setFilterCollege(boolean filterCollege) {
//        //setOutFile();
//        this.filterCollege = filterCollege;
//    }
//
//    public boolean isFilterCollege() {
//        return filterCollege;
//    }
//
//    public void checkedFilter(boolean checkedFilter) {
//        this.checkedFilter = checkedFilter;
//    }
//
//    public boolean isTemporaryCheckFilter() {
//        return checkedFilter;
//    }
//
//    public void setFilterExtramural(boolean isExtramural) {
//        filterExtramural = isExtramural;
//    }
//
//    public boolean isFilterExtramural() {
//        return filterExtramural;
//    }

}
