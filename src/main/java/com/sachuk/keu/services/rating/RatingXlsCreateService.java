package com.sachuk.keu.services.rating;

import com.sachuk.keu.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.List;

@Service
public class RatingXlsCreateService {
    private static final String ALL = "ALL";

    @Autowired
    private RatingXLSCreator xlsCreator;
    private boolean isAll = true;

    public void createXls(String fileName, List<Customer> customers, Customer customer) {
        create(fileName, customers, customer);
    }

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

 }
