package com.sachuk.keu.services.queue;

import com.sachuk.keu.entities.MilitaryMan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.List;

@Service
public class QueueXlsCreateService {
    private static final String ALL = "ALL";

    @Autowired
    private QueueXLSCreator xlsCreator;
    private boolean isAll = true;

    public void createXls(String fileName, List<MilitaryMan> militaryMEN, MilitaryMan militaryMan) {
        create(fileName, militaryMEN, militaryMan);
    }

    private void create(Object input, List<MilitaryMan> militaryMEN, MilitaryMan militaryMan) {
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
            xlsCreator.writeXls(militaryMEN, ALL, militaryMan);

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
