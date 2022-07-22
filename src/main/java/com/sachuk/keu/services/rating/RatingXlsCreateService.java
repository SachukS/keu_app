package com.sachuk.keu.services.rating;

import com.sachuk.keu.entities.Customer;
import com.sachuk.keu.rating.GarrisonGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;

@Service
public class RatingXlsCreateService {
    private String specList;
    private static final String ALL = "ALL";
    @Autowired
    private RatingGenerator ratingGenerator;
    @Autowired
    private RatingXLSCreator xlsCreator;
    private boolean isAll = true;

    public void createXls(String fileName, List<Customer> customers, Customer customer) {
        create(fileName, customers, customer);
    }

//    public void createXls(OutputStream stream) {
//        create(stream);
//    }

    private void create(Object input, List<Customer> customers, Customer customer) {
        xlsCreator.renew();
        OutputStream stream = null;
        String fileName = null;
        if (input instanceof OutputStream)
            stream = (OutputStream) input;
        else if (input instanceof String)
            fileName = (String) input;
        else
            return;
        try {
            System.out.println(fileName);
            if (fileName != null)
                xlsCreator.setFileName(fileName);
                xlsCreator.writeXls(customers, ALL, customer);

            if (stream != null)
                xlsCreator.close(stream);
            else
                xlsCreator.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    public boolean isAll() {
        return isAll;
    }

    public void setAll(boolean isAll) {
        this.isAll = isAll;
    }

//    public void filterBase(boolean isBase) {
//        ratingGenerator.setFilterBase(isBase);
//    }
//
//    public void filterCollege(boolean isCollege) {
//        ratingGenerator.setFilterCollege(isCollege);
//    }
//
//    public void filterExtramural(boolean isExtramural) {
//        ratingGenerator.setFilterExtramural(isExtramural);
//    }

    public String getSpecList() {
        return specList;
    }

    public void setSpecList(String specList) {
        this.specList = specList;
    }

//    public void filterChecked(boolean isChecked) {
//        ratingGenerator.checkedFilter(isChecked);
//    }

}
