package com.company.taskerweb.entity.Tasks;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum Tasks implements EnumClass<String> {

    TASK_1("Задача 1"),
    TASK_2("Задача 2");

    private String id;

    Tasks(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static Tasks fromId(String id) {
        for (Tasks at : Tasks.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}