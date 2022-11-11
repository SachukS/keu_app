package com.sachuk.keu.services.rating;

import com.sachuk.keu.entities.Customer;
import com.sachuk.keu.entities.Work;
import com.sachuk.keu.utils.DateUtil;
import com.sachuk.keu.utils.Enumerable;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class RatingXLSCreator implements AutoCloseable {

    private static final String[] ALL = {"All", "Всі", "Повний список", "Загальний"};
    private Workbook book = new HSSFWorkbook();
    private Sheet sheet;
    private String SPEC = "";
    private int rowNum = 0;
    private int rowCol = 0;
    private int isSecond;
    private String fileName;
    private CellStyle style;
    private static List<Customer> customers = new ArrayList<>();
    private String[] names = {"№", "№плг", "Військ. звання", "Прізвище, ініціали", "Дата кв/обліку", "Дата пільги", "Категорія пільг", "чл.сім.", "кімн.", "ЖК", "Організація", "Висл"};
    private Row rows = null;

    private static Work getWorkWithGarrison() {
        return customers.stream().map(Customer::getWork).findFirst().get();
    }

    public RatingXLSCreator() {
        Arrays.sort(ALL);
        style = book.createCellStyle();
    }

    public RatingXLSCreator(String fileName) {
        this.fileName = fileName;
        Arrays.sort(ALL);
        style = book.createCellStyle();
    }

    public void renew() {
        book = new HSSFWorkbook();
        Font font = book.createFont();
        font.setFontHeightInPoints((short) 7);
        font.setFontName("Arial");
        font.setItalic(false);

        style = book.createCellStyle();

        style.setFont(font);
        SPEC = "";
    }

    public void writeXls(List<Customer> list, String name, Customer customer) {
        if (!fileName.equals(System.getProperty("user.home") + File.separator + "DOVIDKA.xls")) {
            customers = list;
            isSecond = 1;
            System.out.println(name);

            if (book == null)
                return;
            if (!SPEC.equals(name)) {
                sheet = book.createSheet(name);

                SPEC = name;
                rowNum = 6;
                rowCol = 0;

                sheet.setMargin(Sheet.LeftMargin, 0.26);
                sheet.setMargin(Sheet.BottomMargin, 0.24);
                sheet.setMargin(Sheet.RightMargin, 0.24);
                sheet.setMargin(Sheet.TopMargin, 0.24);

                createTableHeaderForSheet(name, customer);

            } else {
                sheet = book.getSheet(name);
                sheet.setFitToPage(true);

            }


            list.forEach(a -> {
                int nom = 0;
                ++rowNum;
                ++rowCol;
                rows = sheet.createRow(rowNum);
                setCellValue(rows, nom++, rowCol);

//                setCellValue(rows, nom++, (int) a.getId());

                setCellValue(rows, nom++, a.getPilgova());

                setCellValue(rows, nom++, a.getRank().getShortNameRank());

                setCellValue(rows, nom++, a.getSurname() + " " + a.getName().charAt(0) + "."
                        + a.getThirdName().charAt(0) + ".");

                setCellValue(rows, nom++, a.getFormatAccounting());

                setCellValue(rows, nom++, a.getFormatQuota());

                setCellValue(rows, nom++, a.getQuotaType());

                setCellValue(rows, nom++, a.getFamilyCount());

                setCellValue(rows, nom++, a.getRoomCount());

                setCellValue(rows, nom++, a.getWork().getAccountingPlace());

                setCellValue(rows, nom++, a.getWork().getWorkPlace());

                setCellValue(rows, nom++, a.getExperience());

                isSecond++;
                if (isSecond == 2) {
                    Font font = book.createFont();
                    font.setFontHeightInPoints((short) 7);
                    font.setFontName("Arial");
                    font.setItalic(false);

                    style = book.createCellStyle();
                    style.setFont(font);
                }
            });
            Enumerable.range(0, names.length).forEach(c -> {
                sheet.autoSizeColumn(c);
            });
        } else {
            customers = list;

            if (book == null)
                return;
            if (!SPEC.equals(name)) {
                sheet = book.createSheet(name);

                sheet.setMargin(Sheet.TopMargin, 0.24);

                createDovidka(list, customer);

            } else {
                sheet = book.getSheet(name);
                sheet.setFitToPage(true);

            }
            sheet.autoSizeColumn(5);

            sheet.setMargin(Sheet.RightMargin, 0.24);
            sheet.setColumnWidth(2, 3300);
            sheet.setColumnWidth(9, 1800);
        }
    }

    private void createDovidka(List<Customer> customers, Customer customer) {
        style = book.createCellStyle();
        Font font = book.createFont();
        font.setFontHeightInPoints((short) 11);
        font.setFontName("Arial");
        font.setBold(true);
        style.setFont(font);
        style.setAlignment(CellStyle.ALIGN_LEFT);

        CellStyle styleSimple = book.createCellStyle();
        Font font2 = book.createFont();
        font2.setFontHeightInPoints((short) 11);
        font2.setFontName("Arial");
        font2.setBold(false);
        styleSimple.setFont(font2);
        styleSimple.setWrapText(true);
        styleSimple.setVerticalAlignment(CellStyle.VERTICAL_TOP);
        styleSimple.setAlignment(CellStyle.ALIGN_LEFT);


        Row rowFirst = sheet.createRow(0);

        Cell title = rowFirst.createCell(3);
        title.setCellValue("ІНФОРМАЦІЙНА ДОВІДКА від " + getDateTime(LocalDateTime.now()));
        title.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 3, 7));


        Row rowThird = sheet.createRow(2);

        Cell rank = rowThird.createCell(0);
        rank.setCellValue("Військове звання:");
        rank.setCellStyle(styleSimple);
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 1));

        Cell surname = rowThird.createCell(2);
        surname.setCellValue("Прізвище:");
        surname.setCellStyle(styleSimple);
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 2, 3));

        Cell cellName = rowThird.createCell(5);
        cellName.setCellValue("Ім'я:");
        cellName.setCellStyle(styleSimple);

        Cell thirdName = rowThird.createCell(7);
        thirdName.setCellValue("По-батькові:");
        thirdName.setCellStyle(styleSimple);
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 7, 8));


        Row rowFour = sheet.createRow(3);

        Cell ranVal = rowFour.createCell(0);
        ranVal.setCellValue(customer.getRank().getNameRank());
        ranVal.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 1));

        Cell surVal = rowFour.createCell(2);
        surVal.setCellValue(customer.getSurname());
        surVal.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 2, 4));

        Cell nameVal = rowFour.createCell(5);
        nameVal.setCellValue(customer.getName());
        nameVal.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 5, 6));

        Cell thirdnameVal = rowFour.createCell(7);
        thirdnameVal.setCellValue(customer.getThirdName());
        thirdnameVal.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 7, 9));


        Row sixRow = sheet.createRow(5);

        Cell garrison = sixRow.createCell(0);
        garrison.setCellValue("Гарнізон " + customer.getWork().getGarrison());
        garrison.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 3));


        Row eightRow = sheet.createRow(7);

        Cell kwData = eightRow.createCell(0);
        kwData.setCellValue("дата зарахування на кв. облік:");
        kwData.setCellStyle(styleSimple);
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 0, 2));

        Cell kwValue = eightRow.createCell(3);
        kwValue.setCellValue(customer.getAccountingDate() != null ? customer.getFormatAccounting() : "-");
        kwValue.setCellStyle(style);

        Cell kst = eightRow.createCell(5);
        kst.setCellValue("кількість чл. сім'ї:");
        kst.setCellStyle(styleSimple);
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 5, 6));

        Cell kstValue = eightRow.createCell(7);
        kstValue.setCellValue(customer.getFamilyCount());
        kstValue.setCellStyle(style);


        Row tenRow = sheet.createRow(9);

        Cell mesto = tenRow.createCell(0);
        mesto.setCellValue("місце служби:");
        mesto.setCellStyle(styleSimple);
        sheet.addMergedRegion(new CellRangeAddress(9, 9, 0, 1));

        Cell mestoVal = tenRow.createCell(2);
        mestoVal.setCellValue(customer.getWork().getWorkPlace());
        mestoVal.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(9, 9, 2, 3));

        Cell sklad = tenRow.createCell(5);
        sklad.setCellValue("склад сім'ї:");
        sklad.setCellStyle(styleSimple);
