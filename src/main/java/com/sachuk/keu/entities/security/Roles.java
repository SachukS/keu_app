package com.sachuk.keu.entities.security;

import java.util.Arrays;

public enum Roles {
    ROLE_ADMIN("Адмін"), ROLE_OPERATOR("Оператор");

    private String name;

    Roles(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Roles getByValue(String mean) {
        return Arrays.stream(Roles.values()).filter(f -> f.getName().equals(mean)).findAny().orElse(null);
    }
}