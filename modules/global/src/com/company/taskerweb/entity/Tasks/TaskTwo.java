package com.company.taskerweb.entity.Tasks;

import java.util.Arrays;

public class TaskTwo {

    public static String expanded(int num) {
        String result;
        String[] str = Integer.toString(num).split("");

        for (int i = 0; i < str.length - 1; i++) {
            if (Integer.valueOf(str[i]) > 0) {
                for (int j = i; j < str.length - 1; j++) {
                    str[i] += "0";
                }
            }

        }
        result = Arrays.toString(str);
        result = result.substring(1, result.length() - 1).replace(", 0", "").replace(",", " +");
        return result;
    }
}
