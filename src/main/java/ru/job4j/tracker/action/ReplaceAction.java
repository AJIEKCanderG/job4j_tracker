package ru.job4j.tracker.action;

import ru.job4j.tracker.*;
import ru.job4j.tracker.io.Input;
import ru.job4j.tracker.io.Output;

public class ReplaceAction implements UserAction {
    private final Output out;

    public ReplaceAction(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Edit item";
    }

    @Override
    public boolean execute(Input input, Tracker tracker) {
        out.println("=== Edit item ====");
        int id = Integer.parseInt(input.askStr("Enter id: "));
        String name = input.askStr("Enter new name: ");
        Item item = new Item(name);
        boolean rls = tracker.replace(id, item);
        if (rls) {
            out.println("Item with id=" + id + " was replaced name: " + item + "\n");
        } else {
            out.println("Error replace" + "\n");
        }
        return true;
    }
}
