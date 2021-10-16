package ru.job4j.singleton;

import ru.job4j.tracker.MemTracker;

public class TrackerSingleTwo {
    private static MemTracker instance;

    private TrackerSingleTwo() {
    }

    public static MemTracker getTracker() {
        if (instance == null) {
            instance = new MemTracker();
        }
        return instance;
    }
}
