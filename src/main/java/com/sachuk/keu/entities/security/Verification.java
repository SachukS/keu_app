package com.sachuk.keu.entities.security;

import java.util.Arrays;

public enum Verification {

    UNVERIFIED("Входу не здійснено"), VERIFIED("Пароль замінено на новий");

    private String name;

    Verification(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Verification getByValue(String mean) {


        return Arrays.stream(Verification.values()).filter(f -> f.getName().equals(mean)).findAny().orElse(null);
    }


}
