package ru.job4j.singleton;

import ru.job4j.tracker.store.MemTracker;

public enum TrackerSingleOne {
        INSTANCE;
    private final MemTracker memTracker = new MemTracker();

    public MemTracker getTracker() {
        return memTracker;
    }
}