package ru.job4j.array;

import java.util.Arrays;

public class ArrayDuplicate {
    public String[] remove(String[] array) {
        String variable;
        int flag = 0;
        for (int i = 0; i < array.length - flag; i++) {
            for (int j = i + 1; j < array.length - i - flag; j++) {
                if (array[i].equals(array[j])) {
                    variable = array[array.length - 1 - flag];
                    array[array.length - 1 - flag] = array[j];
                    array[j] = variable;
                    System.out.println(array[array.length - 1 - flag]);
                    System.out.println(array[j]);
                    flag++;
                }
            }
        }
        return Arrays.copyOf(array, array.length - flag);
    }
}
