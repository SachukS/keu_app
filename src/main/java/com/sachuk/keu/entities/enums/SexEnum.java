package com.sachuk.keu.entities.enums;

import java.util.Arrays;

public enum SexEnum {
    MALE("Чоловіча"), FEMALE("Жіноча");

    private String name;

    SexEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static SexEnum getByValue(String mean) {
        return Arrays.stream(SexEnum.values()).filter(f -> f.getName().equals(mean)).findAny().orElse(null);
    }
}
