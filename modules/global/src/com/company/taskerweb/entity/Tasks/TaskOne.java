package com.company.taskerweb.entity.Tasks;

import java.util.Set;
import java.util.TreeSet;

public class TaskOne {
    private String[] arr1, arr2;

    public TaskOne(String[] arr1, String[] arr2) {
        this.arr1 = arr1;
        this.arr2 = arr2;
    }

    public String[] calculate() {
        Set<String> result = new TreeSet<>();

        for (String str1 : arr1) {
            for (String str2 : arr2) {
                if (str2.contains(str1)) {
                    result.add(str1);
                }
            }
        }
        return result.toArray(new String[0]);
    }
}
