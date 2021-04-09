package ru.job4j.tracker;

import org.junit.Test;

import java.util.Comparator;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;

public class ItemTest {

    @Test
    public void whenByName1lessName2() {
        Comparator<Item> cmpName = new SortByNameItem();
        int rsl = cmpName.compare(
                new Item("Alex"),
                new Item("Ivan")
        );
        assertThat(rsl, lessThan(0));
    }

    @Test
    public void whenByName2lessName1() {
        Comparator<Item> cmpName = new SortByNameItem();
        int rsl = cmpName.compare(
                new Item("Petr"),
                new Item("Bob")
        );
        assertThat(rsl, greaterThan(0));
    }

    @Test
    public void whenByName1equalName2() {
        Comparator<Item> cmpName = new SortByNameItem();
        int rsl = cmpName.compare(
                new Item("Petr"),
                new Item("Petr")
        );
        assertThat(rsl, equalTo(0));
    }

    @Test
    public void whenByName1lessName2Reverse() {
        Comparator<Item> cmpName = new SortByNameItemReverse();
        int rsl = cmpName.compare(
                new Item("Alex"),
                new Item("Ivan")
        );
        assertThat(rsl, greaterThan(0));
    }

    @Test
    public void whenByName2lessName1Reverse() {
        Comparator<Item> cmpName = new SortByNameItemReverse();
        int rsl = cmpName.compare(
                new Item("Petr"),
                new Item("Bob")
        );
        assertThat(rsl, lessThan(0));
    }

    @Test
    public void whenByName1equalName2Reverse() {
        Comparator<Item> cmpName = new SortByNameItemReverse();
        int rsl = cmpName.compare(
                new Item("Petr"),
                new Item("Petr")
        );
        assertThat(rsl, equalTo(0));
    }

    @Test
    public void whenById1lessId2() {
        Comparator<Item> cmpName = new SortByIdItem();
        int rsl = cmpName.compare(
                new Item("Petr", 1),
                new Item("Ivan", 2)
        );
        assertThat(rsl, lessThan(0));
    }

    @Test
    public void whenById12lessId1() {
        Comparator<Item> cmpName = new SortByIdItem();
        int rsl = cmpName.compare(
                new Item("Petr", 3),
                new Item("Bob", 2)
        );
        assertThat(rsl, greaterThan(0));
    }

    @Test
    public void whenById1equalId2() {
        Comparator<Item> cmpName = new SortByIdItem();
        int rsl = cmpName.compare(
                new Item("Petr", 1),
                new Item("Bob", 1)
        );
        assertThat(rsl, equalTo(0));
    }

    @Test
    public void whenById1lessId2Reverse() {
        Comparator<Item> cmpName = new SortByIdItemReverse();
        int rsl = cmpName.compare(
                new Item("Petr", 1),
                new Item("Ivan", 2)
        );
        assertThat(rsl, greaterThan(0));
    }

    @Test
    public void whenById12lessId1Reverse() {
        Comparator<Item> cmpName = new SortByIdItemReverse();
        int rsl = cmpName.compare(
                new Item("Petr", 3),
                new Item("Bob", 2)
        );
        assertThat(rsl, lessThan(0));
    }

    @Test
    public void whenById1equalId2Reverse() {
        Comparator<Item> cmpName = new SortByIdItemReverse();
        int rsl = cmpName.compare(
                new Item("Petr", 1),
                new Item("Bob", 1)
        );
        assertThat(rsl, equalTo(0));
    }

}