//        sheet.addMergedRegion(new CellRangeAddress(9,9,4,5));

        Cell skladVal = tenRow.createCell(6);
        skladVal.setCellValue(customer.getFamily());
        skladVal.setCellStyle(styleSimple);
        sheet.addMergedRegion(new CellRangeAddress(9, 15, 6, 9));


        Row row12 = sheet.createRow(11);

        Cell zhk = row12.createCell(0);
        zhk.setCellValue("назва ЖК:");
        zhk.setCellStyle(styleSimple);
        sheet.addMergedRegion(new CellRangeAddress(11, 11, 0, 1));

        Cell zhkVal = row12.createCell(2);
        zhkVal.setCellValue(customer.getWork().getAccountingPlace());
        zhkVal.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(11, 11, 2, 4));


        Row row15 = sheet.createRow(14);

        Cell plg = row15.createCell(0);
        plg.setCellValue("найменування пільги:");
        plg.setCellStyle(styleSimple);
        sheet.addMergedRegion(new CellRangeAddress(14, 14, 0, 2));

        Cell plgVal = row15.createCell(3);
        plgVal.setCellValue(customer.getQuota().getShortNameQuota());
        plgVal.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(14, 14, 3, 5));


        Row row17 = sheet.createRow(16);

        Cell plgDate = row17.createCell(0);
        plgDate.setCellValue("дата набуття пільги:");
        plgDate.setCellStyle(styleSimple);
        sheet.addMergedRegion(new CellRangeAddress(16, 16, 0, 2));

        Cell plgDateVal = row17.createCell(3);
        plgDateVal.setCellValue(customer.getQuotaDate() != null ? customer.getFormatQuota() : "-");
        plgDateVal.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(16, 16, 3, 4));

        Cell kat = row17.createCell(5);
        kat.setCellValue("категорія пільги:");
        kat.setCellStyle(styleSimple);
        sheet.addMergedRegion(new CellRangeAddress(16, 16, 5, 6));

        Cell katVal = row17.createCell(7);
        katVal.setCellValue(customer.getQuotaType());
        katVal.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(16, 16, 7, 8));


        Row row19 = sheet.createRow(18);

        Cell zag = row19.createCell(0);
        zag.setCellValue("Номер в загальній черзі гарнізону:");
        zag.setCellStyle(styleSimple);
        sheet.addMergedRegion(new CellRangeAddress(18, 18, 0, 3));

        Cell zagVal = row19.createCell(4);
        zagVal.setCellValue(customer.getZagalna());
        zagVal.setCellStyle(style);


        Row row21 = sheet.createRow(20);

        Cell nom = row21.createCell(0);
        nom.setCellValue("Номер в черзі");
        nom.setCellStyle(styleSimple);
        sheet.addMergedRegion(new CellRangeAddress(20, 20, 0, 1));

        Cell nomType = row21.createCell(2);
        nomType.setCellValue(customer.getQuotaType());
        nomType.setCellStyle(styleSimple);
        sheet.addMergedRegion(new CellRangeAddress(20, 20, 2, 3));

        Cell nomVal = row21.createCell(4);
        nomVal.setCellValue(customer.getQuotaType().equals("без пільг") ? "немає" : String.valueOf(customer.getPilgova()));
        nomVal.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(20, 20, 2, 3));


        Row row23 = sheet.createRow(22);

        Cell prim = row23.createCell(0);
        prim.setCellValue("Примітки:");
        prim.setCellStyle(styleSimple);
        sheet.addMergedRegion(new CellRangeAddress(22, 22, 0, 1));

        Cell primVal = row23.createCell(2);
        primVal.setCellValue(customer.getInfo());
        primVal.setCellStyle(styleSimple);
        sheet.addMergedRegion(new CellRangeAddress(22, 28, 2, 6));


        Row last = sheet.createRow(31);

        Cell foot = last.createCell(0);
        foot.setCellValue("*Довідка має виключно інформаційний характер та призначена для внутрішньої роботи ККЕУ");
        foot.setCellStyle(styleSimple);
        sheet.addMergedRegion(new CellRangeAddress(31, 32, 0, 9));
    }

    private void createTableHeaderForSheet(String name, Customer customer) {
        Font font = book.createFont();
        font.setFontHeightInPoints((short) 7);
        font.setFontName("Arial");
        font.setItalic(false);
        style.setFont(font);

        Row rowFirst = sheet.createRow(0);

        Cell cellDate = rowFirst.createCell(0);
        cellDate.setCellValue(getDateTime(LocalDateTime.now()));
        cellDate.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));

        Cell cellTitle = rowFirst.createCell(2);
        cellTitle.setCellValue("Список осіб, що потребують покращення житлових умов гарнізону:");
        cellTitle.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 5));

        Cell cellGarrison = rowFirst.createCell(6);
        cellGarrison.setCellValue(getWorkWithGarrison().getGarrison());
        cellGarrison.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 6, 8));

        Row rowSecond = sheet.createRow(1);

        Cell cellOrg = rowSecond.createCell(2);
        cellOrg.setCellValue("Організація:");
        cellOrg.setCellStyle(style);

        Cell cellOrg2 = rowSecond.createCell(3);
        cellOrg2.setCellValue(customer.getWork().getWorkPlace() + " (перевірку пройшли: " + customer.getRegistrated().getName() + ")");
        cellOrg2.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 3, 5));

        Row rowThird = sheet.createRow(2);

        Cell cellKat = rowThird.createCell(2);
        cellKat.setCellValue("Категорія пільг:");
        cellKat.setCellStyle(style);

        Cell cellQuot = rowThird.createCell(3);
        cellQuot.setCellValue(customer.getQuotaType() == null ? "Всі" : customer.getQuotaType());
        cellQuot.setCellStyle(style);

        Row rowFour = sheet.createRow(3);

        Cell cellKwart = rowFour.createCell(2);
        cellKwart.setCellValue("Квартири:");
        cellKwart.setCellStyle(style);

        Cell cellCount = rowFour.createCell(3);
        cellCount.setCellValue(customer.getRoomCount() == 0 ? "Всі" : String.valueOf(customer.getRoomCount()));
        cellCount.setCellStyle(style);

        Row rowFive = sheet.createRow(4);
        Cell cellCountAll = rowFive.createCell(3);
        cellCountAll.setCellValue(customers.size() + " осіб");
        cellCountAll.setCellStyle(style);

        Row rowSix = sheet.createRow(5);
        Row row = sheet.createRow(6);
        int[] n = {0};

        style = book.createCellStyle();
        style.setFont(font);
        style.setBorderTop((short) 5);
        Arrays.stream(names).filter(nam -> {
            if (!nam.equals("Провалился"))
                return true;
            else if (Arrays.binarySearch(ALL, name) >= 0)
                return true;
            else
                return false;

        }).forEach(colName -> {
            Cell cel = row.createCell(n[0]++);
            cel.setCellValue(colName);
            //style.setBorderTop((short) 5);
            cel.setCellStyle(style);
        });
    }

    public String getDateTime(LocalDateTime local) {
        if (local == null)
            return null;
        return DateUtil.formatDateToString(Date.from(local.atZone(ZoneId.systemDefault()).toInstant()), "dd.MM.yyyy");
    }

    private void setCellValue(Row row, int cell, Double value) {
        setCellValue(row, cell, String.valueOf(value));
    }

    private void setCellValue(Row row, int cell, double value) {
        setCellValue(row, cell, String.valueOf(value));
    }

    private void setCellValue(Row row, int cell, int value) {
        setCellValue(row, cell, String.valueOf(value));
    }


    private void setCellValue(Row row, int cell, String value) {
        Cell newCell = row.createCell(cell);
        newCell.setCellValue(value);
        newCell.setCellStyle(style);
    }

    public void close() {
        try {
            book.write(new FileOutputStream(fileName));
        } catch (IOException e) {
            // TODO Auto-generated catch block

        } finally {
            try {
                book.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block

            }
        }
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void close(OutputStream stream) {
        try {
            book.write(stream);
        } catch (IOException e) {
            // TODO Auto-generated catch block

        } finally {
            try {
                book.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block

            }
        }
    }

}