package com.sachuk.keu.entities.security;

import java.util.Arrays;

public enum Permission {
    NONE("Не перевірено"), PERMITTED("Дозволено"), PROHIBITED("Заборонено");

    private String name;

    Permission(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public static Permission getByValue(String mean){
        return Arrays.stream(Permission.values()).filter(f->f.getName().equals(mean)).findAny().orElse(null);
    }

}
