package ru.job4j.tracker.action;

import ru.job4j.tracker.store.Store;
import ru.job4j.tracker.io.Input;
import ru.job4j.tracker.io.Output;

public class ExitAction implements UserAction {
    private final Output out;

    public ExitAction(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Exit";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        out.println("=== Good bye !!! ====");
        return false;
    }
}
