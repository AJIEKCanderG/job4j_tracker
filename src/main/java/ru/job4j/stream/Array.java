package ru.job4j.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Array {
    public static void main(String[] args) {
        List<Integer> start = new ArrayList<>(List.of(-2, -1, 0, 1, 2));
        System.out.println("Заполнил ArrayList числами: ");
        start.forEach(System.out::print);
        System.out.println();
        System.out.println("Получил поток положительных чисел: ");
        List<Integer> finish = start.stream()
                .filter(list -> list >= 0)
                .collect(Collectors.toList());
        finish.forEach(System.out::print);
    }
}
