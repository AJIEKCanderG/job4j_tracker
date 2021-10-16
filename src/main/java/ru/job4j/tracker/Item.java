package ru.job4j.tracker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Item {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    private int id;
    private String name;
    private final LocalDateTime created = LocalDateTime.now();

    public Item(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public Item(String name) {
        this.name = name;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("id: %s, name: %s, created: %s", id, name, FORMATTER.format(created));
    }
}
