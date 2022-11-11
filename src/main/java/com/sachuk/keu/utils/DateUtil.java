package com.sachuk.keu.utils;

import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static final Logger log = Logger.getLogger(DateUtil.class);

    public static Date formatDate(String date, String datePattern) {
        log.debug("Before formated date: " + date);

        Date resultDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
        try {
            resultDate = formatter.parse(date);
            log.debug("Success formated date to format: " + resultDate.toString());
        } catch (ParseException e) {
            log.error("Cannot formated date");
            log.error(e.getMessage());
        }
        return resultDate;
    }

    public static String formatDateToString(Date date, String datePattern) {
        log.trace("Before format date: " + date + " with pattern: " + datePattern);
        String dateResult;
        SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
        dateResult = dateFormat.format(date);
        log.trace("Success format date: " + dateResult);
        return dateResult;
    }
/*
    public static String formatDateWithTimeToString(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH.mm dd.MM.yyyy");
        return dateFormat.format(date);
    }*/
}