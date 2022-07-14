package com.sachuk.keu.entities.enums;

import java.util.Arrays;

public enum RankType {

    PRESENTMILITARY("в/сл."), RESERVEMILITARY("в/сл.зап."), NONMILITARY("сл.ЗСУ");

    private String name;

    RankType(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public static QuotaType getByValue(String mean){
        return Arrays.stream(QuotaType.values()).filter(f->f.getName().equals(mean)).findAny().orElse(null);
    }
}
