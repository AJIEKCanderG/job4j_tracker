package ru.job4j.tracker.sort;

import ru.job4j.tracker.model.Item;

import java.util.Arrays;
import java.util.List;

public class ItemSorter {
    public static void main(String[] args) {
        List<Item> items = Arrays.asList(
                new Item(1, "Alex"),
                new Item(2, "Petr"),
                new Item(3, "Kirill")
        );
        items.sort(new SortByNameItem());
        System.out.println(items);
        items.sort(new SortByNameItemReverse());
        System.out.println(items);
        items.sort(new SortByIdItem());
        System.out.println(items);
    }
}
