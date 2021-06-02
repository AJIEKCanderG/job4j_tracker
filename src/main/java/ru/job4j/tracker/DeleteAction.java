package ru.job4j.tracker;

public class DeleteAction implements UserAction {
    private final Output out;

    public DeleteAction(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Delete item";
    }

    @Override
    public boolean execute(Input input, Tracker tracker) {
        out.println("=== Delete item ====");
        int id = Integer.parseInt(input.askStr("Enter id: "));
        boolean rls = tracker.delete(id);
        if (rls) {
            out.println("Item with id=" + id + " was deleted" + "\n");
        } else {
            out.println("Error delete" + "\n");
        }
        return true;
    }
}
