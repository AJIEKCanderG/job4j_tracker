package ru.job4j.tracker.store;

import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.store.Store;

import java.util.ArrayList;
import java.util.List;

public class MemTracker implements Store {
    private final List<Item> items = new ArrayList<>();
    private int ids = 1;

    @Override
    public void init() {
    }

    public Item add(Item item) {
        item.setId(ids++);
        items.add(item);
        return item;
    }

    public List<Item> findAll() {
        return List.copyOf(items);
    }

    public List<Item> findByName(String key) {
       List<Item> items1 = new ArrayList<>();
        for (Item item : items) {
            if (item.getName().equals(key)) {
                items1.add(item);
            }
        }
        return items1;
    }

    private int indexOf(int id) {
        int rsl = -1;
        for (int index = 0; index < items.size(); index++) {
            if (items.get(index).getId() == id) {
                rsl = index;
                break;
            }
        }
        return rsl;
    }

    public Item findById(int id) {
        int index = indexOf(id);
        return index != -1 ? items.get(index) : null;
    }

    public boolean replace(int id, Item item) {
        int index = indexOf(id);
        item.setId(id);
        if (index != -1) {
            items.set(index, item);
            return true;
        }
            return false;
    }

    public boolean delete(int id) {
        int index = indexOf(id);
        if (index != -1) {
           items.remove(index);
            return true;
        }
            return false;
    }

    @Override
    public void close() {
    }
}