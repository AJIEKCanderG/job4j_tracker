package ru.job4j.tracker.action;

import ru.job4j.tracker.*;
import ru.job4j.tracker.io.Input;
import ru.job4j.tracker.io.Output;

public class FindByIdAction implements UserAction {
    private final Output out;

    public FindByIdAction(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Find item by Id";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        out.println("=== Find item by Id ====");
        int id = Integer.parseInt(input.askStr("Enter id: "));
        Item rls = tracker.findById(id);
        if (rls != null) {
            out.println("Found by your request " + rls);
        } else {
            out.println("Item with id=" + id + " not found");
            out.println("Please repeat you select:" + "\n");
        }
        return true;
    }
}
