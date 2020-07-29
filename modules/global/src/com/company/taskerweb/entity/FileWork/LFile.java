package com.company.taskerweb.entity.FileWork;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class LFile {
    public static String load(File file) throws FileNotFoundException {
        FileReader fileReader = new FileReader(file);
        Scanner scanner = new Scanner(fileReader);
        String result = null;

        while (scanner.hasNextLine()) {
            if (result == null) {
                result = scanner.nextLine();
            } else {
                result = result +"\n" + scanner.nextLine();
            }
        }
        scanner.close();
        return result;
    }


}
