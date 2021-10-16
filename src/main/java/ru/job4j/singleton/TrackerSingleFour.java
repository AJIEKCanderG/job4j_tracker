package ru.job4j.singleton;

import ru.job4j.tracker.MemTracker;

public final class TrackerSingleFour {
    private TrackerSingleFour() {
    }

    public static MemTracker getTracker() {
        return Holder.INSTANCE;
    }

    private static final class Holder {
            private static final MemTracker INSTANCE = new MemTracker();
    }

    public static void main(String[] args) {
            MemTracker memTracker = TrackerSingleFour.getTracker();
        }
}
