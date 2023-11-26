package com.sachuk.keu.entities.enums;

import java.util.Arrays;

public enum RoleEnum {
    ROLE_ADMIN("Адмін"), ROLE_OPERATOR("Оператор"), ROLE_USER("Користувач"), ROLE_NONE("Інспектор");

    private String name;

    RoleEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static RoleEnum getByValue(String mean) {
        return Arrays.stream(RoleEnum.values()).filter(f -> f.getName().equals(mean)).findAny().orElse(null);
    }
}